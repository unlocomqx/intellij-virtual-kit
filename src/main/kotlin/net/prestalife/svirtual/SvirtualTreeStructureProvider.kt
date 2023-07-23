package net.prestalife.svirtual;

import com.intellij.ide.projectView.TreeStructureProvider
import com.intellij.ide.projectView.ViewSettings
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ide.util.treeView.AbstractTreeNode
import java.util.ArrayList

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
        val route = if (parent.name=="routes") "index" else parent.name

        for (child in children) {
            // check if child is instance of PsiFileNode
            if (child !is PsiFileNode) {
                result.add(child)
                continue
            }

            if (child.virtualFile?.name == "+page.svelte") {
//                    result.add(child)
                continue
            }
            result.add(child)
        }
        return result
    }

}
