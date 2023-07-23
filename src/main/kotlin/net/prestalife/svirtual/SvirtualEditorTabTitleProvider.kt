package net.prestalife.svirtual

import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class SvirtualEditorTabTitleProvider : EditorTabTitleProvider {
    override fun getEditorTabTitle(project: Project, file: VirtualFile): String? {
        val route = getRoute(file) ?: return null

        if (file.name == "+page.svelte") {
            return "$route.svelte"
        }

        // check if filename matches +page.server.ts using regex
        if (file.name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            return "$route.server.ts"
        }

        if (file.name.matches(Regex("\\+page\\.(ts|js)"))) {
            val extension = file.extension
            return "$route.$extension"
        }

        return null
    }

    private fun getRoute(file: VirtualFile): String {
        val parent = file.parent ?: return ""

        if (parent.name == "routes") {
            return "index"
        }

        return parent.name ?: return ""
    }
}
