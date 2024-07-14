package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoadingUserItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ComponentCircleShimmer(size = 100.dp)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(15.dp)
                        .shimmerLoadingAnimation()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(80.dp)
                        .height(15.dp)
                        .shimmerLoadingAnimation()
                )
            }
        }
    }
}