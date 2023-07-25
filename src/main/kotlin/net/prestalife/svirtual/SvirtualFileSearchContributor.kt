package net.prestalife.svirtual

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.util.Processor
import com.intellij.util.TimeoutUtil
import com.intellij.util.indexing.FindSymbolParameters
import com.intellij.util.indexing.IdFilter
import net.prestalife.svirtual.helpers.SvirtualFile

class SvirtualFileSearchContributor : ChooseByNameContributorEx {
    companion object {
        val filesMap = mutableMapOf<String, VirtualFile>()
        private val LOG = Logger.getInstance(
            SvirtualFileSearchContributor::class.java
        )
    }

    override fun processNames(processor: Processor<in String>, scope: GlobalSearchScope, filter: IdFilter?) {
        val start = System.nanoTime()
        filesMap.clear()

        val names = mutableListOf<String>()
        val files = FilenameIndex.getVirtualFilesByName("+page.svelte", scope) +
                FilenameIndex.getVirtualFilesByName("+page.server.ts", scope) +
                FilenameIndex.getVirtualFilesByName("+page.server.js", scope) +
                FilenameIndex.getVirtualFilesByName("+page.ts", scope) +
                FilenameIndex.getVirtualFilesByName("+page.js", scope)

        files.forEach {
            val name = SvirtualFile.generateName(it)
            if (name != null) {
                names.add(name)
                filesMap[name] = it
            }
        }

        names.forEach { if (!processor.process(it)) return@forEach }
        if (LOG.isDebugEnabled) {
            LOG.debug("All SvelteKit names retrieved:" + TimeoutUtil.getDurationMillis(start))
        }
    }

    override fun processElementsWithName(
        name: String, processor: Processor<in NavigationItem>, parameters: FindSymbolParameters
    ) {
        val files = filesMap.filter { Comparing.equal(it.key, name) }.map { it.value }

        SvirtualFile.convertVirtualFilesToPsiFiles(parameters.project, files)
            .forEach { if (!processor.process(it)) return@forEach }
    }
}

