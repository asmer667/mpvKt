/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Glow Background
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import live.mehiz.mpvkt.ui.theme.CyberColors

@Composable
fun CyberGlowBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CyberColors.BackgroundDark),
    ) {
        Box(
            modifier = Modifier
                .size(500.dp)
                .align(Alignment.TopStart)
                .blur(160.dp)
                .background(CyberColors.PurpleNeon.copy(alpha = 0.35f), CircleShape),
        )
        Box(
            modifier = Modifier
                .size(450.dp)
                .align(Alignment.BottomEnd)
                .blur(150.dp)
                .background(CyberColors.CyanNeon.copy(alpha = 0.22f), CircleShape),
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
                .blur(180.dp)
                .background(CyberColors.MagentaNeon.copy(alpha = 0.12f), CircleShape),
        )
        content()
    }
}
