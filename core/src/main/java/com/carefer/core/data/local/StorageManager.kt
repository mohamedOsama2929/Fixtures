package com.carefer.core.data.local

interface StorageManager {
    var accessToken: String
    fun clearUserData()
}