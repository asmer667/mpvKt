/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Home Screen
 * الشاشة الرئيسية مع البيانات الحقيقية
 */

package live.mehiz.mpvkt.ui.components

import android.content.Intent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import live.mehiz.mpvkt.domain.media.MediaFolder
import live.mehiz.mpvkt.domain.media.MediaLibrary
import live.mehiz.mpvkt.domain.media.MediaVideo
import live.mehiz.mpvkt.ui.cyber.CyberFavoritesScreen
import live.mehiz.mpvkt.ui.cyber.CyberLibraryScreen
import live.mehiz.mpvkt.ui.cyber.CyberPlaylistsScreen
import live.mehiz.mpvkt.ui.player.PlayerActivity
import live.mehiz.mpvkt.ui.theme.CyberColors
import live.mehiz.mpvkt.ui.utils.LocalBackStack

@Composable
fun CyberHomeScreen(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val backstack = LocalBackStack.current

    // حالة البيانات
    var videos by remember { mutableStateOf<List<MediaVideo>>(emptyList()) }
    var folders by remember { mutableStateOf<List<MediaFolder>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // جلب البيانات
    LaunchedEffect(Unit) {
        isLoading = true
        videos = MediaLibrary.getAllVideos(context)
        folders = MediaLibrary.getFolders(context)
        isLoading = false
    }

    val dockItems = remember {
        listOf(
            CyberDockItem("playlist", "قائمة", Icons.Default.PlayArrow),
            CyberDockItem("favorites", "المفضلة", Icons.Default.Favorite),
            CyberDockItem("home", "الرئيسية", Icons.Default.Home),
            CyberDockItem("library", "المكتبة", Icons.Default.VideoLibrary),
            CyberDockItem("settings", "الإعدادات", Icons.Default.Settings),
        )
    }

    var activeDockId by remember { mutableStateOf("home") }

    // الفيديو المميز (الأحدث)
    val featuredVideo = videos.firstOrNull()

    CyberGlowBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            CyberTopBar(
                title = "مشغل الوسائط",
                onSettingsClick = onSettingsClick,
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Hero Banner - الفيديو المميز
                if (featuredVideo != null) {
                    item {
                        CyberHeroBanner(
                            title = featuredVideo.title,
                            subtitle = featuredVideo.sizeFormatted,
                            duration = featuredVideo.durationFormatted,
                            badge = featuredVideo.resolution.ifBlank { "VIDEO" },
                            onPlayClick = { playVideo(context, featuredVideo.path) },
                        )
                    }
                }

                // عنوان المجلدات
                if (folders.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "المجلدات",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = "${folders.size} مجلد",
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
                            items(folders.take(10)) { folder ->
                                CyberFolderCard(
                                    folderName = folder.name,
                                    itemCount = folder.videoCount,
                                    onClick = { },
                                )
                            }
                        }
                    }
                }

                // عنوان الأحدث
                if (videos.isNotEmpty()) {
                    item {
                        Text(
                            text = "🔥 الأحدث في المكتبة",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    // الفيديوهات
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                        ) {
                            items(videos.take(20)) { video ->
                                CyberVideoCard(
                                    title = video.title,
                                    duration = video.durationFormatted,
                                    resolution = video.resolution.ifBlank { "HD" },
                                    onClick = { playVideo(context, video.path) },
                                )
                            }
                        }
                    }
                }

                // حالة فارغة
                if (videos.isEmpty() && !isLoading) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "لا توجد فيديوهات",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "أضف ملفات فيديو للمكتبة",
                                color = CyberColors.TextTertiary,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }

                // حالة تحميل
                if (isLoading) {
                    item {
                        Text(
                            text = "جاري تحميل المكتبة...",
                            color = CyberColors.CyanNeon,
                            fontSize = 12.sp,
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }

            CyberDockBar(
                items = dockItems,
                activeId = activeDockId,
                onItemClick = { id ->
                    activeDockId = id
                    when (id) {
                        "home" -> { }
                        "library" -> backstack.add(CyberLibraryScreen)
                        "favorites" -> backstack.add(CyberFavoritesScreen)
                        "playlist" -> backstack.add(CyberPlaylistsScreen)
                        "settings" -> onSettingsClick()
                    }
                },
            )
        }
    }
}

/**
 * تشغيل فيديو
 */
private fun playVideo(context: android.content.Context, path: String) {
    val intent = Intent(Intent.ACTION_VIEW, path.toUri())
    intent.setClass(context, PlayerActivity::class.java)
    context.startActivity(intent)
}

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
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Icon(
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
                maxLines = 1,
            )
            Text(
                text = "$itemCount عنصر",
                color = CyberColors.TextTertiary,
                fontSize = 10.sp,
            )
        }
    }
}
