package com.stevecampos.composecleansample.data.remote

import com.stevecampos.composecleansample.data.DataSource
import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.remote.api.ApiService
import com.stevecampos.composecleansample.data.remote.api.base.emitApiAsResult

class RemoteDataSourceImpl(private val api: ApiService) : DataSource {
    override fun getUsers(request: GetUsersRequest) = emitApiAsResult { api.getUsers() }

    override fun getPosts(request: GetPostsRequest) =
        emitApiAsResult { api.getPosts(userId = request.userId) }
}