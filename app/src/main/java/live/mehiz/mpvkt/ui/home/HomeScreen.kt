/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Home Screen
 */

package live.mehiz.mpvkt.ui.home

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import kotlinx.serialization.Serializable
import live.mehiz.mpvkt.presentation.Screen
import live.mehiz.mpvkt.ui.components.CyberHomeScreen
import live.mehiz.mpvkt.ui.player.PlayerActivity
import live.mehiz.mpvkt.ui.preferences.PreferencesScreen
import live.mehiz.mpvkt.ui.utils.LocalBackStack

@Serializable
object HomeScreen : Screen {
    @Composable
    override fun Content() {
        val backstack = LocalBackStack.current
        CyberHomeScreen(
            onSettingsClick = {
                backstack.add(PreferencesScreen)
            },
        )
    }

    fun playFile(filepath: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, filepath.toUri())
        intent.setClass(context, PlayerActivity::class.java)
        context.startActivity(intent)
    }
}
