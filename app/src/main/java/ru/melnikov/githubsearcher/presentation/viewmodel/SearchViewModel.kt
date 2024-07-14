package ru.melnikov.githubsearcher.presentation.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.melnikov.githubsearcher.domain.model.User
import ru.melnikov.githubsearcher.domain.repository.AuthRepository
import ru.melnikov.githubsearcher.domain.repository.SearchRepository
import ru.melnikov.githubsearcher.utils.PreferencesHelper
import ru.melnikov.githubsearcher.utils.PrefsKeys
import ru.melnikov.githubsearcher.utils.Resource
import ru.melnikov.githubsearcher.utils.StringResourceProvider

class SearchViewModel(
    private val searchRepository: SearchRepository,
    private val authRepository: AuthRepository,
    private val stringResourceProvider: StringResourceProvider,
    private val preferencesHelper: PreferencesHelper
) : ViewModel(), ContainerHost<SearchUIState, UiEventsSearch> {

    override val container = container<SearchUIState, UiEventsSearch>(SearchUIState())

    var searchName by mutableStateOf("")
        private set

    fun updateName(name: String) {
        searchName = name
    }

    var githubUsers: MutableStateFlow<PagingData<User>> =
        MutableStateFlow(value = PagingData.empty())
        private set

    init {
        intent {
            preferencesHelper.observeKey<String>(PrefsKeys.PHOTO_URI).collect { photoUri ->
                reduce { state.copy(photoUri = photoUri) }
            }
        }
        intent {
            val token = preferencesHelper.getValue<String>(PrefsKeys.TOKEN)
            reduce { state.copy(token = token) }
        }
    }

    fun searchUser() {
        viewModelScope.launch {
            searchRepository
                .searchGithubUsers(searchName)
                .cachedIn(this)
                .collectLatest {
                    githubUsers.value = it
                }
        }
    }

    fun getAuthToken(code: String) {
        intent {
            reduce { state.copy(isAuthLoading = true) }
            when (val result = authRepository.getAuthToken(code)) {
                is Resource.Success -> {
                    reduce { state.copy(isAuthLoading = false) }
                    preferencesHelper.setValue(PrefsKeys.TOKEN, result.data?.accessToken ?: "")
                    postSideEffect(UiEventsSearch.NavigateToProfile)
                }

                is Resource.Error -> {
                    reduce { state.copy(isAuthLoading = false) }
                    postSideEffect(
                        UiEventsSearch.SnackBarEvent(
                            result.httpErrors?.getErrorMessage(
                                stringResourceProvider
                            ) ?: ""
                        )
                    )
                }
            }
        }
    }

    fun navigateToProfile() = intent {
        postSideEffect(UiEventsSearch.NavigateToProfile)

    }
}

@Immutable
data class SearchUIState(
    var isAuthLoading: Boolean = false,
    var photoUri: String = "",
    var token: String = ""
)

sealed class UiEventsSearch {
    data class SnackBarEvent(val message: String) : UiEventsSearch()
    data object NavigateToProfile : UiEventsSearch()
}