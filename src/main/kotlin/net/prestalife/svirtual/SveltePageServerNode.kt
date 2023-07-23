package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.impl.nodes.PsiFileNode

class SveltePageServerNode(
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
        data.presentableText = "$route.server.js"
        data.setIcon(Icons.Server)
    }
}