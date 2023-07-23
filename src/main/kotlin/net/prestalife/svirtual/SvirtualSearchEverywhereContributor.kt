package net.prestalife.svirtual

import com.intellij.ide.actions.searcheverywhere.FileSearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.FoundItemDescriptor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.util.ProgressIndicatorUtils
import com.intellij.psi.PsiManager
import com.intellij.util.Processor
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.annotations.NotNull

class SvirtualSearchEverywhereContributor(@NotNull event: AnActionEvent) : FileSearchEverywhereContributor(event) {

    override fun fetchWeightedElements(
        rawPattern: String, progressIndicator: ProgressIndicator, consumer: Processor<in FoundItemDescriptor<Any>>
    ) {
        if (myProject == null) {
            return
        }
        val res: MutableList<FoundItemDescriptor<Any>> = ArrayList()
        ProgressIndicatorUtils.yieldToPendingWriteActions()
        val psiManager = PsiManager.getInstance(myProject)
        ProgressIndicatorUtils.runInReadActionWithWriteActionPriority({

            ContainerUtil.process(res, consumer)
        }, progressIndicator)
    }

    override fun getGroupName(): String {
        return "Svirtual"
    }

    override fun getSortWeight(): Int {
        return 99
    }

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return SvirtualSearchEverywhereContributor(initEvent)
        }
    }
}