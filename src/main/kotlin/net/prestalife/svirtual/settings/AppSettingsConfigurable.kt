package net.prestalife.svirtual.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.xmlb.XmlSerializerUtil
import org.jetbrains.annotations.Nls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import javax.swing.JCheckBox
import javax.swing.JComponent

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
        return mySettingsComponent!!.myPanel
    }

    override fun isModified(): Boolean {
        return mySettingsComponent!!.myPanel.isModified()
    }

    override fun apply() {
        mySettingsComponent!!.myPanel.apply()
        ProjectManager.getInstance().openProjects.forEach { ProjectManager.getInstance().reloadProject(it) }
    }

    override fun reset() {
        mySettingsComponent!!.myPanel.reset()
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}

/**
 * Supports creating and managing a [DialogPanel] for the Settings Dialog.
 */
class AppSettingsComponent {
    val myPanel: DialogPanel
    private lateinit var nestRouteFilesCheckbox: JCheckBox

    init {
        val state = AppSettingsState.instance
        myPanel = panel {
            row {
                checkBox("Nest route files").comment("Nest +page.server.js and +page.js under +page.svelte. May require restart.")
                    .bindSelected(state::nestRouteFiles)
                    .component.apply { nestRouteFilesCheckbox = this }
            }
            row {
                checkBox("Modify project tree").comment("Rename route files to {route}.svelte and {route}.server.js etc...")
                    .bindSelected(state::modifyProjectTree)
            }
            row {
                checkBox("Modify file icons").comment("Display different icons for route file types")
                    .bindSelected(state::modifyFileIcons)
            }
            row {
                checkBox("Modify tabs titles").bindSelected(state::modifyTabsTitles)
            }
        }
    }


    val preferredFocusedComponent: JComponent
        get() = nestRouteFilesCheckbox
}

@State(
    name = "net.prestalife.svirtual.settings.AppSettingsState",
    storages = [Storage(value = "VirtualKitPlugin.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var nestRouteFiles: Boolean = true
    var modifyProjectTree: Boolean = true
    var modifyFileIcons: Boolean = true
    var modifyTabsTitles: Boolean = true

    @Nullable
    override fun getState(): AppSettingsState {
        return this
    }

    override fun loadState(@NotNull state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: AppSettingsState
            get() = ApplicationManager.getApplication().getService(
                AppSettingsState::class.java
            )
    }
}