package uno.redstone.redstoneclaims.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class ChunkCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args == null) {
            return false
        }
        return when (args[0]) {
            "claim" -> {
                ChunkClaim(sender)
                true
            }
            "remove" -> {
                ChunkRemove(sender)
                true
            }
            else -> false
        }
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        if (args == null) {
            return null
        }
        return when (args.size) {
            1 -> mutableListOf("claim", "remove")
            else -> null
        }
    }
}