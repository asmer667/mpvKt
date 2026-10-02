/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Animations
 * أنيميشن النبض + التوهج النابض
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativePaint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import live.mehiz.mpvkt.ui.theme.CyberColors

/**
 * نبض التوهج - للأزرار النشطة
 */
@Composable
fun CyberPulse(
    modifier: Modifier = Modifier,
    color: Color = CyberColors.CyanNeon,
    size: Dp = 100.dp,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulseScale",
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulseAlpha",
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        // هالة متوهجة نابضة
        Box(
            modifier = Modifier
                .size(size)
                .scale(scale)
                .alpha(alpha)
                .background(
                    Brush.radialGradient(
                        listOf(
                            color.copy(alpha = 0.5f),
                            Color.Transparent,
                        ),
                    ),
                    CircleShape,
                ),
        )
        content()
    }
}

/**
 * توهج ثابت حول المكونات
 */
@Composable
fun CyberStaticGlow(
    modifier: Modifier = Modifier,
    color: Color = CyberColors.CyanNeon,
    radius: Float = 24f,
    cornerRadius: Dp = 16.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .cyberDrawGlow(color, radius, cornerRadius),
    ) {
        content()
    }
}

private fun Modifier.cyberDrawGlow(
    color: Color,
    radius: Float,
    cornerRadius: Dp,
): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = NativePaint().apply {
            this.color = color.copy(alpha = 0.5f).toArgb()
            isAntiAlias = true
            maskFilter = android.graphics.BlurMaskFilter(
                radius,
                android.graphics.BlurMaskFilter.Blur.NORMAL,
            )
        }
        canvas.nativeCanvas.drawRoundRect(
            0f,
            0f,
            size.width,
            size.height,
            cornerRadius.toPx(),
            cornerRadius.toPx(),
            paint,
        )
    }
}

/**
 * نقطة نيون نابضة (للمؤشرات الحية)
 */
@Composable
fun CyberLiveDot(
    modifier: Modifier = Modifier,
    color: Color = CyberColors.CyanNeon,
    size: Dp = 8.dp,
) {
    CyberPulse(
        modifier = modifier,
        color = color,
        size = size * 3,
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color, CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.6f), CircleShape),
        )
    }
}

/**
 * خط متحرك (Shimmer)
 */
@Composable
fun CyberShimmer(
    modifier: Modifier = Modifier,
    color: Color = CyberColors.CyanNeon,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "shimmerAlpha",
    )

    Box(
        modifier = modifier
            .alpha(alpha)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        color.copy(alpha = 0.6f),
                        Color.Transparent,
                    ),
                ),
            ),
    )
}
