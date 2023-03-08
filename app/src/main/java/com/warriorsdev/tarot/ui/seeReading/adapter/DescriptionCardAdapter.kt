package com.warriorsdev.tarot.ui.seeReading.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warriorsdev.tarot.R
import com.warriorsdev.tarot.tools.TarotUtils
import com.warriorsdev.tarot.ui.theWay.model.ItemCard

class DescriptionCardAdapter(
    private val items: ArrayList<ItemCard>
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_description, parent, false)
        )

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView
        val ivDescription: ImageView
        val tvDescription: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            ivDescription = itemView.findViewById(R.id.ivDescription)
            tvDescription = itemView.findViewById(R.id.tvDescription)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = items[position]
            holder.tvTitle.text = item.title
            holder.ivDescription.setImageResource(TarotUtils.getResourceByName(item.image))
            holder.tvDescription.text = item.description
        }
    }

}
