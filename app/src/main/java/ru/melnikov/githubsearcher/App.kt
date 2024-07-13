package ru.melnikov.githubsearcher

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.melnikov.githubsearcher.data.di.dataModule
import ru.melnikov.githubsearcher.presentation.di.uiModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, uiModule)
        }
    }
}