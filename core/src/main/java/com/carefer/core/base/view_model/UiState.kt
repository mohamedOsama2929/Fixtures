package com.carefer.core.base.view_model

import com.carefer.core.domain.entities.base.ErrorModel


sealed class UiState {
    object Idle : UiState()
    data class Loading(val Loading: Boolean, val isShimmer: Boolean = false) : UiState()
    data class Error(val Error: ErrorModel) : UiState()
    data class CancellationMessage(val CancellationMessage: String) : UiState()

}
