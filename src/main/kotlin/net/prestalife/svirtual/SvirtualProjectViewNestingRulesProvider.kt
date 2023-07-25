package net.prestalife.svirtual

import com.intellij.ide.projectView.ProjectViewNestingRulesProvider
import net.prestalife.svirtual.settings.AppSettingsState

class SvirtualProjectViewNestingRulesProvider : ProjectViewNestingRulesProvider {
    override fun addFileNestingRules(consumer: ProjectViewNestingRulesProvider.Consumer) {
        val settings = AppSettingsState.instance
        if (!settings.nestRouteFiles) {
            return
        }
        consumer.addNestingRule("+page.svelte", "+page.server.ts")
        consumer.addNestingRule("+page.svelte", "+page.server.js")
        consumer.addNestingRule("+page.svelte", "+page.ts")
        consumer.addNestingRule("+page.svelte", "+page.js")
        consumer.addNestingRule("+page.svelte", "+server.ts")
        consumer.addNestingRule("+page.svelte", "+server.js")

        consumer.addNestingRule("+layout.svelte", "+layout.server.ts")
        consumer.addNestingRule("+layout.svelte", "+layout.server.js")
        consumer.addNestingRule("+layout.svelte", "+layout.ts")
        consumer.addNestingRule("+layout.svelte", "+layout.js")
    }

}
