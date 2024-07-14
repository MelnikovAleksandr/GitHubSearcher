package ru.melnikov.githubsearcher.presentation.viewmodel

import ru.melnikov.githubsearcher.R
import ru.melnikov.githubsearcher.utils.ErrorsTypesHttp
import ru.melnikov.githubsearcher.utils.StringResourceProvider

fun ErrorsTypesHttp?.getErrorMessage(stringResourceProvider: StringResourceProvider): String {
    return when (this) {
        is ErrorsTypesHttp.Https400Errors ->
            stringResourceProvider.getString(R.string.client_error)

        is ErrorsTypesHttp.Https500Errors ->
            stringResourceProvider.getString(R.string.server_error)

        is ErrorsTypesHttp.TimeoutException ->
            stringResourceProvider.getString(R.string.error_timeout)

        is ErrorsTypesHttp.MissingConnection ->
            stringResourceProvider.getString(R.string.error_no_connection)

        is ErrorsTypesHttp.UnknownError ->
            stringResourceProvider.getString(R.string.error_else)

        is ErrorsTypesHttp.NetworkError ->
            stringResourceProvider.getString(R.string.network_error)

        else ->
            stringResourceProvider.getString(R.string.error_else)
    }
}