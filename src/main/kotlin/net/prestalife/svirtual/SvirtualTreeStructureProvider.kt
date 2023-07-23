package net.prestalife.svirtual;

import com.intellij.ide.projectView.ProjectViewNode
import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode

class SvirtualTreeStructureProvider : TreeStructureProvider {
    override fun modify(
        parent: AbstractTreeNode<*>,
        children: MutableCollection<AbstractTreeNode<*>>,
        settings: ViewSettings?
    ): MutableCollection<AbstractTreeNode<*>> {

        if (parent !is PsiDirectoryNode) {
            return children
        }

        val result = ArrayList<AbstractTreeNode<*>>()
        val route = (if (parent.name == "routes") "index" else parent.name) ?: return children

        for (child in children) {
            if (child !is PsiFileNode) {
                result.add(child)
                continue
            }

            val filename = child.virtualFile?.name

            if (filename == "+page.svelte") {
                result.add(SveltePageNode(route, child, parent.settings))
                continue
            }

            // check if filename matches +page.server.ts using regex
            if (filename?.matches(Regex("\\+page\\.server\\.(ts|js)")) == true) {
                result.add(SveltePageServerNode(route, child, parent.settings))
                continue
            }

            if (filename?.matches(Regex("\\+page\\.(ts|js)")) == true) {
                result.add(SveltePageScriptNode(route, child, parent.settings))
                continue
            }

            result.add(child)
        }
        return result
    }
}
