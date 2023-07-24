package net.prestalife.svirtual

import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
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
            val newName = "$route.svelte"
            TouchedFiles.addFile(file, newName)
            return newName
        }

        // check if filename matches +page.server.ts using regex
        if (file.name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            val newName = "$route.server.ts"
            TouchedFiles.addFile(file, newName)
            return newName
        }

        if (file.name.matches(Regex("\\+page\\.(ts|js)"))) {
            val extension = file.extension
            val newName = "$route.$extension"
            TouchedFiles.addFile(file, newName)
            return newName
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
