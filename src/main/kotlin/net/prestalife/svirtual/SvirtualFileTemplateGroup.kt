package net.prestalife.svirtual

import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory


class SvirtualFileTemplateGroup : FileTemplateGroupDescriptorFactory {
    override fun getFileTemplatesDescriptor(): FileTemplateGroupDescriptor {
        val group = FileTemplateGroupDescriptor("SvelteKit", Icons.Svelte)
        group.addTemplate(FileTemplateDescriptor("+page.svelte", Icons.Page))
        group.addTemplate(FileTemplateDescriptor("+layout.svelte", Icons.Layout))
        return group
    }
}