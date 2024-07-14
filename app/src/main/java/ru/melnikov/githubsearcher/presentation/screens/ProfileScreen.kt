package ru.melnikov.githubsearcher.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect
import ru.melnikov.githubsearcher.presentation.components.ComponentCircleShimmer
import ru.melnikov.githubsearcher.presentation.components.RepoTopBar
import ru.melnikov.githubsearcher.presentation.components.shimmerLoadingAnimation
import ru.melnikov.githubsearcher.presentation.viewmodel.ProfileUIState
import ru.melnikov.githubsearcher.presentation.viewmodel.ProfileViewModel
import ru.melnikov.githubsearcher.presentation.viewmodel.UiEventsProfile

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    navigateBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    viewModel.collectSideEffect {
        when (it) {
            is UiEventsProfile.SnackBarEvent -> {
                scope.launch {
                    snackbarHostState.showSnackbar(it.message)
                }
            }

            is UiEventsProfile.NavigateBack -> {
                navigateBack()
            }
        }
    }


    val state by viewModel.container.stateFlow.collectAsState()

    ProfileScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        navigateBack = viewModel::navigateBack
    )

}

@Composable
fun ProfileScreenContent(
    state: ProfileUIState,
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
                name = state.user?.name ?: "",
                title = "",
                navigateBack = navigateBack
            )
        }
    ) { paddingValues ->

        AnimatedVisibility(
            visible = state.isLoading, exit = fadeOut(),
            enter = fadeIn()
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ComponentCircleShimmer(size = 200.dp)
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(15.dp)
                        .shimmerLoadingAnimation()
                )

                Row {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(15.dp)
                            .shimmerLoadingAnimation()
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(15.dp)
                            .shimmerLoadingAnimation()
                    )
                }

                Row {
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(15.dp)
                            .shimmerLoadingAnimation()
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .width(80.dp)
                            .height(15.dp)
                            .shimmerLoadingAnimation()
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = !state.isLoading, exit = fadeOut(),
            enter = fadeIn()
        ) {

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = state.user?.image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Name: ${state.user?.name}")

                Row {
                    Text(text = "Repos: ${state.user?.repos}")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Gists: ${state.user?.gists}")
                }

                Row {
                    Text(text = "Followers: ${state.user?.followers}")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Following: ${state.user?.following}")
                }
            }

        }
    }
}
