package net.prestalife.svirtual.data

import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile

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
    }
}