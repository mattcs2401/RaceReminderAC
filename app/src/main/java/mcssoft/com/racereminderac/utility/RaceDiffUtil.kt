package mcssoft.com.racereminderac.utility

import androidx.recyclerview.widget.DiffUtil
import mcssoft.com.racereminderac.entity.RaceDetails

// Based largely on:
// https://android.jlelse.eu/smart-way-to-update-recyclerview-using-diffutil-345941a160e0

class RaceDiffUtil(private val oldList: ArrayList<RaceDetails>,
                   private val newList: ArrayList<RaceDetails>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int  = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        // Implement method if you're going to use ItemAnimator
//        return super.getChangePayload(oldItemPosition, newItemPosition)
//    }
}