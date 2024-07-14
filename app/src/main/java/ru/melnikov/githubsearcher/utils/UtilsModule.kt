package ru.melnikov.githubsearcher.utils

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.melnikov.githubsearcher.utils.Constants.ENCRYPTED_PREFS_FILE

val utilsModule = module {

    single<SharedPreferences> {
        EncryptedSharedPreferences.create(
            androidApplication(),
            ENCRYPTED_PREFS_FILE,
            MasterKey.Builder(get()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<PreferencesHelper> { PreferencesHelper(sharedPreferences = get()) }

    single { StringResourceProvider(context = get()) }

}