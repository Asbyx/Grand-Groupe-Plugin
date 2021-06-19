package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.Main;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.WorldInitEvent;

import java.util.Objects;

public class WorldEvents implements Listener
{
	@EventHandler
	public void moveToLobby(PlayerJoinEvent event) {
		if (!Main.inGamePlayers.contains(event.getPlayer().getUniqueId()))
			Worlds.teleportToLobby(event.getPlayer());
	}
	
	@EventHandler
	public void respawn(PlayerRespawnEvent event) {
		if (!event.isBedSpawn()) event.setRespawnLocation(Worlds.OVERWORLD.get().getSpawnLocation());
	}
	
	@EventHandler
	public void removeLag(WorldInitEvent event) {
		event.getWorld().setKeepSpawnInMemory(false);
	}
	
	@EventHandler
	public void teleport(PlayerPortalEvent event) {
		Location to = Objects.requireNonNull(event.getTo());
		to.setWorld(Objects.requireNonNull(to.getWorld()).getEnvironment() == World.Environment.NORMAL
					? Worlds.OVERWORLD.get()
					: Worlds.NETHER.get());
		event.setTo(to);
	}
}
