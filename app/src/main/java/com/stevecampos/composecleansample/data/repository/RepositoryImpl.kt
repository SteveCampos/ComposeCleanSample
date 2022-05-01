package com.stevecampos.composecleansample.data.repository

import com.stevecampos.composecleansample.data.DataSource
import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.data.local.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val local: LocalDataSource,
    private val remote: DataSource
) : DataSource {
    override fun getUsers(request: GetUsersRequest): Flow<Result<List<GetUsersResponse>>> = flow {
        local.getUsers(request).collect { localUsers ->
            if (localUsers.isNotEmpty()) {
                emit(Result.success(localUsers))
            } else {
                remote.getUsers(request).collect {
                    if (it.isSuccess && it.getOrNull() != null) {
                        local.addUsers(it.getOrNull()!!)
                    }
                    emit(it)
                }
            }

        }
    }


    override fun getPosts(request: GetPostsRequest): Flow<Result<List<GetPostsResponse>>>
            /*{
                return remote.getPosts(request)
            }*/ = flow {

        local.getPosts(request).collect { localPosts ->
            if (localPosts.isNotEmpty()) {
                emit(Result.success(localPosts))
            } else {
                remote.getPosts(request).collect {
                    if (it.isSuccess && it.getOrNull() != null) {
                        local.addPosts(it.getOrNull()!!)
                    }
                    emit(it)
                }
            }
        }
    }
}