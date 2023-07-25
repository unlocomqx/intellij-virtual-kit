package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import net.prestalife.svirtual.helpers.SvirtualFile
import net.prestalife.svirtual.settings.AppSettingsState

class SvirtualProjectViewNodeDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>, presentation: PresentationData) {
        val settings = AppSettingsState.instance
        if (!settings.modifyProjectTree) {
            return
        }

        val newName = SvirtualFile.generateName(node.virtualFile!!)

        if (newName != null) {
            presentation.presentableText = newName
        }

        if (settings.modifyFileIcons) {
            val icon = SvirtualFile.generateIcon(node.virtualFile!!.name)
            if (icon != null) {
                presentation.setIcon(icon)
            }
        }
    }
}
