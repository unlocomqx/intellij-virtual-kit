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

        val name = node.virtualFile?.name ?: return
        val newName = SvirtualFile.generateName(node.virtualFile!!)

        if (name == "+page.svelte") {
            presentation.presentableText = newName
            if (settings.modifyFileIcons) {
                presentation.setIcon(Icons.Page)
            }
            return
        }

        // check if filename matches +page.server.ts using regex
        if (name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            presentation.presentableText = newName
            if (settings.modifyFileIcons) {
                presentation.setIcon(Icons.Server)
            }
            return
        }

        if (name.matches(Regex("\\+page\\.(ts|js)"))) {
            val extension = node.virtualFile?.extension
            presentation.presentableText = newName
            if (settings.modifyFileIcons) {
                presentation.setIcon(if (extension == "ts") Icons.PageTS else Icons.PageJS)
            }
            return
        }
    }
}
