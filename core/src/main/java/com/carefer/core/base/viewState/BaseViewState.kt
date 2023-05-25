package com.carefer.core.base.viewState


sealed class
BaseViewState {
    object Idle : BaseViewState()
    object Loading : BaseViewState()
    data class Error(val error: String?) : BaseViewState()
    data class OnSuccess(val data: Any) : BaseViewState()

}
