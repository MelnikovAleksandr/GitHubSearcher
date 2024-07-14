package ru.melnikov.githubsearcher.utils

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion

class PreferencesHelper(val sharedPreferences: SharedPreferences) {

    inline fun <reified T> observeKey(key: String): Flow<T> {
        val flow = MutableStateFlow<T>(getValue(key))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, k ->
            if (key == k) {
                flow.value = getValue(key)
            }
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        return flow
            .onCompletion { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    fun setValue(key: String, value: Any) {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> throw IllegalArgumentException("Unsupported value type")
        }
        editor.apply()
    }

    inline fun <reified T> getValue(key: String): T {
        return when (T::class) {
            String::class -> sharedPreferences.getString(key, "") as T
            Int::class -> sharedPreferences.getInt(key, -1) as T
            Boolean::class -> sharedPreferences.getBoolean(key, false) as T
            else -> throw IllegalArgumentException("Unsupported value type")
        }
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}