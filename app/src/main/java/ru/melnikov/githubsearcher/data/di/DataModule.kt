package ru.melnikov.githubsearcher.data.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.melnikov.githubsearcher.data.remote.AuthApi
import ru.melnikov.githubsearcher.data.remote.GitHubApi
import ru.melnikov.githubsearcher.data.remote.RetrofitErrorsHandler
import ru.melnikov.githubsearcher.data.repository.AuthRepositoryImpl
import ru.melnikov.githubsearcher.data.repository.GitHubUserRepositoryImpl
import ru.melnikov.githubsearcher.data.repository.SearchRepositoryImpl
import ru.melnikov.githubsearcher.data.repository.UserRepoRepositoryImpl
import ru.melnikov.githubsearcher.domain.repository.AuthRepository
import ru.melnikov.githubsearcher.domain.repository.GitHubUserRepository
import ru.melnikov.githubsearcher.domain.repository.SearchRepository
import ru.melnikov.githubsearcher.domain.repository.UserRepoRepository
import ru.melnikov.githubsearcher.utils.Constants.GITHUB_AUTH_URL
import ru.melnikov.githubsearcher.utils.Constants.GITHUB_URL
import ru.melnikov.githubsearcher.utils.PreferencesHelper
import ru.melnikov.githubsearcher.utils.PrefsKeys

val dataModule = module {

    single { okHttp(preferencesHelper = get()) }

    single { gsonConverterFactory() }

    single(named("github")) { retrofit(get(), get()) }

    single(named("auth")) { authRetrofit(get(), get()) }

    single { get<Retrofit>(named("github")).create(GitHubApi::class.java) }

    single { get<Retrofit>(named("auth")).create(AuthApi::class.java) }

    single<SearchRepository> { SearchRepositoryImpl(api = get()) }

    single<UserRepoRepository> {
        UserRepoRepositoryImpl(
            api = get(),
            retrofitErrorsHandler = get()
        )
    }

    single<RetrofitErrorsHandler> { RetrofitErrorsHandler.RetrofitErrorsHandlerImpl() }

    single<AuthRepository> { AuthRepositoryImpl(api = get(), retrofitErrorsHandler = get()) }

    single<GitHubUserRepository> {
        GitHubUserRepositoryImpl(
            api = get(),
            retrofitErrorsHandler = get()
        )
    }

}

private fun gsonConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

private fun tokenInterceptor(preferences: PreferencesHelper) = Interceptor { chain ->
    val token = preferences.getValue<String>(PrefsKeys.TOKEN)
    val requestBuilder = chain.request().newBuilder()
    if (token.isNotEmpty()) {
        requestBuilder.addHeader("Authorization", "token $token")
    }
    val newRequest = requestBuilder.build()
    chain.proceed(newRequest)
}

private fun okHttp(preferencesHelper: PreferencesHelper): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .addInterceptor(tokenInterceptor(preferencesHelper))
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
        .setLevel(HttpLoggingInterceptor.Level.BODY)

private fun authRetrofit(
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient,
) = Retrofit.Builder()
    .baseUrl(GITHUB_AUTH_URL)
    .addConverterFactory(gsonConverterFactory)
    .client(okHttpClient)
    .build()