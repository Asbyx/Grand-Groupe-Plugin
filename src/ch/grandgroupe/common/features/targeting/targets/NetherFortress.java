package ch.grandgroupe.common.features.targeting.targets;

import ch.grandgroupe.common.features.targeting.Target;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NetherFortress extends Target
{
	public NetherFortress() {
		super(new ItemStack(Material.NETHER_BRICK, 1));
	}
	
	@Override
	public Location getLocation(Player player) {
		return player.getWorld().getEnvironment() == World.Environment.NETHER
			   ? player.getWorld().locateNearestStructure(player.getLocation(), StructureType.NETHER_FORTRESS, 50, false)
			   : null;
	}
}
