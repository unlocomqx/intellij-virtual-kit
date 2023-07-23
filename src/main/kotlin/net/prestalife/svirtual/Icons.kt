package net.prestalife.svirtual

import com.intellij.ui.IconManager
import javax.swing.Icon

// I couldn't generate this using gradle!

object Icons {
    private fun load(path: String, cacheKey: Int, flags: Int): Icon {
        return IconManager.getInstance().loadRasterizedIcon(
            path,
            Icons::class.java.classLoader, cacheKey, flags
        )
    }

    val Page = load("icons/page.svg", -938751982, 0)
    val Server = load("icons/server.svg", -938751981, 0)
    val PageTS = load("icons/page-ts.svg", -938751980, 0)
    val PageJS = load("icons/page-js.svg", -938751979, 0)
}