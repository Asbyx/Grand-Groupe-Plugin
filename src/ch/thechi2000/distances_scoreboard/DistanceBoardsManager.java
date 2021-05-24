package ch.thechi2000.distances_scoreboard;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DistanceBoardsManager implements Listener
{
	private final Map<UUID, DistanceBoard> boards;
	
	public DistanceBoardsManager()
	{
		boards = new TreeMap<>();
		Bukkit.getOnlinePlayers().forEach(this::addPlayer);
	}
	
	public void addPlayer(Player player)
	{
		boards.put(player.getUniqueId(), new DistanceBoard(player));
		boards.values().forEach(b -> b.addPlayer(player));
	}
	
	public void removePlayer(Player player)
	{
		boards.values().forEach(b -> b.removePlayer(player));
	}
	
	public void updatePlayer(Player player)
	{
		boards.values().forEach(b -> b.updatePlayer(player));
	}
	
	public void enableScoreboard(Player player)
	{
		boards.get(player.getUniqueId()).enable();
	}
	
	public void disableScoreboard(Player player)
	{
		boards.get(player.getUniqueId()).disable();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		event.setJoinMessage("Welcome !");
		addPlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		removePlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		updatePlayer(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerItemHeld(PlayerItemHeldEvent event)
	{
		ItemStack stack = event.getPlayer().getInventory().getItem(event.getNewSlot());
		if (stack != null && stack.getType() == Material.COMPASS)
			enableScoreboard(event.getPlayer());
		else
			disableScoreboard(event.getPlayer());
	}
}
