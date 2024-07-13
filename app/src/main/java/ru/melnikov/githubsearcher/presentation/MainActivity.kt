package ru.melnikov.githubsearcher.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import ru.melnikov.githubsearcher.presentation.navigation.NavigationGraph
import ru.melnikov.githubsearcher.presentation.theme.GitHubSearcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitHubSearcherTheme {
                Scaffold { paddingValues ->
                    NavigationGraph(
                        paddingValues = paddingValues
                    )
                }
            }
        }
    }
}