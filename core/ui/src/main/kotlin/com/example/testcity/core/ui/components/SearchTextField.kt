package com.example.testcity.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.testcity.core.ui.R
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = stringResource(R.string.search_hint),
                style = MaterialTheme.typography.bodyLarge,
                color = TestCityColors.TextSecondary,
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        shape = RoundedCornerShape(TestCityDimens.searchFieldRadius),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = TestCityColors.TextSecondary,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = TestCityColors.SearchFieldBackground,
            unfocusedContainerColor = TestCityColors.SearchFieldBackground,
            focusedBorderColor = TestCityColors.Primary,
            unfocusedBorderColor = TestCityColors.SearchFieldBackground,
            cursorColor = TestCityColors.Primary,
        ),
    )
}
