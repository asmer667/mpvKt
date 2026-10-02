/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Hero Stack
 * البطاقة الرئيسية + بطاقات مصغرة مائلة (تدريجية)
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.mehiz.mpvkt.ui.theme.CyberColors

/**
 * عنصر الفيديو في Hero Stack
 */
data class CyberHeroItem(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val duration: String? = null,
    val badge: String? = null,
)

/**
 * Hero Stack - البطاقة الرئيسية + 4 بطاقات مصغرة مائلة
 */
@Composable
fun CyberHeroStack(
    items: List<CyberHeroItem>,
    onPlayClick: (String) -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxStackSize: Int = 4,
) {
    if (items.isEmpty()) return

    val currentItem = items.first()
    val stackItems = items.drop(1).take(maxStackSize)

    var dragOffset by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (kotlin.math.abs(dragOffset) > 100f) {
                            items.getOrNull(1)?.let { onItemClick(it.id) }
                        }
                        dragOffset = 0f
                    },
                    onHorizontalDrag = { _, delta ->
                        dragOffset += delta
                    },
                )
            },
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Hero الرئيسي
            Box(
                modifier = Modifier
                    .weight(0.65f)
                    .fillMaxHeight()
                    .graphicsLayer {
                        translationX = dragOffset * 0.3f
                    },
            ) {
                CyberHeroBanner(
                    title = currentItem.title,
                    subtitle = currentItem.subtitle,
                    duration = currentItem.duration,
                    badge = currentItem.badge,
                    onPlayClick = { onPlayClick(currentItem.id) },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            // البطاقات المائلة
            Box(
                modifier = Modifier
                    .weight(0.35f)
                    .fillMaxHeight()
                    .graphicsLayer {
                        translationX = dragOffset * 0.7f
                    },
            ) {
                stackItems.forEachIndexed { index, item ->
                    val rotationAngle = -(index * 8f)
                    val offsetX = index * 18f
                    val offsetY = index * 4f
                    val scale = 1f - (index * 0.05f)
                    val alpha = 1f - (index * 0.15f)

                    CyberStackCard(
                        item = item,
                        rotationZ = rotationAngle,
                        offsetX = offsetX,
                        offsetY = offsetY,
                        scale = scale,
                        alpha = alpha,
                        zIndex = (stackItems.size - index).toFloat(),
                        onClick = { onItemClick(item.id) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Composable
private fun CyberStackCard(
    item: CyberHeroItem,
    rotationZ: Float,
    offsetX: Float,
    offsetY: Float,
    scale: Float,
    alpha: Float,
    zIndex: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .graphicsLayer {
                this.translationX = offsetX
                this.translationY = offsetY
                this.rotationZ = rotationZ
                this.scaleX = scale
                this.scaleY = scale
                this.alpha = alpha
                this.zIndex = zIndex
                cameraDistance = 12f * density
            }
            .stackCardGlow(rotationZ)
            .clip(RoundedCornerShape(14.dp))
            .background(CyberColors.GlassSurfaceStrong)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        CyberColors.CyanNeon.copy(alpha = 0.6f),
                        CyberColors.MagentaNeon.copy(alpha = 0.4f),
                        Color.Transparent,
                    ),
                ),
                shape = RoundedCornerShape(14.dp),
            )
            .clickable(onClick = onClick),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.75f),
                            ),
                        ),
                    ),
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(6.dp),
        ) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            item.duration?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = it,
                    color = CyberColors.CyanNeon,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(18.dp)
                .clip(CircleShape)
                .background(CyberColors.CyanNeon.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = CyberColors.CyanNeon,
                modifier = Modifier.size(10.dp),
            )
        }
    }
}

private fun Modifier.stackCardGlow(rotation: Float): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val color = if (rotation < -15f) {
            CyberColors.MagentaNeon.copy(alpha = 0.4f)
        } else {
            CyberColors.CyanNeon.copy(alpha = 0.35f)
        }
        val paint = NativePaint().apply {
            this.color = color.toArgb()
            isAntiAlias = true
            maskFilter = android.graphics.BlurMaskFilter(
                14f,
                android.graphics.BlurMaskFilter.Blur.NORMAL,
            )
        }
        canvas.nativeCanvas.drawRoundRect(
            0f,
            0f,
            size.width,
            size.height,
            14.dp.toPx(),
            14.dp.toPx(),
            paint,
        )
    }
}
