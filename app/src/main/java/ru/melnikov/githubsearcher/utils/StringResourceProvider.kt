package ru.melnikov.githubsearcher.utils

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

class StringResourceProvider(private val context: Context) {

    fun getString(@StringRes resourceId: Int, vararg arguments: Any): String {
        return context.getString(resourceId, *arguments)
    }

    fun getArray(@ArrayRes resourceId: Int): List<String> {
        return context.resources.getStringArray(resourceId).toList()
    }

}