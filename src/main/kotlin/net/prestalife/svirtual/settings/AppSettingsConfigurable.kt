package net.prestalife.svirtual.settings;

import com.intellij.openapi.options.Configurable
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

    override fun getPreferredFocusedComponent(): JComponent? {
        return mySettingsComponent!!.preferredFocusedComponent
    }

    @Nullable
    override fun createComponent(): JComponent? {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.instance
        return mySettingsComponent!!.nestRouteFiles != settings.nestRouteFiles ||
                mySettingsComponent!!.modifyProjectTree != settings.modifyProjectTree
    }

    override fun apply() {
        val settings = AppSettingsState.instance
        settings.nestRouteFiles = mySettingsComponent!!.nestRouteFiles
        settings.modifyProjectTree = mySettingsComponent!!.modifyProjectTree
    }

    override fun reset() {
        val settings = AppSettingsState.instance
        mySettingsComponent!!.nestRouteFiles = settings.nestRouteFiles
        mySettingsComponent!!.modifyProjectTree = settings.modifyProjectTree
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

    init {
        panel = FormBuilder.createFormBuilder()
            .addComponent(nestRouteFilesCheckbox, 1)
            .addComponent(modifyProjectTreeCheckbox, 1)
            .addComponentFillVertically(JPanel(), 0)
            .getPanel()
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
}

