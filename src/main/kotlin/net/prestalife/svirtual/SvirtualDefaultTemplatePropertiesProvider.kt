package net.prestalife.svirtual

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider
import com.intellij.psi.PsiDirectory
import java.util.*


class SvirtualDefaultTemplatePropertiesProvider : DefaultTemplatePropertiesProvider {
    override fun fillProperties(directory: PsiDirectory, props: Properties) {
        props.setProperty("DIR", directory.name)
    }
}