package uno.redstone.redstoneclaims

import org.bukkit.plugin.java.JavaPlugin
import uno.redstone.redstoneclaims.commands.Chunk

@Suppress("unused")
class RedstoneClaims : JavaPlugin() {
    override fun onEnable() {
        getCommand("chunk")?.setExecutor(Chunk())
        getCommand("chunk")?.tabCompleter = Chunk()
    }
}
