package mcssoft.com.racereminderac.utility

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import mcssoft.com.racereminderac.R
import mcssoft.com.racereminderac.interfaces.ISwipe

/** https://www.journaldev.com/23164/android-recyclerview-swipe-to-delete-undo
    https://therubberduckdev.wordpress.com/2017/10/24/android-recyclerview-drag-and-drop-and-swipe-to-dismiss/
 **/
class TouchHelper(context: Context, swipeAction: ISwipe) : ItemTouchHelper.Callback() {

    private val swipeAction: ISwipe
    private val context: Context
    private val background: ColorDrawable
    private val backgroundColour: Int
    private val clearPaint: Paint
    private val deleteDrawable: Drawable
    private val intrinsicWidth: Int
    private val intrinsicHeight: Int

    init {
        this.swipeAction = swipeAction
        this.context = context

        background = ColorDrawable()
        backgroundColour = Color.parseColor("#b80f0a")
        clearPaint = Paint()
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        deleteDrawable = ContextCompat.getDrawable(context, R.drawable.ic_delete)!!
        intrinsicWidth = deleteDrawable.intrinsicWidth
        intrinsicHeight = deleteDrawable.intrinsicHeight
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // Used for drag and drop.
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeAction.onViewSwiped(viewHolder.adapterPosition)
    }

    override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = dX.equals(0) && !isCurrentlyActive

        if (isCancelled) {
            clearCanvas(canvas, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        background.color = backgroundColour
        background.setBounds((itemView.right  + dX).toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(canvas)

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight


        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteDrawable.draw(canvas)

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        // This means if the row is swiped less than 70%, the onSwipe method won’t be triggered.
        return 0.7f
    }

    override fun isLongPressDragEnabled(): Boolean {
//        return super.isLongPressDragEnabled()
        return false
    }

    private fun clearCanvas(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        canvas.drawRect(left, top, right, bottom, clearPaint)
    }

}