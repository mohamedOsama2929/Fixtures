package com.carefer.core.data.local

interface StorageManager {
    var accessToken: String
    var favMatches: MutableList<String>
    fun clearUserData()
}