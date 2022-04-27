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
        _viewState.value = UserListViewState.SuccessGetUsersState(items)
    }
}