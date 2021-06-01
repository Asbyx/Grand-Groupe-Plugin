package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.Main;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class WorldManager
{
	private World world;
	private final String name;
	private int count = 0;
	
	public WorldManager(String name) {
		this.name = name;
		
		Bukkit.getLogger().info("Starting load of " + name);
		
		while (worldExists(getCurrentName())) ++count;
		--count;
		loadWorld(getCurrentName());
		world = Bukkit.getWorld(getCurrentName());
		
		Bukkit.getLogger().info("Finished load of " + name + ". Found " + (count + 1) + " worlds");
	}
	
	public void regenerate() {
		regenerate(true);
	}
	public void regenerate(boolean structures) {
		regenerate(WorldType.NORMAL, structures);
	}
	public void regenerate(WorldType type, boolean structures) {
		regenerate(type, structures, () -> {});
	}
	public void regenerate(WorldType type, boolean structures, Runnable onFinish) {
		++count;
		
		if (world != null) {
			World oldWorld = world;
			
			List<Player> players = oldWorld.getPlayers();
			Bukkit.getLogger().info("Player count: " + players.size());
			players.forEach(Worlds::teleportToLobby);
			//Bukkit.unloadWorld(oldWorld, true);
			
			Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN, () ->
			{
				set(new WorldCreator(getCurrentName())
						.environment(world.getEnvironment())
						.type(type)
						.generateStructures(structures)
						.createWorld());
				
				players.forEach(p -> p.teleport(world.getSpawnLocation()));
				onFinish.run();
			}, 50);
		}
		else
			Main.SCHEDULER.runTask(Main.PLUGIN, () ->
			{
				set(new WorldCreator(getCurrentName())
						.environment(world == null ? World.Environment.NORMAL : world.getEnvironment())
						.type(type)
						.generateStructures(structures)
						.createWorld());
				onFinish.run();
			});
		
	}
	
	private void set(World world) {
		this.world = world;
	}
	private String getCurrentName() {
		String s = name + (count == 0 ? "" : "_" + Integer.toHexString(count));
		Bukkit.getLogger().info(s);
		return s;
	}
	
	private static void deleteWorld(World world) {
		try {
			FileUtils.deleteDirectory(world.getWorldFolder());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static boolean loadWorld(String name) {
		if (worldExists(name))
			return Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
		
		return false;
	}
	private static boolean worldExists(String name) {
		return Objects.requireNonNull(Bukkit.getWorldContainer().list((f, n) -> f.isDirectory() && n.equals(name))).length == 1;
	}
	
	public World get() {
		return world;
	}
}
