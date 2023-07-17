package uno.redstone.redstoneclaims.commands

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.Flags
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import com.sk89q.worldguard.protection.regions.ProtectedRegion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class ChunkCommand : CommandExecutor, TabCompleter {
    private lateinit var sender: CommandSender

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        this.sender = sender
        return when (args?.get(0)) {
            "claim" -> chunkClaim()
            else -> false
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        return when (args?.size) {
            1 -> mutableListOf("claim", "remove")
            else -> null
        }
    }


    private fun chunkClaim(): Boolean {
        if (sender !is Player) {
            sender.sendMessage(Component.text("Must be a player!").color(NamedTextColor.RED))
            return false
        }

        val container = WorldGuard.getInstance().platform.regionContainer
        val regions = container[BukkitAdapter.adapt((sender as Player).world)]

        val senderChunkLocation = (sender as Player).chunk
        val min = BlockVector3.at(senderChunkLocation.x * 16, senderChunkLocation.world.minHeight, senderChunkLocation.z * 16)
        val max = BlockVector3.at(senderChunkLocation.x * 16 + 15, senderChunkLocation.world.maxHeight, senderChunkLocation.z * 16 + 15)

        var i = 0
        if (regions != null) {
            while (regions.hasRegion("${sender.name}_$i")) {
                i++
            }
        }

        val regionName = "${sender.name}_$i"
        val region: ProtectedRegion = ProtectedCuboidRegion(regionName, min, max)

        @Suppress("DEPRECATION")
        region.setFlag(Flags.GREET_MESSAGE, "Welcome!")
        @Suppress("DEPRECATION")
        region.setFlag(Flags.FAREWELL_MESSAGE, "Farewell!")

        regions?.addRegion(region)

        return true
    }
}