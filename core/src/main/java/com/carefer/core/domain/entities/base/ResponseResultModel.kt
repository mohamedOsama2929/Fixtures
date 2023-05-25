package com.carefer.core.domain.entities.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseResultModel<T>(
    @Expose
    @SerializedName("data")
    val data: T
)