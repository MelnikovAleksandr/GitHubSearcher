package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun CircularProgressIndicatorBox(
    alpha: Float = 0.5f
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = alpha))
            .wrapContentSize(Alignment.Center)
            .zIndex(1f)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(60.dp),
            strokeWidth = 6.dp
        )
    }
}