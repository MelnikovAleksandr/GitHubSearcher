package ru.melnikov.githubsearcher.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.melnikov.githubsearcher.presentation.viewmodel.SearchViewModel

val uiModule = module {
    viewModel {
        SearchViewModel(
            searchRepository = get()
        )
    }
}