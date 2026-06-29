package com.example.testcity.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens

@Composable
fun CityListItem(
    name: String,
    country: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(TestCityColors.Surface)
            .clickable(onClick = onClick)
            .padding(
                horizontal = TestCityDimens.spacing16,
                vertical = TestCityDimens.spacing16,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = null,
            tint = TestCityColors.TextSecondary,
            modifier = Modifier.size(TestCityDimens.spacing24),
        )
        Text(
            text = stringResource(R.string.city_list_item_format, name, country),
            color = TestCityColors.TextPrimary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = TestCityDimens.spacing12),
        )
    }
    HorizontalDivider(color = TestCityColors.Divider)
}
