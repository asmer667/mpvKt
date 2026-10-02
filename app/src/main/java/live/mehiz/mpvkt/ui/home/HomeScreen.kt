/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Cyber UI - Home Screen (Replaced)
 * الشاشة الرئيسية الجديدة بأسلوب Cyber
 */

package live.mehiz.mpvkt.ui.home

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import live.mehiz.mpvkt.presentation.Screen
import live.mehiz.mpvkt.ui.components.CyberHomeScreen
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
}
