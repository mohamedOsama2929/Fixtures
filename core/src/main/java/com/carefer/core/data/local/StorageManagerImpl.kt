package com.carefer.core.data.local

import com.carefer.core.data.local.Config.ACCESS_TOKEN_KEY
import com.carefer.core.data.local.Config.FAV_MATCHES
import com.orhanobut.hawk.Hawk
import javax.inject.Inject

class StorageManagerImpl @Inject constructor() : StorageManager {
    override var accessToken: String
        get() = Hawk.get(ACCESS_TOKEN_KEY, "")
        set(value) {
            Hawk.put(ACCESS_TOKEN_KEY, value)
        }
    override var favMatches: MutableList<String>
        get() = Hawk.get(FAV_MATCHES, mutableListOf())
        set(value) {
            Hawk.put(FAV_MATCHES, value)
        }

    override fun clearUserData() {
        Hawk.deleteAll();
    }
}