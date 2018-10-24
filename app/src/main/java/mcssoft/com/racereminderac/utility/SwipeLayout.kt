package mcssoft.com.racereminderac.utility

/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Chau Thai
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**
 * Changed class name to SwipeLayout (from SwipeRevealLayout) and converted to Kotlin.
 * Other original comments mainly untouched.
 */

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import mcssoft.com.racereminderac.R

class SwipeLayout : ViewGroup {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var mOnLayoutCount = 0

    val DRAG_EDGE_LEFT = 0x1
    val DRAG_EDGE_RIGHT = 0x1 shl 1
    val DRAG_EDGE_TOP = 0x1 shl 2
    val DRAG_EDGE_BOTTOM = 0x1 shl 3
    /**
     * The secondary view will be under the main view.
     */
    val MODE_NORMAL = 0
    /**
     * The secondary view will stick the edge of the main view.
     */
    val MODE_SAME_LEVEL = 1
    // These states are used only for ViewBindHelper
    protected val STATE_CLOSE = 0
    protected val STATE_CLOSING = 1
    protected val STATE_OPEN = 2
    protected val STATE_OPENING = 3
    protected val STATE_DRAGGING = 4
    private val DEFAULT_MIN_FLING_VELOCITY = 300 // dp per second
    private val DEFAULT_MIN_DIST_REQUEST_DISALLOW_PARENT = 1 // dp
    /**
     * Main view is the view which is shown when the layout is closed.
     */
    private var mMainView: View? = null

    /**
     * Secondary view is the view which is shown when the layout is opened.
     */
    private var mSecondaryView: View? = null

    /**
     * The rectangle position of the main view when the layout is closed.
     */
    private val mRectMainClose = Rect()

    /**
     * The rectangle position of the main view when the layout is opened.
     */
    private val mRectMainOpen = Rect()

    /**
     * The rectangle position of the secondary view when the layout is closed.
     */
    private val mRectSecClose = Rect()

    /**
     * The rectangle position of the secondary view when the layout is opened.
     */
    private val mRectSecOpen = Rect()

    /**
     * The minimum distance (px) to the closest drag edge that the SwipeRevealLayout
     * will disallow the parent to intercept touch event.
     */
    private var mMinDistRequestDisallowParent = 0

    private var mIsOpenBeforeInit = false
    @Volatile
    private var mAborted = false
    @Volatile
    private var mIsScrolling = false
    @Volatile
    private var mLockDrag = false

    private var mMinFlingVelocity = DEFAULT_MIN_FLING_VELOCITY
    private var mState = STATE_CLOSE
    private var mMode = MODE_NORMAL

    private var mLastMainLeft = 0
    private var mLastMainTop = 0

    private var mDragEdge = DRAG_EDGE_LEFT

    private val mGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        var hasDisallowed = false

