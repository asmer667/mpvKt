/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Additional Screens
 * شاشات إضافية: المكتبة، المفضلة، السجل، قوائم التشغيل
 */

package live.mehiz.mpvkt.ui.cyber

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import live.mehiz.mpvkt.presentation.Screen
import live.mehiz.mpvkt.ui.components.CyberGlowBackground
import live.mehiz.mpvkt.ui.components.CyberTopBar
import live.mehiz.mpvkt.ui.theme.CyberColors
import live.mehiz.mpvkt.ui.utils.LocalBackStack

/**
 * شاشة المكتبة - تعرض جميع الفيديوهات
 */
@Serializable
object CyberLibraryScreen : Screen {
    @Composable
    override fun Content() {
        val backstack = LocalBackStack.current
        CyberGlowBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                CyberTopBar(
                    title = "مكتبة الفيديو",
                    onBackClick = { backstack.removeLastOrNull() },
                )
                EmptyPlaceholder(
                    icon = Icons.Default.VideoLibrary,
                    title = "مكتبة الفيديو",
                    subtitle = "لم يتم العثور على ملفات فيديو",
                )
            }
        }
    }
}

/**
 * شاشة المفضلة
 */
@Serializable
object CyberFavoritesScreen : Screen {
    @Composable
    override fun Content() {
        val backstack = LocalBackStack.current
        CyberGlowBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                CyberTopBar(
                    title = "المفضلة",
                    onBackClick = { backstack.removeLastOrNull() },
                )
                EmptyPlaceholder(
                    icon = Icons.Default.Favorite,
                    title = "المفضلة",
                    subtitle = "لم تتم إضافة أي فيديو للمفضلة بعد",
                )
            }
        }
    }
}

/**
 * شاشة السجل
 */
@Serializable
object CyberHistoryScreen : Screen {
    @Composable
    override fun Content() {
        val backstack = LocalBackStack.current
        CyberGlowBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                CyberTopBar(
                    title = "سجل المشاهدة",
                    onBackClick = { backstack.removeLastOrNull() },
                )
                EmptyPlaceholder(
                    icon = Icons.Default.History,
                    title = "سجل المشاهدة",
                    subtitle = "لم تشاهد أي فيديو بعد",
                )
            }
        }
    }
}

/**
 * شاشة قوائم التشغيل
 */
@Serializable
object CyberPlaylistsScreen : Screen {
    @Composable
    override fun Content() {
        val backstack = LocalBackStack.current
        CyberGlowBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                CyberTopBar(
                    title = "قوائم التشغيل",
                    onBackClick = { backstack.removeLastOrNull() },
                )
                EmptyPlaceholder(
                    icon = Icons.Default.PlayArrow,
                    title = "قوائم التشغيل",
                    subtitle = "لا توجد قوائم تشغيل",
                )
            }
        }
    }
}

/**
 * عنصر placeholder للشاشات الفارغة
 */
@Composable
private fun EmptyPlaceholder(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CyberColors.CyanNeon.copy(alpha = 0.5f),
                modifier = Modifier.padding(20.dp),
            )
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = subtitle,
                color = CyberColors.TextTertiary,
                fontSize = 13.sp,
            )
        }
    }
}
