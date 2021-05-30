package ch.grandgroupe.minigames.coop_defense.creatures;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.listeners.AbstractListener;
import ch.grandgroupe.common.utils.Coordinates;
import net.minecraft.server.v1_16_R3.Vec3D;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.*;

public class EventsManager extends AbstractListener
{
	int totalWaves;
	long timeBetweenWaves;
	long startTime;
	CraftWorld overworld;
	Random random;
	
	public EventsManager(long timeBetweenWaves) {
		this.timeBetweenWaves = timeBetweenWaves;
		totalWaves            = 0;
		overworld             = (CraftWorld) Objects.requireNonNull(Bukkit.getWorld("world"));
		startTime             = overworld.getGameTime();
		random                = new Random();
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set day");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doWeatherCycle true");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDayLightCycle true");
		
		Main.SCHEDULER.scheduleSyncRepeatingTask(Main.PLUGIN, this::update, 0, 1);
		Bukkit.getPluginManager().registerEvents(this, Main.PLUGIN);
	}
	
	public void update() {
		if (overworld.getTime() <= 12000) return;
		if ((overworld.getTime() - 12000) % timeBetweenWaves == 0) startWave();
	}
	
	private void startWave() {
		
		
		++totalWaves;
	}
	
	@EventHandler
	public void onCreatureDeath(EntityDeathEvent event) {
		if (CreatureToDefend.equal(event.getEntity())) {
			CreatureToDefend.CREATURE_TO_DEFEND.stop();
			Main.broadcast("You lost");
		}
	}
	
	@EventHandler
	public void onCreatureHeal(EntityTransformEvent event) {
		if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED && CreatureToDefend.equal(event.getEntity()))
			Main.broadcast("You won");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		BossBar bar = CreatureToDefend.CREATURE_TO_DEFEND.getBar();
		if (bar.getPlayers().contains(event.getPlayer())) bar.addPlayer(event.getPlayer());
	}
}