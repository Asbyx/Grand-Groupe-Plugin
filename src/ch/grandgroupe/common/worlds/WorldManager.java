package ch.grandgroupe.common.worlds;

import ch.grandgroupe.common.Main;
import org.bukkit.*;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class WorldManager
{
	private final String name;
	private int count = 0;
	private World world;
	
	public WorldManager(String name) {
		this.name = name;
		
		if (!worldExists(toString()) && hasSpecialGeneration()) {
			generate();
			return;
		}
		
		while (worldExists(toString())) ++count;
		--count;
		loadWorld(toString());
		world = Bukkit.getWorld(toString());
	}
	
	private static void deleteWorld(String name) {
		try {
			if (worldExists(name)) FileUtils.deleteDirectory(new File(Objects.requireNonNull(Bukkit.getWorldContainer().list((f, n) -> f.isDirectory() && n.equals(name)))[0]));
			Bukkit.getLogger().info("World " + name + " deleted");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void loadWorld(String name) {
		if (worldExists(name))
			Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
	}
	private static boolean worldExists(String name) {
		return Objects.requireNonNull(Bukkit.getWorldContainer().list((f, n) -> f.isDirectory() && n.equals(name))).length == 1;
	}
	
	public void clearUselessWorlds() {
		if (count == 0) return;
		
		Bukkit.getLogger().info(name + ": count=" + count);
		
		for (int i = 0; i < count; ++i)
			 deleteWorld(name + (i == 0 ? "" : "_" + Integer.toHexString(i)));
		
		File f = world.getWorldFolder();
		Bukkit.unloadWorld(world, false);
		
		if (!f.renameTo(new File(name))) throw new Error("Could not rename world folder");
		
		loadWorld(name);
		
		//deleteWorld(toString());
		count = 0;
	}
	
	protected boolean hasSpecialGeneration() {
		return false;
	}
	protected void generate() {
		throw new UnsupportedOperationException();
	}
	protected boolean cannotBeRegenerated() {
		return false;
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
		if (cannotBeRegenerated()) throw new UnsupportedOperationException();
		
		++count;
		
		File serverPacks = new File("datapacks"), worldPacks = new File(this + "/datapacks");
		
		try {
			FileUtils.copyDirectory(serverPacks, worldPacks);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		if (world != null) {
			World oldWorld = world;
			
			List<Player> players = oldWorld.getPlayers();
			players.forEach(Worlds::teleportToLobby);
			//Bukkit.unloadWorld(oldWorld, true);
			
			Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN, () ->
			{
				set(new WorldCreator(toString())
						.environment(world.getEnvironment())
						.type(type)
						.generateStructures(structures)
						.createWorld());
				
				players.forEach(p -> p.teleport(world.getSpawnLocation()));
				Main.SCHEDULER.runTaskLater(Main.PLUGIN, onFinish, 0);
			}, 50);
		}
		else
			Main.SCHEDULER.runTask(Main.PLUGIN, () ->
			{
				set(new WorldCreator(toString())
						.environment(world == null ? World.Environment.NORMAL : world.getEnvironment())
						.type(type)
						.generateStructures(structures)
						.createWorld());
				onFinish.run();
			});
		
	}
	
	protected void set(World world) {
		this.world = world;
	}
	
	@Override
	public String toString() {
		return name + (count == 0 ? "" : "_" + Integer.toHexString(count));
	}
	public World get() {
		return world;
	}
}
