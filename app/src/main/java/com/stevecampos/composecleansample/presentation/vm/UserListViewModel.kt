package com.stevecampos.composecleansample.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.domain.usecase.GetUsersUseCase
import com.stevecampos.composecleansample.presentation.viewstate.UserListViewState
import java.io.IOException

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel(), UserListActions {


    private val _viewState = MutableLiveData<UserListViewState>()
    val viewState: LiveData<UserListViewState> = _viewState

    init {
        loadUsers()
    }

    override fun loadUsers() {
        _viewState.value = UserListViewState.LoadingUsersState
        executeUseCase(
            ::onGetUsersSuccess,
            ::onGetUsersFailed
        ) {
            getUsersUseCase.execute(GetUsersRequest())
        }
    }

    private fun onGetUsersFailed(t: Throwable) {
        val state = when (t) {
            is IOException -> {
                UserListViewState.NetworkErrorState
            }
            else -> {
                UserListViewState.FailedLoadUsersState
            }
        }
        _viewState.value = state
    }

    private fun onGetUsersSuccess(items: List<GetUsersResponse>) {
        _viewState.value = UserListViewState.SuccessAndNoFilterState(items)
    }

    private fun changeToSuccessAndNoFilterState(items: List<GetUsersResponse>) {
        _viewState.value = UserListViewState.SuccessAndNoFilterState(items)
    }

    private fun changeToSuccessAndFilteringState(
        actualItems: List<GetUsersResponse>,
        filter: String
    ) {
        _viewState.value = UserListViewState.SuccessAndFilteringState(actualItems, filter)
    }

    private fun changeToSuccessAndFilterProduceEmptyResultState(
        actualItems: List<GetUsersResponse>,
        filter: String
    ) {
        _viewState.value = UserListViewState.SuccessAndFilterProduceEmptyResultState(
            actualItems,
            filter
        )
    }

    private fun changeToSuccessAndFilterProduceResultsState(
        actualItems: List<GetUsersResponse>,
        filter: String,
        filteredItems: List<GetUsersResponse>
    ) {
        _viewState.value = UserListViewState.SuccessAndFilterProduceResultsState(
            actualItems,
            filter,
            filteredItems
        )
    }


    override fun onInputSearchUserChanged(filter: String) {
        val actualState = _viewState.value ?: return

        val actualItems = when (actualState) {
            is UserListViewState.SuccessAndNoFilterState -> actualState.items
            is UserListViewState.SuccessAndFilterProduceResultsState -> actualState.items
            is UserListViewState.SuccessAndFilterProduceEmptyResultState -> actualState.items
            is UserListViewState.SuccessAndFilteringState -> actualState.items
            else -> listOf()
        }

        if (filter.isBlank()) {
            changeToSuccessAndNoFilterState(actualItems)
            return
        }

        changeToSuccessAndFilteringState(actualItems, filter)

        val filteredItems = actualItems.filter {
            it.name.contains(filter)
        }

        if (filteredItems.isEmpty()) {
            changeToSuccessAndFilterProduceEmptyResultState(actualItems, filter)
        } else {
            changeToSuccessAndFilterProduceResultsState(actualItems, filter, filteredItems)
        }
    }

}