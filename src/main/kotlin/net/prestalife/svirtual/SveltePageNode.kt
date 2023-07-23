package net.prestalife.svirtual

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.projectView.impl.nodes.PsiFileNode
import com.intellij.ui.IconManager
import javax.swing.Icon

class SveltePageNode(
    route: String,
    psiFileNode: PsiFileNode,
    viewSettings: com.intellij.ide.projectView.ViewSettings?
) :
    PsiFileNode(psiFileNode.project, psiFileNode.value, viewSettings) {

    private fun load(path: String, cacheKey: Int, flags: Int): Icon {
        return IconManager.getInstance().loadRasterizedIcon(
            path,
            SveltePageNode::class.java.getClassLoader(), cacheKey, flags
        )
    }

    val pageIcon: Icon = load("icons/page.svg", -938751982, 0)
    private var route: String

    init {
        this.icon = psiFileNode.icon
        this.route = route
    }

    override fun updateImpl(data: PresentationData) {
        data.presentableText = "$route.svelte"
        data.setIcon(pageIcon)
//        data.background = Color(255, 0, 0 )
        return
//        super.updateImpl(data)
    }
}