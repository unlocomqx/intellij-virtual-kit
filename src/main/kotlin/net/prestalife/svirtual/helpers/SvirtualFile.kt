package net.prestalife.svirtual.helpers

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

class SvirtualFile {

    companion object {
        fun generateName(file: VirtualFile): String? {
            val name = file.name
            val route = getRoute(file) ?: return null

            if (name == "+page.svelte") {
                return "$route.svelte"
            }

            if (name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
                return "$route.server.ts"
            }

            if (name.matches(Regex("\\+page\\.(ts|js)"))) {
                val extension = file.extension
                return "$route.$extension"
            }

            return null
        }

        private fun getRoute(file: VirtualFile): String? {
            val parent = file.parent ?: return null
            return (if (parent.name == "routes") "index" else parent.name)
        }

        fun generateNames(files: List<VirtualFile>): List<String> {
            return files.mapNotNull { generateName(it) }
        }

        fun convertVirtualFilesToPsiFiles(project: Project, files: Collection<VirtualFile?>): Collection<PsiFile> {
            val psiFiles: MutableCollection<PsiFile> = HashSet()
            var psiManager: PsiManager? = null
            for (file in files) {
                if (psiManager == null) {
                    psiManager = PsiManager.getInstance(project)
                }
                val psiFile = psiManager.findFile(file!!)
                if (psiFile != null) {
                    psiFiles.add(psiFile)
                }
            }
            return psiFiles
        }
    }
}