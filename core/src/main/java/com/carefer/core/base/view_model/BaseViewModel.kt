package com.carefer.core.base.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carefer.core.base.fragment.NetworkBaseFragment
import com.carefer.core.domain.entities.base.ErrorModel
import com.carefer.core.domain.entities.base.ErrorStatus
import com.carefer.core.domain.usecase.base.CompletionBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state

    fun setLoading(isLoading: Boolean, isShimmer: Boolean = false) {
        _state.value = UiState.Loading(isLoading, isShimmer)
    }

    fun setErrorReason(errorModel: ErrorModel) {
        _state.value = UiState.Error(errorModel)
    }

    fun setCancellationReason(cancellationException: CancellationException) {
        _state.value = UiState.CancellationMessage(cancellationException.message ?: "")
    }

    fun <T> callApi(data: MutableStateFlow<T>, apiCall: (CompletionBlock<T>) -> Unit) {
        if (NetworkBaseFragment.isNetworkConnected) {
            apiCall.invoke {
                isLoading {
                    _state.value = UiState.Loading(it)
                }

                onComplete {
                    ApiState.Success(it)
                    data.value = it
                }
                onError { throwable ->
                    _state.value = UiState.Error(throwable)
                }
                onCancel {
                    _state.value = UiState.CancellationMessage(it.message ?: "")
                }
            }
        } else {
            _state.value = UiState.Error(
                ErrorModel(
                    "No internet connection",
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            )
        }
    }

    fun <T> callApi(data: MutableSharedFlow<T>, apiCall: (CompletionBlock<T>) -> Unit) {
        if (NetworkBaseFragment.isNetworkConnected) {
            apiCall.invoke {
                isLoading {
                    _state.value = UiState.Loading(it)
                }

                onComplete {
                    ApiState.Success(it)
                    viewModelScope.launch {
                        data.emit(it)
                    }
                }
                onError { throwable ->
                    _state.value = UiState.Error(throwable)
                }
                onCancel {
                    _state.value = UiState.CancellationMessage(it.message ?: "")
                }
            }
        } else {
            _state.value = UiState.Error(
                ErrorModel(
                    "No internet connection",
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            )
        }
    }

    fun <T> callApiWithApiState(
        data: MutableStateFlow<ApiState<T>>,
        isShimmer: Boolean = false,
        apiCall: (CompletionBlock<ApiState<T>>) -> Unit
    ) {
        if (NetworkBaseFragment.isNetworkConnected) {
            apiCall.invoke {
                isLoading {
                    // setLoading(it)
                    _state.value = UiState.Loading(it, isShimmer)
                }

                onComplete {
                    if (ApiState.Success(it).successData.equals(data.value)) {
                        ApiState.Idle
                        data.value = ApiState.Idle
                    } else {
                        ApiState.Success(it)
                        data.value = it
                    }
                }
                onError { throwable ->
                    _state.value = UiState.Error(throwable)
                }
                onCancel {
                    _state.value = UiState.CancellationMessage(it.message ?: "")
                }
            }
        } else {
            _state.value = UiState.Error(
                ErrorModel(
                    "No internet connection",
                    0,
                    ErrorStatus.NO_CONNECTION
                )
            )
        }
    }

    open fun reset() {}

    open fun handelViewIntent() {}
}