package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BuildPermissions implements Listener
{
	private static final List<Material> authorizedOOBMaterials = computeAuthorizedOOBMaterials();
	
	private static List<Material> computeAuthorizedOOBMaterials()
	{
		ArrayList<Material> l = new ArrayList<>();
		l.add(Material.TNT);
		l.add(Material.TORCH);
		l.add(Material.REDSTONE_TORCH);
		l.add(Material.FLINT_AND_STEEL);
		l.add(Material.BUCKET);
		l.add(Material.LAVA_BUCKET);
		l.add(Material.WATER_BUCKET);
		return l;
	}
	
	@EventHandler
	public void checkBlockBuild(BlockCanBuildEvent event)
	{
		Player player = Objects.requireNonNull(event.getPlayer());
		FKTeam playerTeam = FKTeam.getTeam(player);
		
		if (playerTeam == null
				|| playerTeam.isInBase(event.getBlock().getLocation())
				|| authorizedOOBMaterials.contains(event.getMaterial())) return;
		
		player.sendMessage(ChatColor.RED + "You cannot build here");
		//if (player.getGameMode() == GameMode.SURVIVAL) player.getInventory().addItem(new ItemStack(event.getMaterial()));
		event.setBuildable(false);
	}
	
	@EventHandler
	public void checkBlockBreak(BlockBreakEvent event)
	{
		FKTeam playerTeam = FKTeam.getTeam(event.getPlayer());
		
		if (FKTeam.allTeams.stream().anyMatch(team -> playerTeam != team && team.isInBase(event.getBlock().getLocation())))
		{
			event.setCancelled(true);
			event.setDropItems(false);
			event.setExpToDrop(0);
		}
	}
}
