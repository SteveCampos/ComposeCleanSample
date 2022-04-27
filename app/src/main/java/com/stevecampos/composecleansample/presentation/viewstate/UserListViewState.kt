package com.stevecampos.composecleansample.presentation.viewstate

import com.stevecampos.composecleansample.data.entities.GetUsersResponse

sealed class UserListViewState {
    object LoadingUsersState : UserListViewState()
    object FailedLoadUsersState : UserListViewState()
    object NetworkErrorState : UserListViewState()

    data class SuccessAndNoFilterState(val items: List<GetUsersResponse>) : UserListViewState()

    data class SuccessAndFilteringState(val items: List<GetUsersResponse>, val filter: String) :
        UserListViewState()

    data class SuccessAndFilterProduceResultsState(
        val items: List<GetUsersResponse>,
        val filter: String,
        val filteredItems: List<GetUsersResponse>
    ): UserListViewState()

    data class SuccessAndFilterProduceEmptyResultState(
        val items: List<GetUsersResponse>,
        val filter: String
    ): UserListViewState()

}

