package net.prestalife.svirtual

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.util.Comparing
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.testFramework.utils.vfs.getPsiFile
import com.intellij.util.Processor
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import net.prestalife.svirtual.data.TouchedFiles

class SvirtualFileSearchContributor : ChooseByNameContributorEx {
    override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
        val names = TouchedFiles.list.mapNotNull { it.getUserData(TouchedFiles.VIRTUAL_FILE_NAME) }.toTypedArray()
        names.forEach { if (!processor.process(it)) return@forEach }
    }

    override fun processElementsWithName(
        name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters
    ) {
        val files =
            TouchedFiles.list.filter { Comparing.strEqual(it.getUserData(TouchedFiles.VIRTUAL_FILE_NAME), name) }
        files.forEach { if (!processor.process(it.getPsiFile(parameters.project))) return@forEach }
    }
}

