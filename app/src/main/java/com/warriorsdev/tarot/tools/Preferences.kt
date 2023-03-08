package com.warriorsdev.tarot.tools

import android.content.Context
import android.content.SharedPreferences
import com.warriorsdev.tarot.tools.AppConstants.TAROT_SHARED
import com.warriorsdev.tarot.tools.AppConstants.READING_COUNT

class Preferences(val context: Context) {

    private val storage: SharedPreferences = context.getSharedPreferences(
        TAROT_SHARED, Context.MODE_PRIVATE
    )

    fun updateReading() {
        val countUpdate = getReading() + 1
        storage.edit().putInt(READING_COUNT, countUpdate).apply()
    }

    fun getReading(): Int {
        return storage.getInt(READING_COUNT, 0)
    }
}

object AppConstants {
    val TAROT_SHARED = "com.warriorsdev.tarot"
    val READING_COUNT = "reading"
}