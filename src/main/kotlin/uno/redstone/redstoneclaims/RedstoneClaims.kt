package uno.redstone.redstoneclaims

import org.bukkit.plugin.java.JavaPlugin
import uno.redstone.redstoneclaims.commands.ChunkCommand

@Suppress("unused")
class RedstoneClaims : JavaPlugin() {
    override fun onEnable() {
        registerCommands()
    }

    private fun registerCommands() {
        getCommand("chunk")?.setExecutor(ChunkCommand())
        getCommand("chunk")?.tabCompleter = ChunkCommand()
    }
}
