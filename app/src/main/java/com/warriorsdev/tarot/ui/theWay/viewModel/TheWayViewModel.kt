package com.warriorsdev.tarot.ui.theWay.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.warriorsdev.tarot.tools.TarotUtils
import com.warriorsdev.tarot.ui.theWay.model.ItemCard
import com.warriorsdev.tarot.ui.theWay.model.TarotCards

class TheWayViewModel(application: Application) : AndroidViewModel(application) {

    private val _cardsTarot = MutableLiveData<ArrayList<ItemCard>>().apply {
        value = getData()
    }

    val cardsTarot: LiveData<ArrayList<ItemCard>> = _cardsTarot

    private fun getData(): ArrayList<ItemCard> {
        val json =
            TarotUtils.readFile(getApplication<Application>().applicationContext, "cards.json")
        val mutableList =
            Gson().fromJson(json, TarotCards::class.java)
                .cards as MutableList<ItemCard>
        mutableList.shuffle()
        return mutableList as ArrayList<ItemCard>
    }

    fun search(nameCard: String): ItemCard? = getData().firstOrNull { it.title == nameCard }

}
