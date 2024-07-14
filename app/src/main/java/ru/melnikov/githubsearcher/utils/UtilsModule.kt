package ru.melnikov.githubsearcher.utils

import org.koin.dsl.module

val utilsModule = module {

    single { StringResourceProvider(context = get()) }

}