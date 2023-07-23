package net.prestalife.svirtual.settings;

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.JComponent
import javax.swing.JPanel


/**
 * Provides controller functionality for application settings.
 */
class AppSettingsConfigurable : Configurable {

    private var mySettingsComponent: AppSettingsComponent? = null

    // A default constructor with no arguments is required because this implementation
    // is registered in an applicationConfigurable EP
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String? {
        return "VirtualKit Settings"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    @Nullable
    override fun createComponent(): JComponent {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.instance
        return mySettingsComponent!!.nestRouteFiles != settings.nestRouteFiles ||
                mySettingsComponent!!.modifyProjectTree != settings.modifyProjectTree ||
                mySettingsComponent!!.modifyTabsTitles != settings.modifyTabsTitles ||
                mySettingsComponent!!.modifyFileIcons != settings.modifyFileIcons
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.nestRouteFiles = mySettingsComponent!!.nestRouteFiles
        settings.modifyProjectTree = mySettingsComponent!!.modifyProjectTree
        settings.modifyFileIcons = mySettingsComponent!!.modifyFileIcons
        settings.modifyTabsTitles = mySettingsComponent!!.modifyTabsTitles
        ProjectManager.getInstance().openProjects.forEach { project ->
            ApplicationManager.getApplication().runWriteAction {
                VirtualFileManager.getInstance().asyncRefresh {
                    ProjectRootManager.getInstance(project).contentRoots.forEach { root ->
                        VfsUtil.markDirtyAndRefresh(false, true, true, root)
                    }
                }
            }
        }

    }

    override fun reset() {
        val settings = AppSettingsState.instance
        mySettingsComponent!!.nestRouteFiles = settings.nestRouteFiles
        mySettingsComponent!!.modifyProjectTree = settings.modifyProjectTree
        mySettingsComponent!!.modifyFileIcons = settings.modifyFileIcons
        mySettingsComponent!!.modifyTabsTitles = settings.modifyTabsTitles
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}

/**
 * Supports creating and managing a [JPanel] for the Settings Dialog.
 */
class AppSettingsComponent {
    val panel: JPanel
    private val nestRouteFilesCheckbox = JBCheckBox("Nest route files")
    private val modifyProjectTreeCheckbox = JBCheckBox("Modify project tree")
    private val modifyFileIconsCheckbox = JBCheckBox("Modify file icons")
    private val modifyTabsTitlesCheckbox = JBCheckBox("Modify tabs titles")

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(nestRouteFilesCheckbox, 1)
            .addComponent(modifyProjectTreeCheckbox, 1)
            .addComponent(modifyFileIconsCheckbox, 1)
            .addComponent(modifyTabsTitlesCheckbox, 1)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = nestRouteFilesCheckbox

    @get:NotNull
    var nestRouteFiles: Boolean
        get() = nestRouteFilesCheckbox.isSelected
        set(state: Boolean) {
            nestRouteFilesCheckbox.isSelected = state
        }
    var modifyProjectTree: Boolean
        get() = modifyProjectTreeCheckbox.isSelected
        set(state: Boolean) {
            modifyProjectTreeCheckbox.isSelected = state
        }

    var modifyFileIcons: Boolean
        get() = modifyFileIconsCheckbox.isSelected
        set(state: Boolean) {
            modifyFileIconsCheckbox.isSelected = state
        }

    var modifyTabsTitles: Boolean
        get() = modifyTabsTitlesCheckbox.isSelected
        set(state: Boolean) {
            modifyTabsTitlesCheckbox.isSelected = state
        }
}

