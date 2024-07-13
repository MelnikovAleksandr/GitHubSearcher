package ru.melnikov.githubsearcher.presentation.viewmodel

import android.util.Log
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
import ru.melnikov.githubsearcher.domain.model.User
import ru.melnikov.githubsearcher.domain.repository.SearchRepository

class SearchViewModel(
    private val searchRepository: SearchRepository
): ViewModel() {

    var searchName by mutableStateOf("")
        private set

    fun updateName(name: String) {
        searchName = name
    }

    var githubUsers: MutableStateFlow<PagingData<User>> =
        MutableStateFlow(value = PagingData.empty())
        private set

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
}