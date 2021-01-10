package com.finbox.locationapi.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.finbox.locationapi.model.Location

class ItemDiffCallback : DiffUtil.Callback() {
    private lateinit var oldList: List<Location>
    private lateinit var newList: List<Location>
    fun setList(oldList: List<Location>, newList: List<Location>){
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]._id == newList[newItemPosition]._id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].latitude == newList[newPosition].latitude && oldList[oldPosition].longitude == newList[newPosition].longitude
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}