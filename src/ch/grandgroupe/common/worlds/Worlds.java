package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.worlds.manager.LobbyManager;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.org.eclipse.sisu.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class Worlds
{
	public static final Location LOBBY_LOCATION = new Location(null, 0.5, 64, 0.5);
	public static WorldManager OVERWORLD;
	public static WorldManager NETHER;
	public static WorldManager END;
	public static WorldManager LOBBY;
	
	public static void teleportToLobby(Player p) {
		if (LOBBY_LOCATION.getWorld() == null) LOBBY_LOCATION.setWorld(LOBBY.get());
		if (p.getGameMode() != GameMode.CREATIVE) p.setGameMode(GameMode.ADVENTURE);
		p.teleport(LOBBY_LOCATION, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}
	public static void teleportToOverworld(Player p) {
		teleportTo(p, Type.OVERWORLD);
	}
	public static void teleportTo(Player p, Type t) {
		if (t == Type.LOBBY) {
			teleportToLobby(p);
			return;
		}
		
		if (p.getGameMode() != GameMode.CREATIVE) p.setGameMode(GameMode.SURVIVAL);
		p.teleport(getCorrespondingWorldManager(t).get().getSpawnLocation());
	}
	
	public static void regenerateAll(boolean generateStructures, Runnable onFinish) {
		OVERWORLD.regenerate(WorldType.NORMAL, generateStructures, () ->
				NETHER.regenerate(WorldType.NORMAL, true, () ->
						END.regenerate(WorldType.NORMAL, true, onFinish)));
	}
	
	public static void init(boolean clear) {
		OVERWORLD = new WorldManager("world");
		NETHER    = new WorldManager("world_nether");
		END       = new WorldManager("world_the_end");
		LOBBY     = new LobbyManager();
		
		World lobby = LOBBY.get();
		lobby.setPVP(false);
		lobby.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		lobby.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
		lobby.setDifficulty(Difficulty.PEACEFUL);
		lobby.setSpawnLocation(LOBBY_LOCATION);
		
		Bukkit.getPluginManager().registerEvents(new WorldEvents(), Main.PLUGIN);
		
		if (clear) {
			OVERWORLD.clearUselessWorlds();
			NETHER.clearUselessWorlds();
			END.clearUselessWorlds();
		}
	}
	
	private static void regenerate(Type type, boolean generateStructures) {
		getCorrespondingWorldManager(type).regenerate(generateStructures);
	}
	
	public static WorldManager getCorrespondingWorldManager(World.Environment environment) {
		return getCorrespondingWorldManager(Objects.requireNonNull(Type.fromEnvironment(environment)));
	}
	public static WorldManager getCorrespondingWorldManager(@Nullable Type type) {
		switch (type) {
			case OVERWORLD:
				return OVERWORLD;
			case NETHER:
				return NETHER;
			case END:
				return END;
			default:
				return LOBBY;
		}
	}
	
	public enum Type
	{
		LOBBY,
		OVERWORLD,
		NETHER,
		END;
		
		public static Type fromString(String s) {
			switch (s.toLowerCase()) {
				case "lobby":
					return LOBBY;
				case "overworld":
					return OVERWORLD;
				case "nether":
					return NETHER;
				case "end":
					return END;
			}
			return null;
		}
		public static Type fromEnvironment(World.Environment environment) {
			switch (environment) {
				case NORMAL:
					return OVERWORLD;
				case NETHER:
					return NETHER;
				case THE_END:
					return END;
				default:
					return null;
			}
		}
	}
}