        override fun onDown(e: MotionEvent): Boolean {
            mIsScrolling = false
            hasDisallowed = false
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            mIsScrolling = true
            return false
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            mIsScrolling = true

            if (parent != null) {
                val shouldDisallow: Boolean

                if (!hasDisallowed) {
                    shouldDisallow = getDistToClosestEdge() >= mMinDistRequestDisallowParent
                    if (shouldDisallow) {
                        hasDisallowed = true
                    }
                } else {
                    shouldDisallow = true
                }

                // disallow parent to intercept touch event so that the layout will work
                // properly on RecyclerView or view that handles scroll gesture.
                parent.requestDisallowInterceptTouchEvent(shouldDisallow)
            }

            return false
        }
    }

    private var mDragDist = 0f
    private var mPrevX = -1f
    private var mPrevY = -1f
    private var mDragHelper: ViewDragHelper? = null
    private var mGestureDetector: GestureDetectorCompat? = null
    private var mDragStateChangeListener: SwipeLayout.DragStateChangeListener? = null // only used for ViewBindHelper
    private var mSwipeListener: SwipeLayout.SwipeListener? = null

    private val mDragHelperCallback = object : ViewDragHelper.Callback() {

        private val slideOffset: Float
            get() {
                when (mDragEdge) {
                    DRAG_EDGE_LEFT -> return (mMainView!!.getLeft() - mRectMainClose.left).toFloat() / mSecondaryView!!.getWidth()

                    DRAG_EDGE_RIGHT -> return (mRectMainClose.left - mMainView!!.getLeft()).toFloat() / mSecondaryView!!.getWidth()

                    DRAG_EDGE_TOP -> return (mMainView!!.getTop() - mRectMainClose.top).toFloat() / mSecondaryView!!.getHeight()

                    DRAG_EDGE_BOTTOM -> return (mRectMainClose.top - mMainView!!.getTop()).toFloat() / mSecondaryView!!.getHeight()

                    else -> return 0f
                }
            }

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            mAborted = false

            if (mLockDrag)
                return false

            mDragHelper?.captureChildView(mMainView!!, pointerId)
            return false
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            when (mDragEdge) {
                DRAG_EDGE_TOP -> return Math.max(
                        Math.min(top, mRectMainClose.top + mSecondaryView!!.getHeight()),
                        mRectMainClose.top
                )

                DRAG_EDGE_BOTTOM -> return Math.max(
                        Math.min(top, mRectMainClose.top),
                        mRectMainClose.top - mSecondaryView!!.getHeight()
                )

                else -> return child.top
            }
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            when (mDragEdge) {
                DRAG_EDGE_RIGHT -> return Math.max(
                        Math.min(left, mRectMainClose.left),
                        mRectMainClose.left - mSecondaryView!!.getWidth()
                )

                DRAG_EDGE_LEFT -> return Math.max(
                        Math.min(left, mRectMainClose.left + mSecondaryView!!.getWidth()),
                        mRectMainClose.left
                )

                else -> return child.left
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val velRightExceeded = pxToDp(xvel.toInt()) >= mMinFlingVelocity
            val velLeftExceeded = pxToDp(xvel.toInt()) <= -mMinFlingVelocity
            val velUpExceeded = pxToDp(yvel.toInt()) <= -mMinFlingVelocity
            val velDownExceeded = pxToDp(yvel.toInt()) >= mMinFlingVelocity

            val pivotHorizontal = getHalfwayPivotHorizontal()
            val pivotVertical = getHalfwayPivotVertical()

            when (mDragEdge) {
                DRAG_EDGE_RIGHT -> if (velRightExceeded) {
                    close(true)
                } else if (velLeftExceeded) {
                    open(true)
                } else {
                    if (mMainView!!.getRight() < pivotHorizontal) {
                        open(true)
                    } else {
                        close(true)
                    }
                }

                DRAG_EDGE_LEFT -> if (velRightExceeded) {
                    open(true)
                } else if (velLeftExceeded) {
                    close(true)
                } else {
                    if (mMainView!!.getLeft() < pivotHorizontal) {
                        close(true)
                    } else {
                        open(true)
                    }
                }

                DRAG_EDGE_TOP -> if (velUpExceeded) {
                    close(true)
                } else if (velDownExceeded) {
                    open(true)
                } else {
                    if (mMainView!!.getTop() < pivotVertical) {
                        close(true)
                    } else {
                        open(true)
                    }
                }

                DRAG_EDGE_BOTTOM -> if (velUpExceeded) {
                    open(true)
                } else if (velDownExceeded) {
                    close(true)
                } else {
                    if (mMainView!!.getBottom() < pivotVertical) {
                        open(true)
                    } else {
                        close(true)
                    }
                }
            }
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            super.onEdgeDragStarted(edgeFlags, pointerId)

            if (mLockDrag) {
                return
            }

            val edgeStartLeft = mDragEdge == DRAG_EDGE_RIGHT && edgeFlags == ViewDragHelper.EDGE_LEFT

            val edgeStartRight = mDragEdge == DRAG_EDGE_LEFT && edgeFlags == ViewDragHelper.EDGE_RIGHT

            val edgeStartTop = mDragEdge == DRAG_EDGE_BOTTOM && edgeFlags == ViewDragHelper.EDGE_TOP

            val edgeStartBottom = mDragEdge == DRAG_EDGE_TOP && edgeFlags == ViewDragHelper.EDGE_BOTTOM

            if (edgeStartLeft || edgeStartRight || edgeStartTop || edgeStartBottom) {
                mDragHelper!!.captureChildView(mMainView!!, pointerId)
            }
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            if (mMode == MODE_SAME_LEVEL) {
                if (mDragEdge == DRAG_EDGE_LEFT || mDragEdge == DRAG_EDGE_RIGHT) {
                    mSecondaryView!!.offsetLeftAndRight(dx)
                } else {
                    mSecondaryView!!.offsetTopAndBottom(dy)
                }
            }

            val isMoved = mMainView!!.getLeft() != mLastMainLeft || mMainView!!.getTop() != mLastMainTop
            if (mSwipeListener != null && isMoved) {
                if (mMainView!!.getLeft() == mRectMainClose.left && mMainView!!.getTop() == mRectMainClose.top) {
                    mSwipeListener!!.onClosed(this@SwipeLayout)
                } else if (mMainView!!.getLeft() == mRectMainOpen.left && mMainView!!.getTop() == mRectMainOpen.top) {
                    mSwipeListener!!.onOpened(this@SwipeLayout)
                } else {
                    mSwipeListener!!.onSlide(this@SwipeLayout, slideOffset)
                }
            }

            mLastMainLeft = mMainView!!.getLeft()
            mLastMainTop = mMainView!!.getTop()
            ViewCompat.postInvalidateOnAnimation(this@SwipeLayout)
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            val prevState = mState

            when (state) {
                ViewDragHelper.STATE_DRAGGING -> mState = STATE_DRAGGING

                ViewDragHelper.STATE_IDLE ->

                    // drag edge is left or right
                    if (mDragEdge == DRAG_EDGE_LEFT || mDragEdge == DRAG_EDGE_RIGHT) {
                        if (mMainView!!.getLeft() == mRectMainClose.left) {
                            mState = STATE_CLOSE
                        } else {
                            mState = STATE_OPEN
                        }
                    } else {
                        if (mMainView!!.getTop() == mRectMainClose.top) {
                            mState = STATE_CLOSE
                        } else {
                            mState = STATE_OPEN
                        }
                    }// drag edge is top or bottom
            }

            if (mDragStateChangeListener != null && !mAborted && prevState != mState) {
                mDragStateChangeListener!!.onDragStateChanged(mState)
            }
        }
    }

    fun getStateString(state: Int): String {
        when (state) {
            STATE_CLOSE -> return "state_close"

            STATE_CLOSING -> return "state_closing"

            STATE_OPEN -> return "state_open"

            STATE_OPENING -> return "state_opening"

            STATE_DRAGGING -> return "state_dragging"

            else -> return "undefined"
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mGestureDetector!!.onTouchEvent(event)
        mDragHelper!!.processTouchEvent(event)
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (isDragLocked()) {
            return super.onInterceptTouchEvent(ev)
        }

        mDragHelper!!.processTouchEvent(ev)
        mGestureDetector!!.onTouchEvent(ev)
        accumulateDragDist(ev)

        val couldBecomeClick = couldBecomeClick(ev)
        val settling = mDragHelper!!.getViewDragState() == ViewDragHelper.STATE_SETTLING
        val idleAfterScrolled = mDragHelper!!.getViewDragState() == ViewDragHelper.STATE_IDLE && mIsScrolling

        // must be placed as the last statement
        mPrevX = ev.x
        mPrevY = ev.y

        // return true => intercept, cannot trigger onClick event
        return !couldBecomeClick && (settling || idleAfterScrolled)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // get views
        if (childCount >= 2) {
            mSecondaryView = getChildAt(0)
            mMainView = getChildAt(1)
        } else if (childCount == 1) {
            mMainView = getChildAt(0)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mAborted = false

        for (index in 0 until childCount) {
            val child = getChildAt(index)

            var left: Int
            var right: Int
            var top: Int
            var bottom: Int
            bottom = 0
            top = bottom
            right = top
            left = right

            val minLeft = paddingLeft
            val maxRight = Math.max(r - paddingRight - l, 0)
            val minTop = paddingTop
            val maxBottom = Math.max(b - paddingBottom - t, 0)

            var measuredChildHeight = child.measuredHeight
            var measuredChildWidth = child.measuredWidth

            // need to take account if child size is match_parent
            val childParams = child.layoutParams
            var matchParentHeight = false
            var matchParentWidth = false

            if (childParams != null) {
                matchParentHeight = childParams.height == ViewGroup.LayoutParams.MATCH_PARENT || childParams.height == ViewGroup.LayoutParams.FILL_PARENT
                matchParentWidth = childParams.width == ViewGroup.LayoutParams.MATCH_PARENT || childParams.width == ViewGroup.LayoutParams.FILL_PARENT
            }

            if (matchParentHeight) {
                measuredChildHeight = maxBottom - minTop
                childParams!!.height = measuredChildHeight
            }

            if (matchParentWidth) {
                measuredChildWidth = maxRight - minLeft
                childParams!!.width = measuredChildWidth
            }

            when (mDragEdge) {
                DRAG_EDGE_RIGHT -> {
                    left = Math.max(r - measuredChildWidth - paddingRight - l, minLeft)
                    top = Math.min(paddingTop, maxBottom)
                    right = Math.max(r - paddingRight - l, minLeft)
                    bottom = Math.min(measuredChildHeight + paddingTop, maxBottom)
                }

                DRAG_EDGE_LEFT -> {
                    left = Math.min(paddingLeft, maxRight)
                    top = Math.min(paddingTop, maxBottom)
                    right = Math.min(measuredChildWidth + paddingLeft, maxRight)
                    bottom = Math.min(measuredChildHeight + paddingTop, maxBottom)
                }

                DRAG_EDGE_TOP -> {
                    left = Math.min(paddingLeft, maxRight)
                    top = Math.min(paddingTop, maxBottom)
                    right = Math.min(measuredChildWidth + paddingLeft, maxRight)
                    bottom = Math.min(measuredChildHeight + paddingTop, maxBottom)
                }

                DRAG_EDGE_BOTTOM -> {
                    left = Math.min(paddingLeft, maxRight)
                    top = Math.max(b - measuredChildHeight - paddingBottom - t, minTop)
                    right = Math.min(measuredChildWidth + paddingLeft, maxRight)
                    bottom = Math.max(b - paddingBottom - t, minTop)
                }
            }

            child.layout(left, top, right, bottom)
        }

        // taking account offset when mode is SAME_LEVEL
        if (mMode == MODE_SAME_LEVEL) {
            when (mDragEdge) {
                DRAG_EDGE_LEFT -> mSecondaryView!!.offsetLeftAndRight(-mSecondaryView!!.getWidth())

                DRAG_EDGE_RIGHT -> mSecondaryView!!.offsetLeftAndRight(mSecondaryView!!.getWidth())

                DRAG_EDGE_TOP -> mSecondaryView!!.offsetTopAndBottom(-mSecondaryView!!.getHeight())

                DRAG_EDGE_BOTTOM -> mSecondaryView!!.offsetTopAndBottom(mSecondaryView!!.getHeight())
            }
        }

        initRects()

        if (mIsOpenBeforeInit) {
            open(false)
        } else {
            close(false)
        }

        mLastMainLeft = mMainView!!.getLeft()
        mLastMainTop = mMainView!!.getTop()

        mOnLayoutCount++
    }

    /**
     * {@inheritDoc}
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        var heightMeasureSpec = heightMeasureSpec
        if (childCount < 2) {
            throw RuntimeException("Layout must have two children")
        }

        val params = layoutParams

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        var desiredWidth = 0
        var desiredHeight = 0

        // first find the largest child
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            desiredWidth = Math.max(child.measuredWidth, desiredWidth)
            desiredHeight = Math.max(child.measuredHeight, desiredHeight)
        }
        // create new measure spec using the largest child width
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(desiredWidth, widthMode)
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(desiredHeight, heightMode)

        val measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childParams = child.layoutParams

            if (childParams != null) {
                if (childParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                    child.minimumHeight = measuredHeight
                }

                if (childParams.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    child.minimumWidth = measuredWidth
                }
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            desiredWidth = Math.max(child.measuredWidth, desiredWidth)
            desiredHeight = Math.max(child.measuredHeight, desiredHeight)
        }

        // taking accounts of padding
        desiredWidth += paddingLeft + paddingRight
        desiredHeight += paddingTop + paddingBottom

        // adjust desired width
        if (widthMode == View.MeasureSpec.EXACTLY) {
            desiredWidth = measuredWidth
        } else {
            if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                desiredWidth = measuredWidth
            }

            if (widthMode == View.MeasureSpec.AT_MOST) {
                desiredWidth = if (desiredWidth > measuredWidth) measuredWidth else desiredWidth
            }
        }

        // adjust desired height
        if (heightMode == View.MeasureSpec.EXACTLY) {
            desiredHeight = measuredHeight
        } else {
            if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                desiredHeight = measuredHeight
            }

            if (heightMode == View.MeasureSpec.AT_MOST) {
                desiredHeight = if (desiredHeight > measuredHeight) measuredHeight else desiredHeight
            }
        }

        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun computeScroll() {
        if (mDragHelper!!.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    /**
     * Open the panel to show the secondary view
     * @param animation true to animate the open motion. [SwipeListener] won't be
     * called if is animation is false.
     */
    fun open(animation: Boolean) {
        mIsOpenBeforeInit = true
        mAborted = false

        if (animation) {
            mState = STATE_OPENING
            mDragHelper!!.smoothSlideViewTo(mMainView!!, mRectMainOpen.left, mRectMainOpen.top)

            if (mDragStateChangeListener != null) {
                mDragStateChangeListener!!.onDragStateChanged(mState)
            }
        } else {
            mState = STATE_OPEN
            mDragHelper!!.abort()

            mMainView!!.layout(
                    mRectMainOpen.left,
                    mRectMainOpen.top,
                    mRectMainOpen.right,
                    mRectMainOpen.bottom
            )

            mSecondaryView!!.layout(
                    mRectSecOpen.left,
                    mRectSecOpen.top,
                    mRectSecOpen.right,
                    mRectSecOpen.bottom
            )
        }

        ViewCompat.postInvalidateOnAnimation(this)
    }

    /**
     * Close the panel to hide the secondary view
     * @param animation true to animate the close motion. [SwipeListener] won't be
     * called if is animation is false.
     */
    fun close(animation: Boolean) {
        mIsOpenBeforeInit = false
        mAborted = false

        if (animation) {
            mState = STATE_CLOSING
            mDragHelper!!.smoothSlideViewTo(mMainView!!, mRectMainClose.left, mRectMainClose.top)

            if (mDragStateChangeListener != null) {
                mDragStateChangeListener!!.onDragStateChanged(mState)
            }

        } else {
            mState = STATE_CLOSE
            mDragHelper!!.abort()

            mMainView!!.layout(
                    mRectMainClose.left,
                    mRectMainClose.top,
                    mRectMainClose.right,
                    mRectMainClose.bottom
            )

            mSecondaryView!!.layout(
                    mRectSecClose.left,
                    mRectSecClose.top,
                    mRectSecClose.right,
                    mRectSecClose.bottom
            )
        }

        ViewCompat.postInvalidateOnAnimation(this)
    }

    /**
     * Get the minimum fling velocity to cause the layout to open/close.
     * @return dp per second
     */
    fun getMinFlingVelocity(): Int {
        return mMinFlingVelocity
    }

    /**
     * Set the minimum fling velocity to cause the layout to open/close.
     * @param velocity dp per second
     */
    fun setMinFlingVelocity(velocity: Int) {
        mMinFlingVelocity = velocity
    }

    /**
     * Get the edge where the layout can be dragged from.
     * @return Can be one of these
     *
     *  * [.DRAG_EDGE_LEFT]
     *  * [.DRAG_EDGE_TOP]
     *  * [.DRAG_EDGE_RIGHT]
     *  * [.DRAG_EDGE_BOTTOM]
     *
     */
    fun getDragEdge(): Int {
        return mDragEdge
    }

    /**
     * Set the edge where the layout can be dragged from.
     * @param dragEdge Can be one of these
     *
     *  * [.DRAG_EDGE_LEFT]
     *  * [.DRAG_EDGE_TOP]
     *  * [.DRAG_EDGE_RIGHT]
     *  * [.DRAG_EDGE_BOTTOM]
     *
     */
    fun setDragEdge(dragEdge: Int) {
        mDragEdge = dragEdge
    }

    fun setSwipeListener(listener: SwipeLayout.SwipeListener) {
        mSwipeListener = listener
    }

    /**
     * @param lock if set to true, the user cannot drag/swipe the layout.
     */
    fun setLockDrag(lock: Boolean) {
        mLockDrag = lock
    }

    /**
     * @return true if the drag/swipe motion is currently locked.
     */
    fun isDragLocked(): Boolean {
        return mLockDrag
    }

    /**
     * @return true if layout is fully opened, false otherwise.
     */
    fun isOpened(): Boolean {
        return mState == STATE_OPEN
    }

    /**
     * @return true if layout is fully closed, false otherwise.
     */
    fun isClosed(): Boolean {
        return mState == STATE_CLOSE
    }

    /** Only used for [ViewBinderHelper]  */
    internal fun setDragStateChangeListener(listener: SwipeLayout.DragStateChangeListener) {
        mDragStateChangeListener = listener
    }

    /** Abort current motion in progress. Only used for [ViewBinderHelper]  */
    protected fun abort() {
        mAborted = true
        mDragHelper!!.abort()
    }

    /**
     * In RecyclerView/ListView, onLayout should be called 2 times to display children views correctly.
     * This method check if it've already called onLayout two times.
     * @return true if you should call [.requestLayout].
     */
    protected fun shouldRequestLayout(): Boolean {
        return mOnLayoutCount < 2
    }


    private fun getMainOpenLeft(): Int {
        when (mDragEdge) {
            DRAG_EDGE_LEFT -> return mRectMainClose.left + mSecondaryView!!.getWidth()

            DRAG_EDGE_RIGHT -> return mRectMainClose.left - mSecondaryView!!.getWidth()

            DRAG_EDGE_TOP -> return mRectMainClose.left

            DRAG_EDGE_BOTTOM -> return mRectMainClose.left

            else -> return 0
        }
    }

    private fun getMainOpenTop(): Int {
        when (mDragEdge) {
            DRAG_EDGE_LEFT -> return mRectMainClose.top

            DRAG_EDGE_RIGHT -> return mRectMainClose.top

            DRAG_EDGE_TOP -> return mRectMainClose.top + mSecondaryView!!.getHeight()

            DRAG_EDGE_BOTTOM -> return mRectMainClose.top - mSecondaryView!!.getHeight()

            else -> return 0
        }
    }

    private fun getSecOpenLeft(): Int {
        if (mMode == MODE_NORMAL || mDragEdge == DRAG_EDGE_BOTTOM || mDragEdge == DRAG_EDGE_TOP) {
            return mRectSecClose.left
        }

        return if (mDragEdge == DRAG_EDGE_LEFT) {
            mRectSecClose.left + mSecondaryView!!.getWidth()
        } else {
            mRectSecClose.left - mSecondaryView!!.getWidth()
        }
    }

    private fun getSecOpenTop(): Int {
        if (mMode == MODE_NORMAL || mDragEdge == DRAG_EDGE_LEFT || mDragEdge == DRAG_EDGE_RIGHT) {
            return mRectSecClose.top
        }

        return if (mDragEdge == DRAG_EDGE_TOP) {
            mRectSecClose.top + mSecondaryView!!.getHeight()
        } else {
            mRectSecClose.top - mSecondaryView!!.getHeight()
        }
    }

    private fun initRects() {
        // close position of main view
        mRectMainClose.set(
                mMainView!!.getLeft(),
                mMainView!!.getTop(),
                mMainView!!.getRight(),
                mMainView!!.getBottom()
        )

        // close position of secondary view
        mRectSecClose.set(
                mSecondaryView!!.getLeft(),
                mSecondaryView!!.getTop(),
                mSecondaryView!!.getRight(),
                mSecondaryView!!.getBottom()
        )

        // open position of the main view
        mRectMainOpen.set(
                getMainOpenLeft(),
                getMainOpenTop(),
                getMainOpenLeft() + mMainView!!.getWidth(),
                getMainOpenTop() + mMainView!!.getHeight()
        )

        // open position of the secondary view
        mRectSecOpen.set(
                getSecOpenLeft(),
                getSecOpenTop(),
                getSecOpenLeft() + mSecondaryView!!.getWidth(),
                getSecOpenTop() + mSecondaryView!!.getHeight()
        )
    }

    private fun couldBecomeClick(ev: MotionEvent): Boolean {
        return isInMainView(ev) && !shouldInitiateADrag()
    }

    private fun isInMainView(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y

        val withinVertical = mMainView!!.getTop() <= y && y <= mMainView!!.getBottom()
        val withinHorizontal = mMainView!!.getLeft() <= x && x <= mMainView!!.getRight()

        return withinVertical && withinHorizontal
    }

    private fun shouldInitiateADrag(): Boolean {
        val minDistToInitiateDrag = mDragHelper!!.getTouchSlop()
        return mDragDist >= minDistToInitiateDrag
    }

    private fun accumulateDragDist(ev: MotionEvent) {
        val action = ev.action
        if (action == MotionEvent.ACTION_DOWN) {
            mDragDist = 0f
            return
        }

        val dragHorizontally = getDragEdge() == DRAG_EDGE_LEFT || getDragEdge() == DRAG_EDGE_RIGHT

        val dragged: Float
        if (dragHorizontally) {
            dragged = Math.abs(ev.x - mPrevX)
        } else {
            dragged = Math.abs(ev.y - mPrevY)
        }

        mDragDist += dragged
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        if (attrs != null && context != null) {
            val a = context.theme.obtainStyledAttributes(
                    attrs,
                    R.styleable.SwipeLayout,
                    0, 0
            )

            mDragEdge = a.getInteger(R.styleable.SwipeLayout_dragEdge, DRAG_EDGE_LEFT)
            mMinFlingVelocity = a.getInteger(R.styleable.SwipeLayout_flingVelocity, DEFAULT_MIN_FLING_VELOCITY)
            mMode = a.getInteger(R.styleable.SwipeLayout_mode, MODE_NORMAL)

            mMinDistRequestDisallowParent = a.getDimensionPixelSize(
                    R.styleable.SwipeLayout_minDistRequestDisallowParent,
                    dpToPx(DEFAULT_MIN_DIST_REQUEST_DISALLOW_PARENT)
            )
        }

        mDragHelper = ViewDragHelper.create(this, 1.0f, mDragHelperCallback)
        mDragHelper!!.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL)

        mGestureDetector = GestureDetectorCompat(context, mGestureListener)
    }

    private fun getDistToClosestEdge(): Int {
        when (mDragEdge) {
            DRAG_EDGE_LEFT -> {
                val pivotRight = mRectMainClose.left + mSecondaryView!!.getWidth()

                return Math.min(
                        mMainView!!.getLeft() - mRectMainClose.left,
                        pivotRight - mMainView!!.getLeft()
                )
            }

            DRAG_EDGE_RIGHT -> {
                val pivotLeft = mRectMainClose.right - mSecondaryView!!.getWidth()

                return Math.min(
                        mMainView!!.getRight() - pivotLeft,
                        mRectMainClose.right - mMainView!!.getRight()
                )
            }

            DRAG_EDGE_TOP -> {
                val pivotBottom = mRectMainClose.top + mSecondaryView!!.getHeight()

                return Math.min(
                        mMainView!!.getBottom() - pivotBottom,
                        pivotBottom - mMainView!!.getTop()
                )
            }

            DRAG_EDGE_BOTTOM -> {
                val pivotTop = mRectMainClose.bottom - mSecondaryView!!.getHeight()

                return Math.min(
                        mRectMainClose.bottom - mMainView!!.getBottom(),
                        mMainView!!.getBottom() - pivotTop
                )
            }
        }

        return 0
    }

    private fun getHalfwayPivotHorizontal(): Int {
        return if (mDragEdge == DRAG_EDGE_LEFT) {
            mRectMainClose.left + mSecondaryView!!.getWidth() / 2
        } else {
            mRectMainClose.right - mSecondaryView!!.getWidth() / 2
        }
    }

    private fun getHalfwayPivotVertical(): Int {
        return if (mDragEdge == DRAG_EDGE_TOP) {
            mRectMainClose.top + mSecondaryView!!.getHeight() / 2
        } else {
            mRectMainClose.bottom - mSecondaryView!!.getHeight() / 2
        }
    }

    private fun pxToDp(px: Int): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    private fun dpToPx(dp: Int): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    internal interface DragStateChangeListener {
        fun onDragStateChanged(state: Int)
    }

    /**
     * Listener for monitoring events about swipe layout.
     */
    interface SwipeListener {
        /**
         * Called when the main view becomes completely closed.
         */
        fun onClosed(view: SwipeLayout)

        /**
         * Called when the main view becomes completely opened.
         */
        fun onOpened(view: SwipeLayout)

        /**
         * Called when the main view's position changes.
         * @param slideOffset The new offset of the main view within its range, from 0-1
         */
        fun onSlide(view: SwipeLayout, slideOffset: Float)
    }

    /**
     * No-op stub for [SwipeListener]. If you only want ot implement a subset
     * of the listener methods, you can extend this instead of implement the full interface.
     */
    class SimpleSwipeListener : SwipeListener {
        override fun onClosed(view: SwipeLayout) {}

        override fun onOpened(view: SwipeLayout) {}

        override fun onSlide(view: SwipeLayout, slideOffset: Float) {}
    }

}