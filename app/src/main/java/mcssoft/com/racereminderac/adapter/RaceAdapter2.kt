package mcssoft.com.racereminderac.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import mcssoft.com.racereminderac.entity.RaceDetails

/**
 * For Paging.
 */
class RaceAdapter2 : PagedListAdapter<RaceDetails, RaceViewHolder2>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RaceDetails>() {
            override fun areItemsTheSame(oldItem: RaceDetails, newItem: RaceDetails): Boolean =
                    oldItem.id == newItem.id
            /**
             * Note that in kotlin, == checking on data classes compares all contents.
             */
            override fun areContentsTheSame(oldItem: RaceDetails, newItem: RaceDetails): Boolean =
                    oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RaceViewHolder2, position: Int) {
        val race = getItem(position)
        if(race == null) {
            return
        } else {
            holder.bindTo(race, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder2 =
            RaceViewHolder2(parent)
}