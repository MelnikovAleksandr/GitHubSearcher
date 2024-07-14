package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoadingRepoItem(
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(20.dp)
                    .shimmerLoadingAnimation()
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .shimmerLoadingAnimation()
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(15.dp)
                    .shimmerLoadingAnimation()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .shimmerLoadingAnimation()
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(15.dp)
                    .shimmerLoadingAnimation()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .shimmerLoadingAnimation()
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(15.dp)
                    .shimmerLoadingAnimation()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .shimmerLoadingAnimation()
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(15.dp)
                    .shimmerLoadingAnimation()
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .shimmerLoadingAnimation()
            )
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(15.dp)
                    .shimmerLoadingAnimation()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .height(20.dp)
                    .shimmerLoadingAnimation()
            )
        }
    }

}