package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import java.awt.Color

class SveltePageNode(
    route: String,
    psiFileNode: PsiFileNode,
    viewSettings: com.intellij.ide.projectView.ViewSettings?
) :
    PsiFileNode(psiFileNode.project, psiFileNode.value, viewSettings) {

    private var route: String

    init {
        this.icon = psiFileNode.icon
        this.route = route
    }

    override fun updateImpl(data: PresentationData) {
        data.presentableText = route
//        data.background = Color(255, 0, 0 )
        return
//        super.updateImpl(data)
    }
}