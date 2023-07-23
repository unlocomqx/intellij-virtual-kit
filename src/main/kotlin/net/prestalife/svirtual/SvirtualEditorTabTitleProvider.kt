package net.prestalife.svirtual

import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.rd.util.addUnique
import net.prestalife.svirtual.data.TouchedFiles
import net.prestalife.svirtual.settings.AppSettingsState

class SvirtualEditorTabTitleProvider : EditorTabTitleProvider {
    override fun getEditorTabTitle(project: Project, file: VirtualFile): String? {
        val settings = AppSettingsState.instance
        if (!settings.modifyTabsTitles) {
            return null
        }

        val route = getRoute(file)

        if (file.name == "+page.svelte") {
            TouchedFiles.list.add(file)
            return "$route.svelte"
        }

        // check if filename matches +page.server.ts using regex
        if (file.name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            TouchedFiles.list.add(file)
            return "$route.server.ts"
        }

        if (file.name.matches(Regex("\\+page\\.(ts|js)"))) {
            TouchedFiles.list.add(file)
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

        return parent.name
    }
}
