package ru.melnikov.githubsearcher.presentation.screens

import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.melnikov.githubsearcher.BuildConfig
import ru.melnikov.githubsearcher.R
import ru.melnikov.githubsearcher.domain.model.User
import ru.melnikov.githubsearcher.presentation.components.CircularProgressIndicatorBox
import ru.melnikov.githubsearcher.presentation.components.SearchField
import ru.melnikov.githubsearcher.presentation.components.SearchTopBar
import ru.melnikov.githubsearcher.presentation.components.ShimmerLoadingUserItem
import ru.melnikov.githubsearcher.presentation.components.UserListItem
import ru.melnikov.githubsearcher.presentation.viewmodel.SearchUIState
import ru.melnikov.githubsearcher.presentation.viewmodel.SearchViewModel
import ru.melnikov.githubsearcher.presentation.viewmodel.UiEventsSearch
import ru.melnikov.githubsearcher.utils.Constants
import ru.melnikov.githubsearcher.utils.Constants.REDIRECT_URI

@Composable
fun SearchScreen(
    code: String?,
    viewModel: SearchViewModel = koinViewModel(),
    onNavigateToRepositoryScreen: (String) -> Unit,
    navigateToProfile: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var previousCode by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = code) {
        if (code != null && code != previousCode) {
            viewModel.getAuthToken(code)
            previousCode = code
        }
    }

    viewModel.collectSideEffect {
        when (it) {
            is UiEventsSearch.SnackBarEvent -> {
                scope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }

            is UiEventsSearch.NavigateToProfile -> {
                navigateToProfile()
            }
        }
    }

    val users = viewModel.githubUsers.collectAsLazyPagingItems()

    val state by viewModel.container.stateFlow.collectAsState()

    SearchScreenContent(
        state = state,
        users = users,
        searchName = viewModel.searchName,
        searchNameUpdate = viewModel::updateName,
        onSearchClick = viewModel::searchUser,
        onNavigateToRepositoryScreen = onNavigateToRepositoryScreen,
        snackbarHostState = snackbarHostState,
        navigateToProfile = viewModel::navigateToProfile
    )
}

@Composable
fun SearchScreenContent(
    state: SearchUIState,
    users: LazyPagingItems<User>,
    searchName: String,
    searchNameUpdate: (String) -> Unit,
    onSearchClick: () -> Unit,
    onNavigateToRepositoryScreen: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    navigateToProfile: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {

                RESULT_CANCELED -> {
                    showToast(context, "Auth canceled")
                }

                else -> {
                    showToast(context, "Auth error")
                }
            }
        }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            SearchTopBar(
                photoUri = state.photoUri,
                onProfileClick = {
                    if (state.token.isBlank()) {
                        startIntentActivity(startForResult)
                    } else {
                        navigateToProfile()
                    }
                }
            )
        }
    ) { paddingValues ->

        if (state.isAuthLoading) {
            CircularProgressIndicatorBox()
        }

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                inputText = searchName,
                onUpdateText = searchNameUpdate,
                placeholder = stringResource(id = R.string.search_user),
                onSearchIconCLick = onSearchClick,
                focusManager = focusManager,
                keyboardController = keyboardController
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                items(users.itemCount) { index ->
                    UserListItem(
                        modifier = Modifier.animateItem(),
                        user = users[index],
                        onNavigateToRepositoryScreen = onNavigateToRepositoryScreen
                    )
                }
                users.apply {
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            item {
                                repeat(10) {
                                    ShimmerLoadingUserItem()
                                }
                            }
                        }

                        is LoadState.Error -> {
                            val error = users.loadState.refresh as LoadState.Error
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 60.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                    Image(
                                        painter = painterResource(id = R.drawable.error_list),
                                        contentDescription = null
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 12.dp),
                                        text = error.error.localizedMessage
                                            ?: stringResource(id = R.string.unknown_error),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        is LoadState.NotLoading -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 60.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.search_icon),
                                        contentDescription = "search"
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 12.dp),
                                        text = stringResource(id = R.string.search),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun startIntentActivity(startForResult: ManagedActivityResultLauncher<Intent, ActivityResult>) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(
            "https://github.com/login/oauth/authorize" +
                    "?client_id=" + BuildConfig.ID +
                    "&scope=repo" +
                    "&redirect_uri=" + REDIRECT_URI
        )
    }
    try {
        startForResult.launch(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

