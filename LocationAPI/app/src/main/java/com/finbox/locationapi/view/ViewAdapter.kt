package com.finbox.locationapi.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.finbox.locationapi.databinding.ItemLayoutBinding
import com.finbox.locationapi.model.Location
import com.finbox.locationapi.utils.Constants
import com.finbox.locationapi.utils.ItemDiffCallback
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*
import kotlin.collections.ArrayList


class ViewAdapter(
    private val locationList: ArrayList<Location>
) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {


    private val diff by lazy {
        ItemDiffCallback()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = locationList[position]
        holder.mView.apply {
            title_text.text = String.format("Location %s", position + 1)
            lats.text = String.format("Lat %s", item.latitude)
            longs.text = String.format("Long %s", item.longitude)
            time_stamp.text = String.format("Updated on: %s",item.convertTime())
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }


    override fun getItemCount(): Int = locationList.size
    fun setData(newList: List<Location>) {
        val diffResult = DiffUtil.calculateDiff(
            diff.apply {
                setList(
                    locationList,
                    newList
                )
            }
        )
        locationList.run {
            clear()
            addAll(newList)
        }
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView)


}
