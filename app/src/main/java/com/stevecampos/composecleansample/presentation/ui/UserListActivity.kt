package com.stevecampos.composecleansample.presentation.ui

import android.net.Uri
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stevecampos.composecleansample.data.entities.GetUsersResponse
import com.stevecampos.composecleansample.presentation.ui.theme.ComposeCleanSampleTheme
import com.stevecampos.composecleansample.presentation.viewstate.UserListViewState
import com.stevecampos.composecleansample.presentation.vm.UserListViewModel
import org.koin.androidx.compose.get
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toLowerCase
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.stevecampos.composecleansample.presentation.ui.nav.UserNavType
import com.stevecampos.composecleansample.presentation.vm.GetPostsViewModel
import org.koin.androidx.compose.viewModel

class UserListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCleanSampleTheme {
                MyComposableApp()
            }
        }
    }
}

@Composable
fun MyComposableApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ComposeAppScreens.UserListScreen.route
    ) {
        composable(route = ComposeAppScreens.UserListScreen.route) {
            UserListScreen(navController = navController)
        }
        composable(
            route = ComposeAppScreens.GetPostsScreen.route + "{user}",
            arguments = listOf(navArgument("user") { type = UserNavType() })
        ) {
            val user = it.arguments?.getParcelable<GetUsersResponse>("user") ?: return@composable
            val postsViewModel: GetPostsViewModel by viewModel()
            postsViewModel.loadPosts(user.id)
            GetPostsScreen(navController = navController, user = user, postsViewModel)
        }

    }
}

@Composable
fun UserListScreen(navController: NavController) {
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
    ) {
        BodyContent(navController)
    }
}

@Composable
fun BodyContent(navController: NavController, viewModel: UserListViewModel = get()) {
    val uiState = viewModel.viewState.observeAsState(UserListViewState.LoadingUsersState)

    val posibleStates = uiState.value

    when (posibleStates) {
        is UserListViewState.LoadingUsersState -> LoadingUsersWidget()
        is UserListViewState.FailedLoadUsersState -> FailedLoadUsersWidget(viewModel)
        is UserListViewState.NetworkErrorState -> NetworkErrorUserListWidget(viewModel)
        is UserListViewState.SuccessGetUsersState -> SuccessGetUsers(
            navController = navController,
            originalItems = posibleStates.items
        )
    }
}

@Composable
fun EmptyResultsWidget() {
    Column {
        Box(Modifier.fillMaxSize()) {
            Text(
                stringResource(R.string.activity_list_user_msg_empty_filter_results),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun NetworkErrorUserListWidget(viewModel: UserListViewModel) {
    NetworkErrorWidget(
        errorTxt = stringResource(R.string.activity_list_user_msg_network_error),
        onRefreshBttnClicked = { viewModel.loadUsers() })
}

@Composable
fun NetworkErrorWidget(errorTxt: String, onRefreshBttnClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.ic_embarrassed),
            contentDescription = errorTxt,
            modifier = Modifier.fillMaxSize(.4f)
        )
        Text(text = errorTxt)
        TextButton(onClick = { onRefreshBttnClicked.invoke() }) {
            Text(stringResource(id = R.string.activity_msg_reload))
        }
    }
}

@Composable
fun FailedLoadUsersWidget(viewModel: UserListViewModel) {
    FailedToLoadWidget(
        errorTxt = stringResource(R.string.activity_list_user_msg_failed_to_load_users),
        onRefreshBttnClicked = { viewModel.loadUsers() })
}

@Composable
fun FailedToLoadWidget(errorTxt: String, onRefreshBttnClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(R.drawable.ic_embarrassed),
            contentDescription = errorTxt,
            modifier = Modifier.fillMaxSize(.4f)
        )
        Text(text = errorTxt)
        TextButton(onClick = onRefreshBttnClicked) {
            Text(stringResource(id = R.string.activity_msg_reload))
        }
    }
}


@Composable
fun LoadingUsersWidget() {
    LoadingWidget()
}

@Composable
fun LoadingWidget() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun SuccessGetUsers(navController: NavController, originalItems: List<GetUsersResponse>) {

    var filterTxt by rememberSaveable {
        mutableStateOf("")
    }

    val filtering = filterTxt.isNotBlank()

    var filteredResults = listOf<GetUsersResponse>()
    if (filtering) {
        filteredResults = originalItems.filter {
            it.name.toLowerCase(Locale.current).contains(filterTxt.toLowerCase(Locale.current))
        }
    }

    val filteredResultsIsEmpty = filteredResults.isEmpty()

    Column {
        TextField(
            value = filterTxt,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {
                filterTxt = it
            },
            label = { Text(stringResource(R.string.activity_list_user_hint_search_user)) }
        )
        if (!filtering)
            UserList(navController, originalItems)
        else
            if (filteredResultsIsEmpty) EmptyResultsWidget() else UserList(
                navController,
                filteredResults
            )
    }
}

@Composable
fun UserList(
    navController: NavController,
    items: List<GetUsersResponse>,
    //onUserItemClicked: (GetUsersResponse) -> Unit = {}
) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(items) { user ->
            UserItem(
                user = user,
                onClick = {
                    val userJson = Uri.encode(Gson().toJson(user))
                    navController.navigate("${ComposeAppScreens.GetPostsScreen.route}$userJson")
                },
            )
        }
    }
}

@Composable
fun UserItem(user: GetUsersResponse, showBttn: Boolean = true, onClick: () -> Unit = {}) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth(),
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
            if (showBttn) TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick
            ) {
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
        MyComposableApp()
    }
}