package uno.redstone.redstoneclaims.commands

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChunkRemove() {
    constructor(sender: CommandSender) : this() {
        if (sender !is Player) {
            sender.sendMessage(Component.text("Must be a player!").color(NamedTextColor.RED))
            return
        }

        val container = WorldGuard.getInstance().platform.regionContainer
        val regions = container[BukkitAdapter.adapt(sender.world)]

        val regionIDs = regions?.getApplicableRegionsIDs(BlockVector3.at(sender.location.x, sender.location.y, sender.location.z)) ?: return
        for (regionID in regionIDs) {
            val region = regions.getRegion(regionID)
            if (region == null || !region.owners.contains(sender.name)) {
                continue
            }
            regions.removeRegion(regionID)
            sender.sendMessage(Component.text("Chunk has been removed").color(NamedTextColor.GREEN))
        }
    }
}