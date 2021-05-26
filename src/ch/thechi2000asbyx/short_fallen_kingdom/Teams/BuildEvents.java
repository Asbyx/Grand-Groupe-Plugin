package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.common.*;
import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;

import java.util.*;

public class BuildEvents extends AbstractListener
{
	private static final List<Material> authorizedOOBMaterials = Misc.list(Material.TNT,
			Material.TORCH,
			Material.REDSTONE_TORCH,
			Material.FLINT_AND_STEEL,
			Material.BUCKET,
			Material.LAVA_BUCKET,
			Material.WATER_BUCKET);
	
	@EventHandler
	public void checkBlockBuild(BlockCanBuildEvent event) {
		if (isDisabled()) return;
		
		Player player = Objects.requireNonNull(event.getPlayer());
		FKTeam playerTeam = FKTeam.getTeam(player);
		
		if (playerTeam == null
				|| playerTeam.isInBase(event.getBlock().getLocation())
				|| authorizedOOBMaterials.contains(event.getMaterial())) return;
		
		player.sendMessage(ChatColor.RED + "You cannot build here");
		event.setBuildable(false);
		
		Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN, player::updateInventory, 1);
	}
	
	@EventHandler
	public void checkBlockBreak(BlockBreakEvent event) {
		if (isDisabled()) return;
		
		FKTeam playerTeam = FKTeam.getTeam(event.getPlayer());
		
		if (FKTeam.allTeams.stream().anyMatch(team -> playerTeam != team && team.isInBase(event.getBlock().getLocation()))) {
			event.setCancelled(true);
			event.setDropItems(false);
			event.setExpToDrop(0);
		}
	}
}
