package net.prestalife.svirtual.data

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager

class TouchedFiles {
    companion object {
        val VIRTUAL_FILE_NAME = Key<String>("SVIRTUAL_VIRTUAL_FILE_NAME")

        val instance = TouchedFiles()

        var list: MutableSet<VirtualFile> = mutableSetOf()

        fun addFile(file: VirtualFile, newName: String? = null) {
            if (newName != null) {
                file.putUserData(VIRTUAL_FILE_NAME, newName)
            }
            list.add(file)
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