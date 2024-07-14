package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.melnikov.githubsearcher.R

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    photoUri: String,
    onProfileClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
            )
            .background(
                MaterialTheme.colorScheme.primary
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = stringResource(id = R.string.search_screen_top_bar),
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            modifier = Modifier.padding(vertical = 4.dp),
            onClick = onProfileClick
        ) {
            if (photoUri.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUri)
                        .crossfade(true)
                        .build(),
                    loading = {
                        ComponentCircleShimmer(size = 32.dp)
                    },
                    error = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.account_circle),
                            contentDescription = null,
                            modifier = Modifier.size(31.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.account_circle),
                    modifier = Modifier.size(32.dp),
                    contentDescription = "to profile",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}