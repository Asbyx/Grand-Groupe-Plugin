package ch.grandgroupe.common.features.targeting.targets;

import ch.grandgroupe.common.features.targeting.Target;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class WorldSpawn extends Target
{
	public WorldSpawn()
	{
		super(new ItemStack(Material.GRASS_BLOCK, 1));
	}
	
	public Location getLocation(Player player) {
		return Objects.requireNonNull(player.getLocation().getWorld()).getSpawnLocation();
	}
}
