package mcssoft.com.racereminderac.utility

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import mcssoft.com.racereminderac.interfaces.ISwipe

/**
 *  https://www.journaldev.com/23164/android-recyclerview-swipe-to-delete-undo
  * https://therubberduckdev.wordpress.com/2017/10/24/android-recyclerview-drag-and-drop-and-swipe-to-dismiss/
 **/
class TouchHelper(private val context: Context, private val swipeAction: ISwipe) : ItemTouchHelper.Callback() {

    private val background: ColorDrawable = ColorDrawable()
    private val backgroundColour: Int = Color.parseColor(Constants.DELETE_COLOUR)
    private val clearPaint: Paint = Paint()

    init {
        clearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // Used for drag and drop.
        return false
    }

    /**
     * ItemTouchHelper.Callback()
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        /** Note: Can't seem to get EventBus to work here. Likely something with the callback. **/
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