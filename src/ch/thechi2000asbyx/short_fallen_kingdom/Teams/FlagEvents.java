package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

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
		
		if (item.getItemStack().getType() == Material.CLOCK)
		{
			Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN,
					() ->
					{
						switch (team.setFlagLocation(item.getLocation()))
						{
							case NONE:
								team.stream().forEach(p -> p.sendMessage(ChatColor.GREEN + "Flag moved to " + team.getFlagLocation()));
								break;
							case OUTSIDE_BASE:
								player.sendMessage(ChatColor.RED + "Cannot place flag outside of the base");
								break;
							case INVALID_HEIGHT:
								player.sendMessage(ChatColor.RED + "Cannot place the flag further than 20 blocks above/under the center of the base");
								break;
							case INVALID_WORLD:
								player.sendMessage(ChatColor.RED + "Cannot place the flag in another dimension");
								break;
							case BLOCKING_BLOCKS:
								player.sendMessage(ChatColor.RED + "Cannot place the flag, there are blocks on the way");
								break;
							case TEAM_ELIMINATED:
								player.sendMessage(ChatColor.RED + "Cannot place the flag, your team was eliminated");
								break;
						}
						
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
		Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN,
				() ->
						FKTeam.allTeams.stream().filter(t -> !t.isEliminated() && t.isFlagDestroyed()).forEach(t ->
						{
							t.eliminate();
							Main.broadcast(ChatColor.GREEN + "The team " + t.getName() + " was eliminated");
						}),
				5);
	}
}
