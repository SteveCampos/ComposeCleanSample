package com.stevecampos.composecleansample.presentation.ui

import android.os.Bundle
import com.stevecampos.composecleansample.R
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.presentation.ui.theme.ComposeCleanSampleTheme
import com.stevecampos.composecleansample.presentation.viewstate.UserListViewState
import com.stevecampos.composecleansample.presentation.vm.UserListViewModel
import org.koin.androidx.compose.get

class UserListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCleanSampleTheme {
                UserListScreen()
            }
        }
    }
}

@Composable
fun UserListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier, viewModel: UserListViewModel = get()) {
    val uiState = viewModel.viewState.observeAsState(UserListViewState.LoadingUsersState)

    val posibleStates = uiState.value

    when (posibleStates) {
        is UserListViewState.LoadingUsersState -> LoadingUsersWidget()
        is UserListViewState.FailedLoadUsersState -> FailedLoadUsersWidget()
        is UserListViewState.NetworkErrorState -> NetworkErrorWidget()
        is UserListViewState.SuccessAndNoFilterState -> UserList(users = posibleStates.items)
        else -> NotImplementedWidget()
    }
}

@Composable
fun NotImplementedWidget() {
    Text(text = "Not implemented yet!")
}

@Composable
fun NetworkErrorWidget() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.ic_embarrassed),
            contentDescription = stringResource(id = R.string.activity_list_user_msg_network_error),
            modifier = Modifier.fillMaxSize(.4f)
        )
        Text(text = stringResource(id = R.string.activity_list_user_msg_network_error))
    }
}

@Composable
fun FailedLoadUsersWidget() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.ic_embarrassed),
            contentDescription = stringResource(id = R.string.activity_list_user_msg_failed_to_load_users),
            modifier = Modifier.fillMaxSize(.4f)
        )
        Text(text = stringResource(id = R.string.activity_list_user_msg_failed_to_load_users))
    }
}

@Composable
fun LoadingUsersWidget() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Composable
fun UserList(users: List<GetUsersResponse>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(users) { user ->
            UserItem(user = user)
        }
    }
}

@Composable
fun UserItem(user: GetUsersResponse) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxSize(),
        elevation = 4.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = user.name,
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.h6
            )
            Row {
                Icon(
                    Icons.Outlined.Phone,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(id = R.string.activity_list_user_cd_phone)
                )
                Text(text = user.phone, modifier = Modifier.padding(start = 8.dp))
            }
            Row {
                Icon(
                    Icons.Outlined.Email,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = stringResource(id = R.string.activity_list_user_cd_email)
                )
                Text(text = user.email, modifier = Modifier.padding(start = 8.dp))
            }
            TextButton(modifier = Modifier.align(Alignment.End), onClick = { }) {
                Text(
                    text = stringResource(id = R.string.activity_list_user_msg_show_post).toUpperCase(
                        Locale.current
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeCleanSampleTheme {
        UserListScreen()
    }
}