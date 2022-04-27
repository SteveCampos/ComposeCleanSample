package com.stevecampos.composecleansample.domain.usecase

interface UseCase<in Params, out Type> {
    fun execute(params: Params): Type

    object None
}