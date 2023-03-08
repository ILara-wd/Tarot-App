package com.warriorsdev.tarot.ui.theWay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TarotCards(
    val cards: ArrayList<ItemCard>
) : Parcelable

@Parcelize
data class ItemCard(
    val title: String,
    val description: String,
    val image: String,
) : Parcelable
