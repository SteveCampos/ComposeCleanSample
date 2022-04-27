package com.stevecampos.composecleansample.domain.usecase

import com.stevecampos.composecleansample.data.DataSource
import com.stevecampos.composecleansample.data.entities.GetPostsRequest
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val repository: DataSource) :
    UseCase<GetPostsRequest, Flow<Result<List<GetPostsResponse>>>> {
    override fun execute(params: GetPostsRequest) = repository.getPosts(params)
}