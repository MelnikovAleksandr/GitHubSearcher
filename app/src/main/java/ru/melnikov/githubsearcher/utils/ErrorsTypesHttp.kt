package ru.melnikov.githubsearcher.utils

sealed class ErrorsTypesHttp() {
    data object Https400Errors : ErrorsTypesHttp()
    data object Https500Errors : ErrorsTypesHttp()
    data object TimeoutException : ErrorsTypesHttp()
    data object MissingConnection : ErrorsTypesHttp()
    data object NetworkError : ErrorsTypesHttp()
    data object UnknownError : ErrorsTypesHttp()
}