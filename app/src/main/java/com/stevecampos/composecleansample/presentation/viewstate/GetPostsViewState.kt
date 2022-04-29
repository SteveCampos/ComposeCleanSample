package com.stevecampos.composecleansample.presentation.viewstate

import com.stevecampos.composecleansample.data.entities.GetPostsResponse

sealed class GetPostsViewState {
    object LoadingGetPostsState : GetPostsViewState()
    object FailedGetPostsState : GetPostsViewState()
    object NetworkErrorState : GetPostsViewState()
    data class SuccessGetPostsState(val items: List<GetPostsResponse>) : GetPostsViewState()
}

