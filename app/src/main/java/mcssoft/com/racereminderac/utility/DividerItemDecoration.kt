package mcssoft.com.racereminderac.utility

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mcssoft.com.racereminderac.R

class DividerItemDecoration : RecyclerView.ItemDecoration {

    private var context: Context

    constructor(context: Context){
        this.context = context
        DividerItemDecoration(context, VERTICAL_LIST, -1, -1)
    }

    constructor(context: Context, orientation: Int) {
        this.context = context
        DividerItemDecoration(context, orientation, -1, -1)
    }

    constructor(context: Context, orientation: Int, padding: Int, dividerHeight: Int) : this(context) {
        this.context = context
        setOrientation(orientation)

        initialise()
        if (padding != -1) this.padding = padding
        updatePadding()
        if (dividerHeight != -1) this.dividerHeight = dividerHeight
    }

//    fun DividerItemDecoration(context: Context, orientation: Int, startpadding: Int, endpadding: Int, dividerHeight: Int): ??? {
//        setOrientation(orientation)
//        mContext = context
//
//        init()
//        if (startpadding != -1) this.startpadding = startpadding
//        if (endpadding != -1) this.endpadding = endpadding
//        if (dividerHeight != -1) this.dividerHeight = dividerHeight
//    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)
        if (orientation == VERTICAL_LIST) {
            drawVertical(canvas, parent)
        } else {
            drawHorizontal(canvas, parent)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (orientation == VERTICAL_LIST) {
            if (parent.getChildAdapterPosition(view) != parent.adapter!!.getItemCount() - 1) {
                outRect.set(0, 0, 0, dividerHeight)
            } else {
                outRect.set(0, 0, 0, 0)
            }
        } else {
            if (parent.getChildAdapterPosition(view) != parent.adapter!!.getItemCount() - 1) {
                outRect.set(0, 0, dividerHeight, 0)
            } else {
                outRect.set(0, 0, 0, 0)
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child))
            val bottom = top + dividerHeight

            canvas.drawRect(left.toFloat(), top.toFloat(), (left + startPadding).toFloat(), bottom.toFloat(), paddingPaint)
            canvas.drawRect((right - endPadding).toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paddingPaint)
            canvas.drawRect((left + startPadding).toFloat(), top.toFloat(), (right - endPadding).toFloat(), bottom.toFloat(), dividerPaint)
        }
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child))
            val right = left + dividerHeight
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), (top + startPadding).toFloat(), paddingPaint)
            canvas.drawRect(left.toFloat(), (bottom - endPadding).toFloat(), right.toFloat(), bottom.toFloat(), paddingPaint)
            canvas.drawRect(left.toFloat(), (top + startPadding).toFloat(), right.toFloat(), (bottom - endPadding).toFloat(), dividerPaint)
        }
    }

    private fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        this.orientation = orientation
    }

    private fun initialise() {
        padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        updatePadding()
        dividerHeight = DEFAULT_DIVIDER_HEIGHT

        paddingPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        paddingPaint!!.setColor(ContextCompat.getColor(context, android.R.color.white))
        paddingPaint!!.setStyle(Paint.Style.FILL)

        dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        dividerPaint!!.setColor(ContextCompat.getColor(context, android.R.color.darker_gray))
        dividerPaint!!.setStyle(Paint.Style.FILL)
    }

    private fun updatePadding() {
        startPadding = padding
        endPadding = padding
    }

    private var startPadding: Int = -1
    private var endPadding: Int = -1
    private var padding: Int = -1
    private var dividerHeight: Int = -1
    private var orientation: Int = -1

    private var paddingPaint: Paint? = null
    private var dividerPaint: Paint? = null

    private val DEFAULT_DIVIDER_HEIGHT = 1
    private val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL
    private val VERTICAL_LIST = LinearLayoutManager.VERTICAL
}