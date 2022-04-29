package com.stevecampos.composecleansample.domain.usecase

import com.stevecampos.composecleansample.data.DataSource
import com.stevecampos.composecleansample.data.entities.GetUsersRequest
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(private val repository: DataSource) :
    UseCase<GetUsersRequest, Flow<Result<List<GetUsersResponse>>>> {
    override fun execute(params: GetUsersRequest) = repository.getUsers(params)
}