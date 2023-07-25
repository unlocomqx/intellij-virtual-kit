package net.prestalife.svirtual.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.ProjectManager
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.UI
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
    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String {
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
        ProjectManager.getInstance().openProjects.forEach { ProjectManager.getInstance().reloadProject(it) }
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
            .addComponent(
                UI.PanelFactory.panel(nestRouteFilesCheckbox)
                    .withComment("Nest +page.server.js and +page.js under +page.svelte. May require restart.")
                    .createPanel(), 1
            )
            .addComponent(
                UI.PanelFactory.panel(modifyProjectTreeCheckbox)
                    .withComment("Rename route files to {route}.svelte and {route}.server.js etc...").createPanel(), 1
            ).addComponent(
                UI.PanelFactory.panel(modifyFileIconsCheckbox)
                    .withComment("Display different icons for route file types").createPanel(), 1
            )
            .addComponent(modifyTabsTitlesCheckbox, 1).addComponentFillVertically(JPanel(), 0)
            .panel
    }

    val preferredFocusedComponent: JComponent
        get() = nestRouteFilesCheckbox

    @get:NotNull
    var nestRouteFiles: Boolean
        get() = nestRouteFilesCheckbox.isSelected
        set(state) {
            nestRouteFilesCheckbox.isSelected = state
        }
    var modifyProjectTree: Boolean
        get() = modifyProjectTreeCheckbox.isSelected
        set(state) {
            modifyProjectTreeCheckbox.isSelected = state
        }

    var modifyFileIcons: Boolean
        get() = modifyFileIconsCheckbox.isSelected
        set(state) {
            modifyFileIconsCheckbox.isSelected = state
        }

    var modifyTabsTitles: Boolean
        get() = modifyTabsTitlesCheckbox.isSelected
        set(state) {
            modifyTabsTitlesCheckbox.isSelected = state
        }
}

