package uno.redstone.redstoneclaims.commands

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.flags.Flags
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChunkClaim() {
    constructor(sender: CommandSender) : this() {
        if (sender !is Player) {
            sender.sendMessage(Component.text("Must be a player!").color(NamedTextColor.RED))
            return
        }
        val container = WorldGuard.getInstance().platform.regionContainer
        val regions = container[BukkitAdapter.adapt(sender.world)]
        val min = BlockVector3.at(sender.chunk.x * 16, sender.world.minHeight, sender.chunk.z * 16)
        val max = BlockVector3.at(sender.chunk.x * 16 + 15, sender.world.maxHeight, sender.chunk.z * 16 + 15)
        var regionCount = 0
        if (regions != null) {
            while (regions.hasRegion("${sender.name}_${regionCount}")) {
                regionCount++
            }
        }
        val regionName = "${sender.name}_${regionCount}"
        val region = ProtectedCuboidRegion(regionName, min, max)
        region.owners.addPlayer(sender.name)
        @Suppress("DEPRECATION")
        region.setFlag(Flags.GREET_TITLE, "Welcome\nto ${sender.name}'s region!")
        @Suppress("DEPRECATION")
        region.setFlag(Flags.FAREWELL_TITLE, "Farewell\n")
        if (regions != null) {
            if (region.getIntersectingRegions(regions.regions.values).size > 0) {
                sender.sendMessage(Component.text("Chunk already claimed!").color(NamedTextColor.RED))
                return
            }
        }
        regions?.addRegion(region)
        sender.sendMessage(Component.text("Chunk claimed!").color(NamedTextColor.GREEN))
    }
}