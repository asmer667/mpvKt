/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Home Screen
 * الشاشة الرئيسية الكاملة
 */

package live.mehiz.mpvkt.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import live.mehiz.mpvkt.ui.theme.CyberColors

/**
 * بيانات الفيديو
 */
data class CyberVideoItem(
    val id: String,
    val title: String,
    val duration: String? = null,
    val resolution: String? = null,
)

/**
 * الشاشة الرئيسية بأسلوب Cyber
 */
@Composable
fun CyberHomeScreen(
    modifier: Modifier = Modifier,
    onVideoClick: (String) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onPlayClick: (String) -> Unit = {},
) {
    // Dock items
    val dockItems = remember {
        listOf(
            CyberDockItem("playlist", "قائمة", Icons.Default.PlayArrow),
            CyberDockItem("favorites", "المفضلة", Icons.Default.Favorite),
            CyberDockItem("home", "الرئيسية", Icons.Default.Home),
            CyberDockItem("library", "المكتبة", Icons.Default.VideoLibrary),
            CyberDockItem("settings", "الإعدادات", Icons.Default.Settings),
        )
    }

    // فيديوهات تجريبية
    val displayVideos = remember {
        listOf(
            CyberVideoItem("1", "Interstellar", "2:49:00", "4K"),
            CyberVideoItem("2", "The Batman", "2:57:00", "4K"),
            CyberVideoItem("3", "Dune", "2:35:12", "4K"),
            CyberVideoItem("4", "Inception", "2:28:16", "4K"),
            CyberVideoItem("5", "The Witcher", "1:22:00", "4K"),
        )
    }

    var activeDockId by remember { mutableStateOf("home") }

    CyberGlowBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            // الشريط العلوي
            CyberTopBar(
                title = "مشغل الوسائط",
                onSettingsClick = onSettingsClick,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // المحتوى - قابل للتمرير
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Hero Banner
                item {
                    CyberHeroBanner(
                        title = "The Last of Us",
                        subtitle = "مغامرة - دراما - إثارة",
                        duration = "1:52:03",
                        badge = "4K HDR",
                        onPlayClick = { onPlayClick("hero") },
                    )
                }

                // عنوان المجلدات
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "المكتبة",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "عرض الكل",
                            color = CyberColors.CyanNeon,
                            fontSize = 11.sp,
                        )
                    }
                }

                // المجلدات
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(4) { index ->
                            CyberFolderCard(
                                folderName = listOf("أفلام", "مسلسلات", "أنمي", "وثائقيات")[index],
                                itemCount = listOf(47, 32, 16, 12)[index],
                                onClick = { },
                            )
                        }
                    }
                }

                // عنوان الأحدث
                item {
                    Text(
                        text = "🔥 الأحدث في المكتبة",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // قائمة الفيديوهات
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                    ) {
                        items(displayVideos) { video ->
                            CyberVideoCard(
                                title = video.title,
                                duration = video.duration,
                                resolution = video.resolution,
                                onClick = { onVideoClick(video.id) },
                            )
                        }
                    }
                }

                // مساحة إضافية أسفل
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            // الشريط السفلي
            CyberDockBar(
                items = dockItems,
                activeId = activeDockId,
                onItemClick = { activeDockId = it },
            )
        }
    }
}

/**
 * بطاقة مجلد بسيطة
 */
@Composable
fun CyberFolderCard(
    folderName: String,
    itemCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CyberCard(
        onClick = onClick,
        modifier = modifier,
        cornerRadius = 16.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
                tint = CyberColors.CyanNeon,
                modifier = Modifier.height(32.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = folderName,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "$itemCount عنصر",
                color = CyberColors.TextTertiary,
                fontSize = 10.sp,
            )
        }
    }
}
