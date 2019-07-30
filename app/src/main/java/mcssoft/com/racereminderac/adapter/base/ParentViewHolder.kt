package mcssoft.com.racereminderac.adapter.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

    override fun onClick(view: View) { }

    override fun onLongClick(view: View): Boolean { return true }
}