package net.prestalife.svirtual

import com.intellij.ide.IdeBundle
import com.intellij.ide.actions.searcheverywhere.*
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereFiltersStatisticsCollector.FileTypeFilterCollector
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.util.ProgressIndicatorUtils
import com.intellij.psi.PsiManager
import com.intellij.util.Processor
import com.intellij.util.containers.ContainerUtil
import org.jetbrains.annotations.NotNull

class SvirtualSearchEverywhereContributor(@NotNull event: AnActionEvent) : FileSearchEverywhereContributor(event) {

    private val myFilter = createFileTypeFilter(this.myProject)

    override fun processElement(
        progressIndicator: ProgressIndicator,
        consumer: Processor<in FoundItemDescriptor<Any>>,
        model: FilteringGotoByModel<*>?,
        element: Any?,
        degree: Int
    ): Boolean {
        return super.processElement(progressIndicator, consumer, model, element, degree)
    }

    override fun fetchElements(pattern: String, progressIndicator: ProgressIndicator, consumer: Processor<in Any>) {
        super.fetchElements(pattern, progressIndicator, consumer)
    }

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

    override fun getActions(onChanged: Runnable): List<AnAction?> {
        return doGetActions(myFilter, FileTypeFilterCollector(), onChanged)
    }

    override fun getGroupName(): String {
        return IdeBundle.message("search.everywhere.group.name.files")
    }

    override fun getSortWeight(): Int {
        return 99
    }

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return PSIPresentationBgRendererWrapper.wrapIfNecessary(SvirtualSearchEverywhereContributor(initEvent))
        }
    }
}