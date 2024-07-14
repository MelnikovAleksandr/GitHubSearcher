package ru.melnikov.githubsearcher.data.remote

import retrofit2.Response
import ru.melnikov.githubsearcher.utils.ErrorsTypesHttp
import ru.melnikov.githubsearcher.utils.Resource
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

interface RetrofitErrorsHandler {
    suspend fun <T> executeSafely(
        block: suspend () -> Resource<T>
    ): Resource<T>

    fun <T, R> responseFailureHandler(response: Response<T>): Resource.Error<R>

    class RetrofitErrorsHandlerImpl : RetrofitErrorsHandler {
        override suspend fun <T> executeSafely(block: suspend () -> Resource<T>): Resource<T> {
            return try {
                block()
            } catch (e: Exception) {
                when (e) {
                    is ConnectException, is SocketException -> {
                        Resource.Error(httpErrors = ErrorsTypesHttp.MissingConnection)
                    }

                    is SocketTimeoutException -> {
                        Resource.Error(httpErrors = ErrorsTypesHttp.TimeoutException)
                    }

                    is IOException -> {
                        Resource.Error(httpErrors = ErrorsTypesHttp.NetworkError)
                    }

                    else -> {
                        Resource.Error(httpErrors = ErrorsTypesHttp.UnknownError)
                    }
                }
            }
        }

        override fun <T, R> responseFailureHandler(response: Response<T>): Resource.Error<R> {
            return when (response.code()) {
                in 400..499 -> {
                    Resource.Error(
                        httpErrors = ErrorsTypesHttp.Https400Errors
                    )
                }

                in 500..599 -> {
                    Resource.Error(
                        httpErrors = ErrorsTypesHttp.Https500Errors
                    )
                }

                else -> {
                    Resource.Error(httpErrors = ErrorsTypesHttp.UnknownError)
                }
            }
        }
    }
}