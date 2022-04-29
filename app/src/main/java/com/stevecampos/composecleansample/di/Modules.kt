package com.stevecampos.composecleansample.di

import com.stevecampos.composecleansample.data.DataSource
import com.stevecampos.composecleansample.data.remote.RemoteDataSourceImpl
import com.stevecampos.composecleansample.data.remote.api.ApiService
import com.stevecampos.composecleansample.data.remote.api.base.BaseApiClient
import com.stevecampos.composecleansample.data.repository.RepositoryImpl
import com.stevecampos.composecleansample.domain.usecase.GetPostsUseCase
import com.stevecampos.composecleansample.domain.usecase.GetUsersUseCase
import com.stevecampos.composecleansample.presentation.vm.GetPostsViewModel
import com.stevecampos.composecleansample.presentation.vm.UserListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userListModule = module {
    single {
        androidApplication().resources
    }

    //Repository

    single<ApiService>(named("ApiClient")) {
        BaseApiClient<ApiService>(ApiService::class.java).getApiClient()
    }

    single(named("RemoteDataSourceImpl")) { RemoteDataSourceImpl(get(named("ApiClient"))) as DataSource }

    single(named("RepositoryImpl")) {
        RepositoryImpl(
            //get(named("LocalDataSourceImpl")),
            get(named("RemoteDataSourceImpl"))
        ) as DataSource
    }

    //UseCase

    factory(named("GetUsersUseCase")) { GetUsersUseCase(get(named("RepositoryImpl"))) }
    factory(named("GetPostsUseCase")) { GetPostsUseCase(get(named("RepositoryImpl"))) }

    //ViewModel
    viewModel {
        UserListViewModel(get(named("GetUsersUseCase")))
    }
    viewModel {
        GetPostsViewModel(get(named("GetPostsUseCase")))
    }



}