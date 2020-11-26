package com.example.rafael.chat.shared

import android.content.Context
import android.content.SharedPreferences

class UserPref(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(
            "rtgsa64s6*jnksf",
            Context.MODE_PRIVATE)

    fun set(key: String, value: Any?) {
        val editor = sharedPref.edit()

        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Double -> editor.putFloat(key, value.toFloat())
        }

        editor.commit()
    }

    @JvmOverloads
    fun getString(key: String, strdefault: String = ""): String? {
        return sharedPref.getString(key, strdefault)
    }

    fun getInt(key: String, valuedefault: Int): Int {
        return sharedPref.getInt(key, valuedefault)
    }

    fun getLong(key: String, valuedefault: Long): Long {
        return sharedPref.getLong(key, valuedefault)
    }

    fun getBoolean(key: String, defaultvalue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultvalue)
    }

    fun getFloat(key: String, valuedefault: Float): Float {
        return sharedPref.getFloat(key, valuedefault)
    }

    fun remove(key: String) {
        val editor = sharedPref.edit()
        editor.remove(key)

        editor.commit()
    }

    fun clean() {
        val editor = sharedPref.edit()
        editor.clear()

        editor.commit()
    }
}