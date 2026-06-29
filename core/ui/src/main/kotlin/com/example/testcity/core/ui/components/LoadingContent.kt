package com.example.testcity.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testcity.core.ui.theme.TestCityColors
import com.example.testcity.core.ui.theme.TestCityDimens

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(TestCityDimens.spacing16),
        verticalArrangement = Arrangement.spacedBy(TestCityDimens.spacing16),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(TestCityColors.Skeleton, RoundedCornerShape(TestCityDimens.searchFieldRadius)),
        )
        repeat(5) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(TestCityDimens.spacing12),
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(TestCityColors.Skeleton, CircleShape),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp)
                        .background(TestCityColors.Skeleton, RoundedCornerShape(4.dp)),
                )
            }
        }
    }
}

@Composable
fun LoadingFooter(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(20.dp)
                .background(TestCityColors.Skeleton, RoundedCornerShape(4.dp)),
        )
    }
}
