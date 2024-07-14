package ru.melnikov.githubsearcher.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.melnikov.githubsearcher.R
import ru.melnikov.githubsearcher.domain.model.Repo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RepoItem(
    modifier: Modifier = Modifier,
    repo: Repo
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

            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = formatLocalDateTime(repo.updatedAt),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.branch_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = repo.defaultBranch,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.code_fork_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = repo.forksCount.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.star_icon),
                contentDescription = null
            )
            Text(
                text = repo.stargazersCount.toString(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.programing_language),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = repo.language,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.Start
        ) {

            Text(
                text = repo.description,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Justify
            )

        }

    }

}

fun formatLocalDateTime(dateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return dateTime.format(formatter)
}
