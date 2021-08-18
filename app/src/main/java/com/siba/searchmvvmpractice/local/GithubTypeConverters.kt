package com.siba.searchmvvmpractice.local

import android.util.Log
import androidx.room.TypeConverter

object GithubTypeConverters {
    /**
     * TypeConverter
     */
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (exception: NumberFormatException) {
                    Log.e("$exception", "Cannot convert $it to number")
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }
}