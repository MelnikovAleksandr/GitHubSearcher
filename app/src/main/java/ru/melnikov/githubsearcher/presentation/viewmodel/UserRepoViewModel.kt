package ru.melnikov.githubsearcher.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.melnikov.githubsearcher.domain.model.Repo
import ru.melnikov.githubsearcher.domain.repository.UserRepoRepository
import ru.melnikov.githubsearcher.presentation.navigation.Routes
import ru.melnikov.githubsearcher.utils.Resource
import ru.melnikov.githubsearcher.utils.StringResourceProvider
import java.time.Duration

class UserRepoViewModel(
    private val userRepoRepository: UserRepoRepository,
    private val stringResourceProvider: StringResourceProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = MutableStateFlow(UserRepoUIState())
    val state: StateFlow<UserRepoUIState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow: SharedFlow<UiEvents> = _eventFlow.asSharedFlow()

    init {
        val userNameArgs = savedStateHandle.toRoute<Routes.UserRepositoriesScreen>().userName
        state.value.userName = userNameArgs
        getUserRepos(userNameArgs)
    }

    private fun getUserRepos(userName: String) {
        viewModelScope.launch {
            state.value.isLoading = true
            when (val result = userRepoRepository.getUserRepo(userName)) {
                is Resource.Success -> {
                    state.value.isLoading = false
                    state.value.userRepos = result.data ?: emptyList()
                }

                is Resource.Error -> {
                    state.value.isLoading = false
                    _eventFlow.emit(
                        UiEvents.SnackBarEvent(
                            result.httpErrors?.getErrorMessage(
                                stringResourceProvider
                            ) ?: ""
                        )
                    )
                }
            }
        }
    }

    fun navigateBack() = viewModelScope.launch {
        _eventFlow.emit(UiEvents.NavigateBack)
    }

}

@Immutable
data class UserRepoUIState(
    var userRepos: List<Repo> = emptyList(),
    var isLoading: Boolean = true,
    var userName: String = ""
)

sealed class UiEvents {
    data class SnackBarEvent(val message: String) : UiEvents()
    data object NavigateBack : UiEvents()
}
