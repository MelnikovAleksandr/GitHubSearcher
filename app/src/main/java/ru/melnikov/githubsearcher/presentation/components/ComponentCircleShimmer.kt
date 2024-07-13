package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun ComponentCircleShimmer(
    modifier: Modifier = Modifier,
    size: Dp
) {
    Box(
        modifier = modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .size(size)
            .shimmerLoadingAnimation()
    )
}