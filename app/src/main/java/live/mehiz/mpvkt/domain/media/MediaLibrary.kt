/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Media Library - مسح الجهاز بحثاً عن ملفات الفيديو
 */

package live.mehiz.mpvkt.domain.media

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * بيانات فيديو واحدة
 */
data class MediaVideo(
    val id: Long,
    val title: String,
    val path: String,
    val uri: Uri,
    val duration: Long,
    val size: Long,
    val resolution: String = "",
    val dateAdded: Long = 0,
) {
    val durationFormatted: String
        get() {
            if (duration <= 0L) return "00:00"
            val totalSeconds = duration / 1000
            val hours = totalSeconds / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60
            return if (hours > 0) {
                String.format(java.util.Locale.US, "%d:%02d:%02d", hours, minutes, seconds)
            } else {
                String.format(java.util.Locale.US, "%02d:%02d", minutes, seconds)
            }
        }

    val sizeFormatted: String
        get() {
            val kb = size / 1024.0
            val mb = kb / 1024.0
            val gb = mb / 1024.0
            return when {
                gb >= 1.0 -> String.format(java.util.Locale.US, "%.2f GB", gb)
                mb >= 1.0 -> String.format(java.util.Locale.US, "%.1f MB", mb)
                kb >= 1.0 -> String.format(java.util.Locale.US, "%.1f KB", kb)
                else -> "$size B"
            }
        }
}

/**
 * بيانات مجلد
 */
data class MediaFolder(
    val path: String,
    val name: String,
    val videoCount: Int,
)

/**
 * مكتبة الوسائط - تبحث في MediaStore عن ملفات الفيديو
 */
object MediaLibrary {

    /**
     * جلب جميع ملفات الفيديو من الجهاز
     */
    suspend fun getAllVideos(context: Context): List<MediaVideo> = withContext(Dispatchers.IO) {
        val videos = mutableListOf<MediaVideo>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.RESOLUTION,
            MediaStore.Video.Media.DATE_ADDED,
        )

        val selection = "${MediaStore.Video.Media.DURATION} > 0"
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            sortOrder,
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val resolutionColumn = cursor.getColumnIndex(MediaStore.Video.Media.RESOLUTION)
            val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn) ?: "Unknown"
                val data = cursor.getString(dataColumn) ?: ""
                val duration = cursor.getLong(durationColumn)
                val size = cursor.getLong(sizeColumn)
                val resolution = if (resolutionColumn >= 0) cursor.getString(resolutionColumn) ?: "" else ""
                val dateAdded = cursor.getLong(dateColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id,
                )

                videos.add(
                    MediaVideo(
                        id = id,
                        title = name,
                        path = data,
                        uri = contentUri,
                        duration = duration,
                        size = size,
                        resolution = resolution,
                        dateAdded = dateAdded,
                    ),
                )
            }
        }

        videos
    }

    /**
     * تجميع الفيديوهات حسب المجلد
     */
    suspend fun getFolders(context: Context): List<MediaFolder> = withContext(Dispatchers.IO) {
        val videos = getAllVideos(context)
        val grouped = videos.groupBy { video ->
            val parent = java.io.File(video.path).parent ?: "/"
            parent
        }
        grouped.map { (path, folderVideos) ->
            MediaFolder(
                path = path,
                name = java.io.File(path).name.ifBlank { "Internal Storage" },
                videoCount = folderVideos.size,
            )
        }.sortedByDescending { it.videoCount }
    }
}
