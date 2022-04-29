package com.stevecampos.composecleansample.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.domain.usecase.GetPostsUseCase
import com.stevecampos.composecleansample.presentation.viewstate.GetPostsViewState
import java.io.IOException

class GetPostsViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel(), GetPostsActions {


    private val _viewState = MutableLiveData<GetPostsViewState>()
    val viewState: LiveData<GetPostsViewState> = _viewState

    init {

    }

    override fun loadPosts(userId: Int) {
        _viewState.value = GetPostsViewState.LoadingGetPostsState
        executeUseCase(
            ::onGetPostsSuccess,
            ::onGetPostsFailed
        ) {
            getPostsUseCase.execute(GetPostsRequest(userId))
        }
    }

    private fun onGetPostsFailed(t: Throwable) {
        val state = when (t) {
            is IOException -> {
                GetPostsViewState.NetworkErrorState
            }
            else -> {
                GetPostsViewState.FailedGetPostsState
            }
        }
        _viewState.value = state
    }

    private fun onGetPostsSuccess(items: List<GetPostsResponse>) {
        _viewState.value = GetPostsViewState.SuccessGetPostsState(items)
    }
}