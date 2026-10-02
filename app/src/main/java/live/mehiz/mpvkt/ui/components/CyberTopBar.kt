/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Top Bar
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.mehiz.mpvkt.ui.theme.CyberColors

@Composable
fun CyberTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            color = CyberColors.TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CyberIconButton(
                icon = Icons.Default.Search,
                contentDescription = "Search",
                onClick = onSearchClick,
            )
            CyberIconButton(
                icon = Icons.Default.Settings,
                contentDescription = "Settings",
                onClick = onSettingsClick,
            )
            CyberIconButton(
                icon = Icons.Default.MoreVert,
                contentDescription = "More",
                onClick = onMoreClick,
            )
        }
    }
}

@Composable
private fun CyberIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(CyberColors.GlassSurface)
            .border(1.dp, CyberColors.DarkGlassBorder, CircleShape),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(16.dp),
        )
    }
}
