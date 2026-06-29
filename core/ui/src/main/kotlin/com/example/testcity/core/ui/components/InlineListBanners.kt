package com.example.testcity.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens

@Composable
fun RefreshErrorBanner(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TestCityColors.ErrorContainer)
            .padding(
                horizontal = TestCityDimens.spacing16,
                vertical = TestCityDimens.spacing8,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = message,
            color = TestCityColors.Error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
        )
        TextButton(onClick = onRetry) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun PagingAppendError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onRetry,
        modifier = modifier
            .fillMaxWidth()
            .padding(TestCityDimens.spacing16),
    ) {
        Text(
            text = stringResource(R.string.tap_to_retry),
            color = TestCityColors.Primary,
        )
    }
}
