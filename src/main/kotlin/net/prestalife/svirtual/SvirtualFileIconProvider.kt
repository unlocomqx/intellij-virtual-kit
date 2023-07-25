package net.prestalife.svirtual

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import net.prestalife.svirtual.helpers.SvirtualFile
import net.prestalife.svirtual.settings.AppSettingsState
import javax.swing.Icon

class SvirtualFileIconProvider : FileIconProvider {
    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {
        val settings = AppSettingsState.instance
        if (!settings.modifyFileIcons) {
            return null
        }

        return SvirtualFile.generateIcon(file.name)
    }
}
