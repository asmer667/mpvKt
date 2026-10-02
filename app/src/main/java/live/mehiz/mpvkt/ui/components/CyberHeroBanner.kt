/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Hero Banner (with Bloom)
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.mehiz.mpvkt.ui.theme.CyberColors

@Composable
fun CyberHeroBanner(
    title: String,
    subtitle: String? = null,
    duration: String? = null,
    badge: String? = null,
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CyberBloomCard(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
        borderColor = CyberColors.CyanNeon,
        glowColor = CyberColors.PurpleNeon,
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 1.5.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            CyberColors.CyanNeon,
                            CyberColors.MagentaNeon,
                            CyberColors.PurpleNeon,
                        ),
                    ),
                    shape = RoundedCornerShape(24.dp),
                ),
        ) {
            // خلفية متدرجة
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                CyberColors.PurpleNeon.copy(alpha = 0.4f),
                                CyberColors.BackgroundDark,
                            ),
                        ),
                    ),
            )

            // Overlay داكن
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.88f),
                                Color.Black.copy(alpha = 0.3f),
                                Color.Transparent,
                            ),
                        ),
                    ),
            )

            // المحتوى
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp),
            ) {
                badge?.let {
                    CyberHudBadge(text = it, accentColor = CyberColors.MagentaNeon)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                subtitle?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it,
                        color = CyberColors.TextSecondary,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = onPlayClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.background(
                            brush = Brush.horizontalGradient(
                                listOf(CyberColors.CyanNeon, CyberColors.PurpleNeon),
                            ),
                            shape = RoundedCornerShape(12.dp),
                        ),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                text = "تشغيل الآن",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            )
                        }
                    }

                    duration?.let {
                        CyberHudBadge(text = it, accentColor = CyberColors.CyanNeon)
                    }
                }
            }
        }
    }
}
