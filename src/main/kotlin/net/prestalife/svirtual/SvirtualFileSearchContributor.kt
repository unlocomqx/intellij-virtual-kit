package net.prestalife.svirtual

import com.intellij.navigation.ChooseByNameContributorEx
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.diagnostic.Logger
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

        val files = arrayOf(
            "+page.svelte",
            "+page.server.ts",
            "+page.server.js",
            "+page.ts",
            "+page.js",
            "+server.ts",
            "+server.js",

            "+layout.svelte",
            "+layout.server.ts",
            "+layout.server.js",
            "+layout.ts",
            "+layout.js"
        ).map { FilenameIndex.getVirtualFilesByName(it, scope) }.flatten()

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
        val files = filesMap.filter { it.key == name }.values

        SvirtualFile.convertVirtualFilesToPsiFiles(parameters.project, files)
            .forEach { if (!processor.process(it)) return@forEach }
    }
}

