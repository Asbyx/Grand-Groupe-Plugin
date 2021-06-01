package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.Main;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldInitEvent;

public class WorldEvents implements Listener
{
	@EventHandler
	public void moveToLobby(PlayerJoinEvent event) {
		if (!Main.inGamePlayers.contains(event.getPlayer().getUniqueId()))
		Worlds.teleportToLobby(event.getPlayer());
	}
	
	@EventHandler
	public void removeLag(WorldInitEvent event) {
		event.getWorld().setKeepSpawnInMemory(false);
	}
}
