package com.carefer.core.utils

interface LoadingListener {
    fun showLoading(show: Boolean, isShimmer: Boolean = false)
}