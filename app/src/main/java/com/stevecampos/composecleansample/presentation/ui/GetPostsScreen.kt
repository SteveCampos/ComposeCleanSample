package com.stevecampos.composecleansample.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stevecampos.composecleansample.R
import com.stevecampos.composecleansample.data.entities.GetPostsResponse
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.presentation.viewstate.GetPostsViewState
import com.stevecampos.composecleansample.presentation.vm.GetPostsViewModel

@Composable
fun GetPostsScreen(
    navController: NavController,
    user: GetUsersResponse,
    viewModel: GetPostsViewModel
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.activity_get_posts_msg_posts_from,
                            user.name
                        )
                    )
                },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }
    ) {
        GetPostsBodyContent(user, viewModel)
    }
}

@Composable
fun GetPostsBodyContent(user: GetUsersResponse, viewModel: GetPostsViewModel) {

    Column {
        UserItem(
            user = user,
            showBttn = false
        )
        GetPostsContent(user, viewModel)
    }
}

@Composable
fun GetPostsContent(user: GetUsersResponse, viewModel: GetPostsViewModel) {
    val uiState = viewModel.viewState.observeAsState(GetPostsViewState.FailedGetPostsState)

    val posibleStates = uiState.value

    when (posibleStates) {
        is GetPostsViewState.LoadingGetPostsState -> LoadingGetPostsWidget()
        is GetPostsViewState.FailedGetPostsState -> FailedLoadGetPostsWidget(user, viewModel)
        is GetPostsViewState.NetworkErrorState -> NetworkErrorGetPostsWidget(user, viewModel)
        is GetPostsViewState.SuccessGetPostsState -> SuccessGetPostsWidget(posibleStates.items)
    }
}

@Composable
fun SuccessGetPostsWidget(posts: List<GetPostsResponse>) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
        Text(
            text = stringResource(R.string.activity_get_posts_msg_posts),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )
        LazyColumn {
            items(posts) { post ->
                PostItem(post)
            }
        }
    }
}

@Composable
fun PostItem(post: GetPostsResponse) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(),
        elevation = 4.dp
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )
            Text(text = post.body, style = MaterialTheme.typography.body1)
        }
    }

}

@Composable
fun NetworkErrorGetPostsWidget(user: GetUsersResponse, viewModel: GetPostsViewModel) {
    NetworkErrorWidget(
        errorTxt = stringResource(R.string.activity_get_posts_msg_network_error),
        onRefreshBttnClicked = { viewModel.loadPosts(user.id) }
    )
}

@Composable
fun FailedLoadGetPostsWidget(user: GetUsersResponse, viewModel: GetPostsViewModel) {
    FailedToLoadWidget(
        errorTxt = stringResource(R.string.activity_get_posts_msg_failed_to_get_posts),
        onRefreshBttnClicked = { viewModel.loadPosts(user.id) })
}

@Composable
fun LoadingGetPostsWidget() {
    LoadingWidget()
}
