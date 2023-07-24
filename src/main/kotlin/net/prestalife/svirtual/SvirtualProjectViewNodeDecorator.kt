package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.ProjectViewNodeDecorator
import com.intellij.ide.projectView.impl.nodes.NestingTreeNode
import com.intellij.openapi.vfs.VirtualFile
import net.prestalife.svirtual.data.TouchedFiles
import net.prestalife.svirtual.settings.AppSettingsState

class SvirtualProjectViewNodeDecorator : ProjectViewNodeDecorator {
    override fun decorate(node: ProjectViewNode<*>, presentation: PresentationData) {
        val settings = AppSettingsState.instance
        if (!settings.modifyProjectTree) {
            return
        }

        val name = node.virtualFile?.name ?: return

        if (name == "+page.svelte") {
            val route = getRoute(node)
            presentation.presentableText = "$route.svelte"
            TouchedFiles.addFile(node.virtualFile as VirtualFile, presentation.presentableText)
            if (settings.modifyFileIcons) {
                presentation.setIcon(Icons.Page)
            }
            return
        }

        // check if filename matches +page.server.ts using regex
        if (name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            val route = getRoute(node)
            presentation.presentableText = "$route.server.ts"
            TouchedFiles.addFile(node.virtualFile as VirtualFile, presentation.presentableText)
            if (settings.modifyFileIcons) {
                presentation.setIcon(Icons.Server)
            }
            return
        }

        if (name.matches(Regex("\\+page\\.(ts|js)"))) {
            val extension = node.virtualFile?.extension
            val route = getRoute(node)
            presentation.presentableText = "$route.$extension"
            TouchedFiles.addFile(node.virtualFile as VirtualFile, presentation.presentableText)
            if (settings.modifyFileIcons) {
                presentation.setIcon(if (extension == "ts") Icons.PageTS else Icons.PageJS)
            }
            return
        }
    }

    private fun getRoute(node: ProjectViewNode<*>): String {
        var parent = node.parent ?: return ""
        if (parent is NestingTreeNode) {
            parent = parent.parent ?: return ""
        }
        return (if (parent.name == "routes") "index" else parent.name) ?: return ""
    }
}
