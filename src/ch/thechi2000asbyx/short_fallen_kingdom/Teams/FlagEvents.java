package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class FlagEvents implements Listener
{
	@EventHandler
	public void moveFlag(PlayerDropItemEvent event)
	{
		Item item = event.getItemDrop();
		World world = item.getWorld();
		Player player = event.getPlayer();
		FKTeam team = FKTeam.getTeam(player);
		
		if (team == null) return;
		
		Bukkit.broadcastMessage("Team not null");
		
		if (item.getItemStack().getType() == Material.CLOCK)
		{
			Bukkit.broadcastMessage("Clock dropped");
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(Main.NAME)),
					() ->
					{
						if (!team.setFlagLocation(item.getLocation()))
							player.sendMessage(ChatColor.RED + "Cannot place flag outside of the base");
						
						item.remove();
						player.getInventory().addItem(new ItemStack(Material.CLOCK));
					},
					20);
		}
	}
	
	@EventHandler
	public void checkBreakFlag(EntityExplodeEvent event)
	{
		checkBreakFlag();
	}
	
	@EventHandler
	public void checkBreakFlag(BlockBurnEvent event)
	{
		checkBreakFlag();
	}
	
	@EventHandler
	public void checkBreakFlag(BlockBreakEvent event)
	{
		checkBreakFlag();
	}
	
	private void checkBreakFlag()
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(Main.NAME)),
				() ->
						FKTeam.allTeams.stream().filter(t -> !t.isEliminated() && t.isFlagDestroyed()).forEach(t ->
						{
							t.eliminate();
							Bukkit.broadcastMessage(ChatColor.GREEN + "The team " + t.getName() + " was eliminated");
						}),
				1);
	}
}
