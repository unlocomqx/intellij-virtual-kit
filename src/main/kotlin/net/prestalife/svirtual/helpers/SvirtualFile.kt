package net.prestalife.svirtual.helpers

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import net.prestalife.svirtual.Icons
import javax.swing.Icon

class SvirtualFile {

    companion object {
        fun generateNameFromFile(file: VirtualFile): String? {
            val name = file.name
            val extension = file.extension
            val dir = file.parent

            if (extension.isNullOrEmpty()) {
                return null
            }

            return generateNameFromFilename(dir, name, extension)
        }

        fun generateNameFromFilename(
            dir: VirtualFile?,
            name: String,
            extension: String
        ): String? {
            val route = getRoute(dir) ?: return null

            if (name == "+page.svelte") {
                return "$route.svelte"
            }

            if (name == "+layout.svelte") {
                return "$route.layout.svelte"
            }

            if (name.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
                return "$route.server.$extension"
            }

            if (name.matches(Regex("\\+page\\.(ts|js)"))) {
                return "$route.$extension"
            }

            if (name.matches(Regex("\\+server\\.(ts|js)"))) {
                return "$route.endpoint.$extension"
            }

            if (name.matches(Regex("\\+layout\\.server\\.(ts|js)"))) {
                return "$route.layout.server.$extension"
            }

            if (name.matches(Regex("\\+layout\\.(ts|js)"))) {
                return "$route.layout.$extension"
            }

            return null
        }

        private fun getRoute(dir: VirtualFile?): String? {
            var parent: VirtualFile = dir ?: return null

            var routeName = sanitizeName(parent.name)

            while (routeName.startsWith('[')) {
                parent = parent.parent ?: return routeName
                val parentRoute = sanitizeName(parent.name)
                routeName = "$parentRoute/$routeName"
            }

            return routeName
        }

        private fun sanitizeName(name: String): String {
            if (name == "routes") {
                return "index"
            }

            return name
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

        fun generateIcon(filename: String): Icon? {
            val extension = filename.substringAfterLast('.')

            if (filename == "+page.svelte") {
                return Icons.Page
            }

            if (filename == "+layout.svelte") {
                return Icons.Layout
            }

            if (filename.matches(Regex("\\+(page|layout)\\.server\\.(ts|js)"))) {
                return Icons.Server
            }

            if (filename.matches(Regex("\\+(page|layout)\\.(ts|js)"))) {
                return if (extension == "ts") Icons.PageTS else Icons.PageJS
            }

            if (filename.matches(Regex("\\+server\\.(ts|js)"))) {
                return if (extension == "ts") Icons.PageTS else Icons.PageJS
            }

            return null
        }
    }
}