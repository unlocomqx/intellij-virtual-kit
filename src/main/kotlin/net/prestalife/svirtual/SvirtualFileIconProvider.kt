package net.prestalife.svirtual

import com.intellij.ide.FileIconProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.Icon

class SvirtualFileIconProvider : FileIconProvider {
    override fun getIcon(file: VirtualFile, flags: Int, project: Project?): Icon? {

        val filename = file.name

        if (filename == "+page.svelte") {
            return Icons.Page
        }

        if (filename.matches(Regex("\\+page\\.server\\.(ts|js)"))) {
            return Icons.Server
        }

        if (filename.matches(Regex("\\+page\\.(ts|js)"))) {
            val extension = file.extension
            return if (extension == "ts") Icons.PageTS else Icons.PageJS
        }

        return null
    }
}
