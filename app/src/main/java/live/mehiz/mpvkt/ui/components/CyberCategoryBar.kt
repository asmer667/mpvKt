/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Category Bar
 * شريط التصنيفات الأفقي
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.mehiz.mpvkt.ui.theme.CyberColors

data class CyberCategory(
    val id: String,
    val title: String,
)

@Composable
fun CyberCategoryBar(
    categories: List<CyberCategory>,
    selectedId: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
    ) {
        items(categories) { category ->
            CyberCategoryChip(
                category = category,
                isSelected = category.id == selectedId,
                onClick = { onSelect(category.id) },
            )
        }
    }
}

@Composable
private fun CyberCategoryChip(
    category: CyberCategory,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) CyberColors.CyanNeon.copy(alpha = 0.2f) else Color.Transparent,
        label = "categoryBg",
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) CyberColors.CyanNeon else CyberColors.TextSecondary,
        label = "categoryText",
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(
                width = 1.dp,
                brush = if (isSelected) {
                    Brush.horizontalGradient(
                        listOf(CyberColors.CyanNeon, CyberColors.MagentaNeon),
                    )
                } else {
                    Brush.linearGradient(listOf(Color.White.copy(alpha = 0.15f), Color.Transparent))
                },
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = category.title,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )
    }
}
