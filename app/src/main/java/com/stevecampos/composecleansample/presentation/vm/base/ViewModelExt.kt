package com.stevecampos.composecleansample.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <R> ViewModel.executeUseCase(
    onSuccess: (R) -> Unit,
    onFailure: (Throwable) -> Unit,
    doUseCase: (
        () -> Flow<Result<R>>)
) {
    viewModelScope.launch {
        withContext(coroutineContext) {
            doUseCase.invoke().collect { result ->

                withContext(Dispatchers.Main) {

                    result.onFailure { exception ->
                        onFailure.invoke(exception)
                    }
                    result.onSuccess { data ->
                        onSuccess.invoke(data)
                    }

                }
            }
        }
    }
}