package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.openapi.vfs.VirtualFile

class SveltePageNode(
    route: String,
    psiFileNode: PsiFileNode,
    viewSettings: com.intellij.ide.projectView.ViewSettings?
) :
    PsiFileNode(psiFileNode.project, psiFileNode.value, viewSettings) {

    private var route: String

    init {
        this.route = route
    }

    override fun updateImpl(data: PresentationData) {
        data.presentableText = "$route.svelte"
        data.setIcon(Icons.Page)
    }
}
