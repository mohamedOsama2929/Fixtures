package com.carefer.core.domain.usecase.base

interface ModelMapper<From, To> {
    fun convert(from: From?): To
}