package ch.grandgroupe.minigames.coop_defense;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.utils.Coordinates;
import ch.grandgroupe.minigames.coop_defense.creatures.*;
import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.*;

public class Wave
{
	private final BossBar bar;
	private final CraftWorld overworld;
	public static final int MIN_RADIUS = 10;
	public static final int MAX_RADIUS = 20;
	
	public Wave(int waveDifficulty) {
		overworld = Objects.requireNonNull((CraftWorld) Bukkit.getWorld("world"));
		
		for (int i = 0; i < 2 + waveDifficulty; ++i) {
			Location loc = randomWaveSpawnLocation();
			Main.broadcast(new Coordinates(loc).toString());
			
			overworld.addEntity(new AttackZombie(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new AttackZombie(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new AttackZombie(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new DefendZombie(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new DefendZombie(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
		}
		
		for (int i = 0; i < waveDifficulty; ++i) {
			Location loc = randomWaveSpawnLocation();
			Main.broadcast(new Coordinates(loc).toString());
			
			overworld.addEntity(new AttackSkeleton(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new AttackSkeleton(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new AttackSkeleton(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new DefendSkeleton(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
			overworld.addEntity(new DefendSkeleton(loc), CreatureSpawnEvent.SpawnReason.CUSTOM);
		}
		
		bar = Bukkit.createBossBar("Wave " + waveDifficulty, BarColor.RED, BarStyle.SOLID);
	}
	
	private Location randomWaveSpawnLocation() {
		Vec3D creature = CreatureToDefend.CREATURE_TO_DEFEND.getPositionVector();
		Vec3D vec;
		
		Random random = new Random();
		do {
			vec = new Vec3D(random.nextInt(2 * MAX_RADIUS) - MAX_RADIUS, 0, random.nextInt(2 * MAX_RADIUS) - MAX_RADIUS);
		}
		while (vec.f() < MIN_RADIUS);
		
		Location loc = new Location(overworld, vec.x + creature.x, 0, vec.z + creature.z);
		
		loc.setY(overworld.getHighestBlockYAt((int) loc.getX(), (int) loc.getZ()));
		
		return loc;
	}
}
