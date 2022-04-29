package com.stevecampos.composecleansample.data.local

import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getUsers(request: GetUsersRequest): Flow<List<GetUsersResponse>>
    suspend fun addUsers(users: List<GetUsersResponse>)
}