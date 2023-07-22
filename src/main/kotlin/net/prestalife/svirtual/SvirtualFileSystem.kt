package net.prestalife.svirtual

import com.intellij.openapi.util.io.FileAttributes
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.VfsImplUtil
import java.io.OutputStream

class SvirtualFileSystem : com.intellij.openapi.vfs.newvfs.NewVirtualFileSystem() {
    companion object {
        const val PROTOCOL = "svirtual"

        val INSTANCE: SvirtualFileSystem
            get() =
                VirtualFileManager.getInstance().getFileSystem(PROTOCOL) as SvirtualFileSystem
    }

    override fun getProtocol() = AAAASvirtualFileSystem.PROTOCOL

    override fun findFileByPath(path: String): VirtualFile? {
        return VfsImplUtil.findFileByPathIfCached(this, path)
    }

    override fun refresh(asynchronous: Boolean) {
        VfsImplUtil.refresh(this, asynchronous)
    }

    override fun refreshAndFindFileByPath(path: String): VirtualFile? {
        return VfsImplUtil.refreshAndFindFileByPath(this, path)
    }

    override fun deleteFile(requestor: Any?, vFile: VirtualFile) = throw UnsupportedOperationException()
    override fun moveFile(requestor: Any?, vFile: VirtualFile, newParent: VirtualFile) =
        throw UnsupportedOperationException()

    override fun renameFile(requestor: Any?, vFile: VirtualFile, newName: String) =
        throw UnsupportedOperationException()

    override fun createChildFile(requestor: Any?, vDir: VirtualFile, fileName: String): VirtualFile =
        throw UnsupportedOperationException()

    override fun createChildDirectory(requestor: Any?, vDir: VirtualFile, dirName: String): VirtualFile =
        throw UnsupportedOperationException()

    override fun copyFile(
        requestor: Any?,
        virtualFile: VirtualFile,
        newParent: VirtualFile,
        copyName: String
    ): VirtualFile = throw UnsupportedOperationException()

    override fun exists(file: VirtualFile): Boolean {
        return file.exists()
    }

    override fun list(file: VirtualFile): Array<String> {
        throw UnsupportedOperationException()
    }

    override fun isDirectory(file: VirtualFile) = file.isDirectory

    override fun getTimeStamp(file: VirtualFile) = file.timeStamp

    override fun setTimeStamp(file: VirtualFile, timeStamp: Long) {
        throw UnsupportedOperationException()
    }

    override fun isWritable(file: VirtualFile) = false

    override fun setWritable(file: VirtualFile, writableFlag: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun contentsToByteArray(file: VirtualFile): ByteArray {
        val stream = file.inputStream!!
        return FileUtil.loadBytes(stream, file.length.toInt())
    }

    override fun getInputStream(file: VirtualFile) = file.inputStream!!

    override fun getOutputStream(
        file: VirtualFile,
        requestor: Any?,
        modStamp: Long,
        timeStamp: Long
    ): OutputStream {
        throw UnsupportedOperationException()
    }

    override fun getLength(file: VirtualFile) = file.length

    override fun extractRootPath(path: String): String {
        throw UnsupportedOperationException()
    }

    override fun findFileByPathIfCached(path: String): VirtualFile? {
        return null
    }

    override fun getRank(): Int = 2

    override fun getAttributes(file: VirtualFile): FileAttributes? {
        throw UnsupportedOperationException()
    }

}