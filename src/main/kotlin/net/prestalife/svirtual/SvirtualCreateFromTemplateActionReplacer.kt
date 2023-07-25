package net.prestalife.svirtual

import com.intellij.ide.fileTemplates.CreateFromTemplateActionReplacer
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.ide.fileTemplates.FileTemplateUtil
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import net.prestalife.svirtual.helpers.SvirtualFile
import javax.swing.Icon


class SvirtualCreateFromTemplateActionReplacer : CreateFromTemplateActionReplacer {

    override fun replaceCreateFromFileTemplateAction(fileTemplate: FileTemplate?): AnAction {
        val filename = (fileTemplate?.name + "." + fileTemplate?.extension)
        val icon = SvirtualFile.generateIcon(filename) ?: Icons.Svelte
        return CreateSvelteKitFileAction(fileTemplate, filename, icon)
    }

    class CreateSvelteKitFileAction(private val fileTemplate: FileTemplate?, text: String, icon: Icon) :
        AnAction(text, null, icon) {
        override fun actionPerformed(e: AnActionEvent) {
            val project = e.project
            if (project == null) {
                Messages.showErrorDialog("No project found.", "Error")
                return
            }

            val target = e.getData(CommonDataKeys.VIRTUAL_FILE)
            val targetDir = if (target?.isDirectory == true) target else target?.parent
            if (targetDir == null) {
                Messages.showErrorDialog("Cannot determine the target directory.", "Error")
                return
            }

            val targetPsiDir = PsiManager.getInstance(project).findDirectory(targetDir)
            if (targetPsiDir == null) {
                Messages.showErrorDialog("Cannot determine the target directory.", "Error")
                return
            }

            // check if the file already exists
            val fileName = fileTemplate?.name + "." + fileTemplate?.extension
            val targetFile = targetDir.findChild(fileName)
            if (targetFile != null) {
                Messages.showErrorDialog("File already exists.", "Error")
                return
            }

            if (fileTemplate != null) {
                ApplicationManager.getApplication().runWriteAction {
                    val psiFile = FileTemplateUtil.createFromTemplate(
                        fileTemplate, fileName, null, targetPsiDir
                    )
                    if ((psiFile as PsiFile).virtualFile != null) {
                        FileEditorManager.getInstance(project).openFile(psiFile.virtualFile, true)
                        ProjectView.getInstance(project).select(psiFile, psiFile.virtualFile, true)
                    }
                }
            }
        }
    }
}