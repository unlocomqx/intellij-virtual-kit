package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.impl.nodes.PsiFileNode

class SveltePageScriptNode(
    route: String,
    psiFileNode: PsiFileNode,
    viewSettings: com.intellij.ide.projectView.ViewSettings?
) :
    PsiFileNode(psiFileNode.project, psiFileNode.value, viewSettings) {

    private var route: String
    private var psiFileNode: PsiFileNode

    init {
        this.route = route
        this.psiFileNode = psiFileNode
    }

    override fun updateImpl(data: PresentationData) {
        val extension = psiFileNode.virtualFile?.extension
        data.presentableText = "$route.$extension"
        data.setIcon(if(extension == "ts") Icons.PageTS else Icons.PageJS)
    }
}