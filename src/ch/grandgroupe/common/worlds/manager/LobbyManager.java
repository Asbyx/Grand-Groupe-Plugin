package ch.grandgroupe.common.worlds.manager;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.worlds.WorldManager;
import org.bukkit.*;

public class LobbyManager extends WorldManager
{
	public LobbyManager() {
		super("world_lobby");
	}
	
	@Override
	protected boolean hasSpecialGeneration() {
		return true;
	}
	@Override
	protected boolean canBeRegenerated() {
		return false;
	}
	@Override
	protected void generate() {
		Main.SCHEDULER.runTask(Main.PLUGIN, () -> set(new WorldCreator("world_lobby")
				.generateStructures(false)
				.type(WorldType.FLAT)
				.generatorSettings("{\"layers\":[{\"block\":\"grass\", \"height\":64}]}")
				.createWorld()));
	}
}
