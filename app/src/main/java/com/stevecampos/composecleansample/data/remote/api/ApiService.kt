package com.stevecampos.composecleansample.data.remote.api

import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): Response<List<GetUsersResponse>>

    @GET("/posts")
    suspend fun getPosts(@Query("userId") userId: Int): Response<List<GetPostsResponse>>
}