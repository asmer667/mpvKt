/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Video Card (with Bloom)
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.mehiz.mpvkt.ui.theme.CyberColors

@Composable
fun CyberVideoCard(
    title: String,
    duration: String? = null,
    resolution: String? = null,
    isFavorite: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 140.dp,
    height: Dp = 200.dp,
) {
    // الحاوية الرئيسية مع Bloom
    CyberBloomCard(
        modifier = modifier
            .width(width)
            .height(height)
            .clickable(onClick = onClick),
        borderColor = CyberColors.CyanNeon,
        glowColor = CyberColors.MagentaNeon,
        shape = RoundedCornerShape(18.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // طبقة خلفية - بنفسجي
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = CyberColors.PurpleNeon.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(18.dp),
                    ),
            )

            // الطبقة الأمامية
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(18.dp))
                    .background(CyberColors.GlassSurfaceStrong)
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                CyberColors.CyanNeon,
                                CyberColors.MagentaNeon,
                                Color.Transparent,
                            ),
                        ),
                        shape = RoundedCornerShape(18.dp),
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    CyberColors.PurpleNeon.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.95f),
                                ),
                            ),
                        ),
                )

                // زر المفضلة
                if (isFavorite) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.6f))
                            .border(1.dp, CyberColors.MagentaNeon.copy(alpha = 0.8f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("❤", color = CyberColors.MagentaNeon, fontSize = 14.sp)
                    }
                }

                // المحتوى السفلي
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        duration?.let {
                            CyberHudBadge(text = it, accentColor = CyberColors.CyanNeon)
                        }
                        resolution?.let {
                            CyberHudBadge(text = it, accentColor = CyberColors.MagentaNeon)
                        }
                    }
                }

                // زر تشغيل نابض
                CyberPulse(
                    modifier = Modifier.align(Alignment.Center),
                    color = CyberColors.CyanNeon,
                    size = 60.dp,
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(CyberColors.CyanNeon.copy(alpha = 0.3f))
                            .border(1.5.dp, CyberColors.CyanNeon, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = CyberColors.CyanNeon,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                }
            }
        }
    }
}
