package net.prestalife.svirtual

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileListener
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.VfsImplUtil
import com.intellij.openapi.vfs.newvfs.VirtualFileFilteringListener
import java.util.concurrent.ConcurrentHashMap

class SvirtualFileSystem: com.intellij.openapi.vfs.VirtualFileSystem() {
    private val listenerWrappers: MutableMap<VirtualFileListener, VirtualFileListener> = ConcurrentHashMap()
    companion object {
        const val PROTOCOL = "svirtual"

        val instance: SvirtualFileSystem
            get() =
                VirtualFileManager.getInstance().getFileSystem(PROTOCOL) as SvirtualFileSystem
    }

    override fun getProtocol() = PROTOCOL
    override fun isReadOnly(): Boolean = false

    override fun findFileByPath(path: String): VirtualFile? {
        return VfsImplUtil.findFileByPathIfCached(this, path)
    }

    override fun refresh(asynchronous: Boolean) {
        VfsImplUtil.refresh(this, asynchronous)
    }

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        return VfsImplUtil.refreshAndFindFileByPath(this, path)
    }

    override fun addVirtualFileListener(listener: VirtualFileListener) {
        val wrapper: VirtualFileListener = VirtualFileFilteringListener(listener, this)
        VirtualFileManager.getInstance().addVirtualFileListener(wrapper)
        listenerWrappers[listener] = wrapper
    }

    override fun removeVirtualFileListener(listener: VirtualFileListener) {
        listenerWrappers.remove(listener)?.let {
            VirtualFileManager.getInstance().removeVirtualFileListener(it)
        }
    }

    override fun deleteFile(requestor: Any?, vFile: VirtualFile) = throw UnsupportedOperationException()
    override fun moveFile(requestor: Any?, vFile: VirtualFile, newParent: VirtualFile) = throw UnsupportedOperationException()
    override fun renameFile(requestor: Any?, vFile: VirtualFile, newName: String) = throw UnsupportedOperationException()
    override fun createChildFile(requestor: Any?, vDir: VirtualFile, fileName: String): VirtualFile = throw UnsupportedOperationException()
    override fun createChildDirectory(requestor: Any?, vDir: VirtualFile, dirName: String): VirtualFile = throw UnsupportedOperationException()
    override fun copyFile(requestor: Any?, virtualFile: VirtualFile, newParent: VirtualFile, copyName: String): VirtualFile  = throw UnsupportedOperationException()

}