package ru.melnikov.githubsearcher.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.melnikov.githubsearcher.R
import ru.melnikov.githubsearcher.presentation.components.EmptyContent
import ru.melnikov.githubsearcher.presentation.components.RepoItem
import ru.melnikov.githubsearcher.presentation.components.RepoTopBar
import ru.melnikov.githubsearcher.presentation.components.ShimmerLoadingRepoItem
import ru.melnikov.githubsearcher.presentation.viewmodel.UiEvents
import ru.melnikov.githubsearcher.presentation.viewmodel.UserRepoUIState
import ru.melnikov.githubsearcher.presentation.viewmodel.UserRepoViewModel

@Composable
fun UserRepoScreen(
    viewModel: UserRepoViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackBarEvent -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message)
                    }
                }

                is UiEvents.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    }

    val state = viewModel.state.collectAsState().value

    UserRepoScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        navigateBack = viewModel::navigateBack
    )


}

@Composable
fun UserRepoScreenContent(
    state: UserRepoUIState,
    snackbarHostState: SnackbarHostState,
    navigateBack: () -> Unit
) {


    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            RepoTopBar(
                name = state.userName,
                title = stringResource(id = R.string.repositories),
                navigateBack = navigateBack
            )
        }
    ) { paddingValues ->

        if (state.isLoading) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                repeat(6) {
                    ShimmerLoadingRepoItem()
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                itemsIndexed(items = state.userRepos, key = { _, repo -> repo.id }) { index, repo ->
                    RepoItem(repo = repo)
                }
                item {
                    if (state.userRepos.isEmpty()) {
                        EmptyContent()
                    }
                }
            }
        }
    }
}