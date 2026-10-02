/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Bloom Effect
 * تأثير التوهج الحقيقي (Bloom)
 */

package live.mehiz.mpvkt.ui.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import live.mehiz.mpvkt.ui.theme.CyberColors

/**
 * طبقة Bloom - توهج حقيقي متعدد الطبقات
 * يعمل على Android 12+ (API 31+)
 */
@Composable
fun CyberBloomLayer(
    modifier: Modifier = Modifier,
    color: Color = CyberColors.CyanNeon,
    intensity: Float = 0.5f,
    cornerRadius: Float = 24f,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        // طبقة التوهج الحقيقية
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        renderEffect = RenderEffect
                            .createBlurEffect(40f, 40f, Shader.TileMode.CLAMP)
                            .asComposeRenderEffect()
                        alpha = intensity
                    }
                    .background(
                        Brush.radialGradient(
                            listOf(
                                color.copy(alpha = 0.6f),
                                color.copy(alpha = 0.2f),
                                Color.Transparent,
                            ),
                        ),
                    ),
            )
        } else {
            // Fallback للأجهزة القديمة
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            listOf(
                                color.copy(alpha = 0.3f),
                                Color.Transparent,
                            ),
                        ),
                    ),
            )
        }

        // المحتوى الفعلي
        content()
    }
}

/**
 * إطار Bloom للبطاقات
 */
@Composable
fun CyberBloomCard(
    modifier: Modifier = Modifier,
    borderColor: Color = CyberColors.CyanNeon,
    glowColor: Color = CyberColors.PurpleNeon,
    shape: Shape? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        // التوهج خلف البطاقة
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = RenderEffect
                            .createBlurEffect(30f, 30f, Shader.TileMode.CLAMP)
                            .asComposeRenderEffect()
                    }
                    alpha = 0.7f
                }
                .background(
                    Brush.linearGradient(
                        listOf(
                            borderColor.copy(alpha = 0.6f),
                            glowColor.copy(alpha = 0.6f),
                        ),
                    ),
                ),
        )

        // البطاقة الرئيسية
        Box(
            modifier = Modifier
                .clip(shape ?: androidx.compose.foundation.shape.RoundedCornerShape(16))
                .background(CyberColors.BackgroundDark),
        ) {
            content()
        }
    }
}
