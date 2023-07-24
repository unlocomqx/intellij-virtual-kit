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
            val new_name = "$route.svelte"
            TouchedFiles.addFile(file, new_name)
            return new_name
        }

        // check if filename matches +page.server.ts using regex
        if (file.name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            val new_name = "$route.server.ts"
            TouchedFiles.addFile(file, new_name)
            return new_name
        }

        if (file.name.matches(Regex("\\+page\\.(ts|js)"))) {
            val extension = file.extension
            val new_name = "$route.$extension"
            TouchedFiles.addFile(file, new_name)
            return new_name
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
