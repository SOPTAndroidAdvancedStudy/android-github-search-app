package com.siba.searchmvvmpractice.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

object ImageLoader {
    private val imageCache = mutableMapOf<String, Bitmap>()

    suspend fun loadImageWithSuspendModifier(url: String, completed: (Bitmap?) -> Unit) {
        if (url.isEmpty()) {
            completed(null)
            return
        }

        if (imageCache.containsKey(url)) {
            completed(imageCache[url])
            return
        }

        withContext(Dispatchers.IO) {
            try {
                val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
                imageCache[url] = bitmap

                withContext(Dispatchers.Main) {
                    completed(bitmap)
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    completed(null)
                }
            }
        }
    }

    fun loadImageWithoutSuspendModifier(url: String, completed: (Bitmap?) -> Unit) {
        if (url.isEmpty()) {
            completed(null)
            return
        }

        if (imageCache.containsKey(url)) {
            completed(imageCache[url])
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
                imageCache[url] = bitmap

                withContext(Dispatchers.Main) {
                    completed(bitmap)
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    completed(null)
                }
            }
        }
    }
}