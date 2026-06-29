package com.example.testcity.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens

@Composable
fun NoNetworkContent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TestCityColors.Background,
        bottomBar = {
            PrimaryButton(
                text = stringResource(R.string.try_again),
                onClick = onRetry,
                modifier = Modifier.padding(
                    horizontal = TestCityDimens.spacing16,
                    vertical = TestCityDimens.spacing16,
                ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = TestCityDimens.spacing24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(TestCityColors.Error, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = TestCityColors.Surface,
                    modifier = Modifier.size(40.dp),
                )
            }

            Text(
                text = stringResource(R.string.error_no_network),
                color = TestCityColors.TextPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = TestCityDimens.spacing24),
            )

            Text(
                text = stringResource(R.string.error_no_network_subtitle),
                color = TestCityColors.TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = TestCityDimens.spacing12),
            )
        }
    }
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TestCityColors.Background,
        bottomBar = {
            if (onRetry != null) {
                PrimaryButton(
                    text = stringResource(R.string.try_again),
                    onClick = onRetry,
                    modifier = Modifier.padding(
                        horizontal = TestCityDimens.spacing16,
                        vertical = TestCityDimens.spacing16,
                    ),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = TestCityDimens.spacing24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            Text(
                text = message,
                color = TestCityColors.Error,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
