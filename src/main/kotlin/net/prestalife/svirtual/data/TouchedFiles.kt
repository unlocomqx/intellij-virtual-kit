package net.prestalife.svirtual.data

import com.intellij.openapi.vfs.VirtualFile

class TouchedFiles {
    companion object {
        val instance = TouchedFiles()

        var list: MutableSet<VirtualFile> = mutableSetOf()
    }
}