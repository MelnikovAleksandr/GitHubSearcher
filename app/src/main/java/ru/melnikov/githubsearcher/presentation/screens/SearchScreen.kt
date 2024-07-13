package ru.melnikov.githubsearcher.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import ru.melnikov.githubsearcher.R
import ru.melnikov.githubsearcher.domain.model.User
import ru.melnikov.githubsearcher.presentation.components.SearchField
import ru.melnikov.githubsearcher.presentation.components.SearchTopBar
import ru.melnikov.githubsearcher.presentation.components.ShimmerLoadingItem
import ru.melnikov.githubsearcher.presentation.components.UserListItem
import ru.melnikov.githubsearcher.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
) {

    val users = viewModel.githubUsers.collectAsLazyPagingItems()

    SearchScreenContent(
        users = users,
        searchName = viewModel.searchName,
        searchNameUpdate = viewModel::updateName,
        onSearchClick = viewModel::searchUser
    )
}

@Composable
fun SearchScreenContent(
    users: LazyPagingItems<User>,
    searchName: String,
    searchNameUpdate: (String) -> Unit,
    onSearchClick: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SearchTopBar(
                photoUri = "",
                onProfileClick = {}
            )
        }
    ) { paddingValues ->

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
                        onNavigateToRepositoryScreen = {}
                    )
                }
                users.apply {
                    when (loadState.refresh) {
                        is LoadState.Loading -> {
                            item {
                                repeat(10) {
                                    ShimmerLoadingItem()
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

