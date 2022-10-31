package com.example.data.storage.sh_prefs

import android.content.SharedPreferences

class SPrefStorageImpl(private val settings: SharedPreferences) : SPrefStorageInterface {
    override fun setQuerySearch(string: String) =
        settings.edit().putString(QUERY, string).apply()


    override fun getQuerySearch(): String =
        settings.getString(QUERY, DEFAULT_QUERY) ?: DEFAULT_QUERY

    override fun setLastQuerySearch(string: String) =
        settings.edit().putString(LAST_QUERY_SCROLLED, string).apply()


    override fun getLastQuerySearch(): String =
        settings.getString(LAST_QUERY_SCROLLED, DEFAULT_QUERY) ?: DEFAULT_QUERY


    companion object {
        private const val QUERY = "QUERY"
        private const val LAST_QUERY_SCROLLED: String = "LAST_QUERY"
        private const val DEFAULT_QUERY = "anime"
    }
}