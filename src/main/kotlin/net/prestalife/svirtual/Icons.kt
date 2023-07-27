package net.prestalife.svirtual

import com.intellij.ui.IconManager
import javax.swing.Icon


// I couldn't generate this using gradle!

object Icons {
    private fun load(path: String): Icon {
        return IconManager.getInstance().getIcon(
            path,
            Icons::class.java.classLoader
        )
    }

    val Svelte = load("icons/svelte.svg")

    val Page = load("icons/page.svg")
    val Server = load("icons/server.svg")
    val PageTS = load("icons/page-ts.svg")
    val PageJS = load("icons/page-js.svg")

    val Layout = load("icons/layout.svg")
}