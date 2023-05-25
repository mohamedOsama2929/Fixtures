package com.carefer.core.base.view_model


sealed class ApiState<out T> {
    object Idle : ApiState<Nothing>()
    data class Success<T>(val successData: T) : ApiState<T>() {
        override fun equals(other: Any?): Boolean = (other is Success<*>)
                && successData == other.successData
                && successData.hashCode() == other.successData.hashCode()

        override fun hashCode(): Int {
            return successData?.hashCode() ?: 0
        }

    }
}
