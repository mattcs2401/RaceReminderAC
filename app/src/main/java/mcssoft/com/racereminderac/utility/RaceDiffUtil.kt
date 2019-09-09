package mcssoft.com.racereminderac.utility

import androidx.recyclerview.widget.DiffUtil

class RaceDiffUtil : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //
        return true
    }

    override fun getOldListSize(): Int {
        //
        return 0
    }

    override fun getNewListSize(): Int {
        //
        return 0
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        //
        return true
    }
}