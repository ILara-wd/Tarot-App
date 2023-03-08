package com.warriorsdev.tarot.ui.theWay.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.warriorsdev.tarot.R
import com.warriorsdev.tarot.ui.theWay.model.ItemCard

class MazoCardAdapter(
    private val itemsList: ArrayList<ItemCard>,
    private var onItemClicked: ((cardTarot: ItemCard) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cards, parent, false)
        )

    override fun getItemCount(): Int = itemsList.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBackCard: ImageView

        init {
            ivBackCard = itemView.findViewById(R.id.ivBackCard)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = itemsList[position]
            holder.ivBackCard.layoutParams.width = 60
            holder.ivBackCard.scaleType = ImageView.ScaleType.MATRIX
            holder.ivBackCard.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    fun removeCard(item: ItemCard) {
        itemsList.remove(item)
        notifyDataSetChanged()
    }

}
