package com.stevecampos.composecleansample.domain.usecase.base

interface UseCase<in Params, out Type> {
    fun execute(params: Params): Type

    object None
}