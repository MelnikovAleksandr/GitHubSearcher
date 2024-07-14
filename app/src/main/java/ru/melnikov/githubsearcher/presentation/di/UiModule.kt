package ru.melnikov.githubsearcher.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.melnikov.githubsearcher.presentation.viewmodel.SearchViewModel
import ru.melnikov.githubsearcher.presentation.viewmodel.UserRepoViewModel

val uiModule = module {
    viewModel {
        SearchViewModel(
            searchRepository = get()
        )
    }

    viewModel {
        UserRepoViewModel(
            userRepoRepository = get(),
            stringResourceProvider = get(),
            savedStateHandle = get()
        )
    }
}