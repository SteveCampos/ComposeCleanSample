package com.stevecampos.composecleansample.data.local

import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.data.local.orm.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val userDao: UserDao) : LocalDataSource {
    override fun getUsers(request: GetUsersRequest): Flow<List<GetUsersResponse>> {
        return userDao.getUsers()
    }

    override suspend fun addUsers(users: List<GetUsersResponse>) {
        return userDao.addUsers(users)
    }

}