package ch.grandgroupe.common.features.targeting;

import ch.grandgroupe.common.features.targeting.targets.WorldSpawn;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.*;

public class TargetManager
{
	private final Player player;
	private Target target;
	
	public TargetManager(Player player) {
		this.player = player;
		target      = new WorldSpawn();
	}
	
	public void update() {
		Location targetLocation = target.getLocation(player).clone();
		targetLocation.setY(0);
		//targetLocation.getBlock().setType(Material.LODESTONE);
		
		Arrays.stream(player.getInventory().getContents())
			  .filter(itemStack -> itemStack != null && itemStack.getType() == Material.COMPASS)
			  .forEach(compass ->
			  {
				  CompassMeta meta = Objects.requireNonNull((CompassMeta) compass.getItemMeta());
				  meta.setLodestoneTracked(true);
				  meta.setLodestone(targetLocation);
				  compass.setItemMeta(meta);
			  });
		
		player.setCompassTarget(target.getLocation(player));
	}
	
	public void setTarget(Target target) {
		if (target.canBeSetFor(player))
			this.target = target;
		else
			player.sendMessage(ChatColor.RED + target.getErrorMessage(player));
	}
}
