package ru.melnikov.githubsearcher.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.melnikov.githubsearcher.domain.model.Repo
import ru.melnikov.githubsearcher.domain.repository.UserRepoRepository
import ru.melnikov.githubsearcher.presentation.navigation.Routes
import ru.melnikov.githubsearcher.utils.Resource
import ru.melnikov.githubsearcher.utils.StringResourceProvider

class UserRepoViewModel(
    private val userRepoRepository: UserRepoRepository,
    private val stringResourceProvider: StringResourceProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ContainerHost<UserRepoUIState, UiEventsRepo> {

    override val container = container<UserRepoUIState, UiEventsRepo>(UserRepoUIState())

    init {
        intent {
            val userNameArgs = savedStateHandle.toRoute<Routes.UserRepositoriesScreen>().userName
            reduce { state.copy(userName = userNameArgs) }
            getUserRepos(userNameArgs)
        }
    }

    private fun getUserRepos(userName: String) = intent {
        reduce { state.copy(isLoading = true) }
        when (val result = userRepoRepository.getUserRepo(userName)) {
            is Resource.Success -> {
                reduce { state.copy(isLoading = false, userRepos = result.data ?: emptyList()) }
            }

            is Resource.Error -> {
                reduce { state.copy(isLoading = false) }
                postSideEffect(
                    UiEventsRepo.SnackBarEvent(
                        result.httpErrors?.getErrorMessage(
                            stringResourceProvider
                        ) ?: ""
                    )
                )
            }
        }
    }

    fun navigateBack() = intent {
        postSideEffect(UiEventsRepo.NavigateBack)
    }
}


@Immutable
data class UserRepoUIState(
    var userRepos: List<Repo> = emptyList(),
    var isLoading: Boolean = false,
    var userName: String = ""
)

sealed class UiEventsRepo {
    data class SnackBarEvent(val message: String) : UiEventsRepo()
    data object NavigateBack : UiEventsRepo()
}
