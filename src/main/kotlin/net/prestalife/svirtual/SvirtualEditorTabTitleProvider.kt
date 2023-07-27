package net.prestalife.svirtual

import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import net.prestalife.svirtual.helpers.SvirtualFile
import net.prestalife.svirtual.settings.AppSettingsState

class SvirtualEditorTabTitleProvider : EditorTabTitleProvider {
    override fun getEditorTabTitle(project: Project, file: VirtualFile): String? {
        val settings = AppSettingsState.instance
        if (!settings.modifyTabsTitles) {
            return null
        }

        return SvirtualFile.generateNameFromFile(file)
    }
}
