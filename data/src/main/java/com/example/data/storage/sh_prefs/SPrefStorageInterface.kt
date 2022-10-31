package com.example.data.storage.sh_prefs

interface SPrefStorageInterface {
    fun setQuerySearch(string: String)
    fun getQuerySearch(): String
    fun setLastQuerySearch(string: String)
    fun getLastQuerySearch(): String
}