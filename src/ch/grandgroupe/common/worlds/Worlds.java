package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Worlds
{
	public enum Type
	{
		NORMAL,
		NETHER,
		THE_END
	}
	
	public static WorldManager OVERWORLD;
	public static WorldManager NETHER;
	public static WorldManager END;
	public static WorldManager LOBBY;
	
	public static void teleportToLobby(Player p) {
		if (LOBBY_LOCATION.getWorld() == null) LOBBY_LOCATION.setWorld(LOBBY.get());
		if (p.getGameMode() != GameMode.CREATIVE) p.setGameMode(GameMode.ADVENTURE);
		p.teleport(LOBBY_LOCATION, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}
	public static void teleportToWorld(Player p, Type t) {
		if (p.getGameMode() != GameMode.CREATIVE) p.setGameMode(GameMode.SURVIVAL);
		p.teleport(getCorrespondingWorldManager(t).get().getSpawnLocation());
	}
	
	public static final Location LOBBY_LOCATION = new Location(null, 0.5, 64, 0.5);
	
	public static void init() {
		OVERWORLD = new WorldManager("world");
		NETHER    = new WorldManager("world_nether");
		END       = new WorldManager("world_the_end");
		LOBBY     = new WorldManager("world_lobby");
		
		if (LOBBY.get() == null) LOBBY.regenerate(WorldType.FLAT, false);
		World lobby = LOBBY.get();
		lobby.setPVP(false);
		lobby.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		lobby.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		lobby.setDifficulty(Difficulty.PEACEFUL);
		lobby.setSpawnLocation(LOBBY_LOCATION);
		
		Bukkit.getPluginManager().registerEvents(new WorldEvents(), Main.PLUGIN);
	}
	
	private static void regenerate(Type type, boolean generateStructures) {
		getCorrespondingWorldManager(type).regenerate(generateStructures);
	}
	
	private static WorldManager getCorrespondingWorldManager(Type type) {
		switch (type) {
			case NORMAL:
				return OVERWORLD;
			case NETHER:
				return NETHER;
			case THE_END:
				return END;
			default:
				return LOBBY;
		}
	}
}

