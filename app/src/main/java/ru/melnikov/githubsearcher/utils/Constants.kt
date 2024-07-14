package ru.melnikov.githubsearcher.utils

object Constants {
    const val PAGING_FIRST_PAGE = 1
    const val PAGING_SIZE = 10
    const val GITHUB_URL = "https://api.github.com/"
    const val GITHUB_AUTH_URL = "https://github.com/"
    const val REDIRECT_URI = "searcher://callback"
    const val ENCRYPTED_PREFS_FILE = "preferences_file"
}

object PrefsKeys {
    const val TOKEN = "token"
    const val PHOTO_URI = "photo"
}