package uno.redstone.redstoneclaims.commands

import org.bukkit.command.CommandExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

// TODO: Check for permission before running a command

class ChunkCommand : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (args == null) {
            return false
        }
        return when (args.get(0)) {
            "claim" -> {
                TODO()
            }
            "remove" -> {
                TODO()
            }
            "membership" -> {
                TODO()
            }

            else -> false
        }

    }
    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>?): MutableList<String>? {
        return if (args == null) {
            null
        } else {
            mutableListOf("claim", "remove", "ownership")
        }
    }
}