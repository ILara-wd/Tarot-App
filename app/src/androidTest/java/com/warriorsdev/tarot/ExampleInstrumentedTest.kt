package com.warriorsdev.tarot

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.warriorsdev.tarot.tools.TarotUtils
import com.warriorsdev.tarot.ui.theWay.CardTarot
import com.warriorsdev.tarot.ui.theWay.ItemCard

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.warriorsdev.tarot", appContext.packageName)
    }

    @Test
    fun shuffle() {
        val json = TarotUtils.readFile(
            InstrumentationRegistry.getInstrumentation().targetContext,
            "cards.json"
        )
        val tempList = Gson().fromJson(json, ItemCard::class.java).cards as MutableList<CardTarot>
        val mutableList =
            Gson().fromJson(json, ItemCard::class.java).cards as MutableList<CardTarot>
        mutableList.shuffle()
        assertNotEquals(mutableList, tempList)
    }

}