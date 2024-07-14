package ru.melnikov.githubsearcher.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.melnikov.githubsearcher.domain.model.GithubUser
import ru.melnikov.githubsearcher.domain.repository.GitHubUserRepository
import ru.melnikov.githubsearcher.utils.PreferencesHelper
import ru.melnikov.githubsearcher.utils.PrefsKeys
import ru.melnikov.githubsearcher.utils.Resource
import ru.melnikov.githubsearcher.utils.StringResourceProvider

class ProfileViewModel(
    private val gitHubUserRepository: GitHubUserRepository,
    private val stringResourceProvider: StringResourceProvider,
    private val preferencesHelper: PreferencesHelper
) : ViewModel(), ContainerHost<ProfileUIState, UiEventsProfile> {

    override val container = container<ProfileUIState, UiEventsProfile>(ProfileUIState())

    init {
        getUser()
    }

    fun navigateBack() = intent {
        postSideEffect(UiEventsProfile.NavigateBack)
    }

    private fun getUser() = intent {
        reduce { state.copy(isLoading = true) }
        when (val user = gitHubUserRepository.getUserInfo()) {
            is Resource.Success -> {
                preferencesHelper.setValue(PrefsKeys.PHOTO_URI, user.data?.image ?: "")
                reduce { state.copy(isLoading = false, user = user.data) }
            }

            is Resource.Error -> {
                reduce { state.copy(isLoading = false) }
                postSideEffect(
                    UiEventsProfile.SnackBarEvent(
                        user.httpErrors?.getErrorMessage(
                            stringResourceProvider
                        ) ?: ""
                    )
                )
            }
        }
    }
}

@Immutable
data class ProfileUIState(
    var user: GithubUser? = null,
    var isLoading: Boolean = false
)

sealed class UiEventsProfile {
    data class SnackBarEvent(val message: String) : UiEventsProfile()
    data object NavigateBack : UiEventsProfile()
}