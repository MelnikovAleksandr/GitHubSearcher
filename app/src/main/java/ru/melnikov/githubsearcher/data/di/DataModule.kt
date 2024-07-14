package ru.melnikov.githubsearcher.data.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.melnikov.githubsearcher.data.remote.GitHubApi
import ru.melnikov.githubsearcher.data.remote.RetrofitErrorsHandler
import ru.melnikov.githubsearcher.data.repository.SearchRepositoryImpl
import ru.melnikov.githubsearcher.data.repository.UserRepoRepositoryImpl
import ru.melnikov.githubsearcher.domain.repository.SearchRepository
import ru.melnikov.githubsearcher.domain.repository.UserRepoRepository
import ru.melnikov.githubsearcher.utils.Constants.GITHUB_URL

val dataModule = module {

    single { okHttp() }

    single { gsonConverterFactory() }

    single { retrofit(get(), get()) }

    single { get<Retrofit>().create(GitHubApi::class.java) }

    single<SearchRepository> { SearchRepositoryImpl(api = get()) }

    single<UserRepoRepository> {
        UserRepoRepositoryImpl(
            api = get(),
            retrofitErrorsHandler = get()
        )
    }

    single<RetrofitErrorsHandler> { RetrofitErrorsHandler.RetrofitErrorsHandlerImpl() }

}

private fun gsonConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun okHttp(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .build()

private fun retrofit(
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient,
) = Retrofit.Builder()
    .baseUrl(GITHUB_URL)
    .addConverterFactory(gsonConverterFactory)
    .client(okHttpClient)
    .build()

private fun loggingInterceptor() =
    HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)