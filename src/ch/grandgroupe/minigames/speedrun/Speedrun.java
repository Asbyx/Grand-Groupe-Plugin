package ch.grandgroupe.minigames.speedrun;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.features.AbstractListener;
import ch.grandgroupe.common.worlds.Worlds;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.*;
import java.util.stream.*;

public class Speedrun extends AbstractListener
{
	private final Objective objective;
	private final List<SpeedrunScoreboard> scoreboards;
	private final List<UUID> playersId;
	private long startTime;
	
	public Speedrun(Objective objective) {
		enable();
		
		this.objective = objective;
		Bukkit.getPluginManager().registerEvents(this, Main.PLUGIN);
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules tomb true");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules compassTargeting true");
		
		playersId   = Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).collect(Collectors.toList());
		scoreboards = onlinePlayers().map(SpeedrunScoreboard::new).collect(Collectors.toList());
		Main.inGamePlayers.addAll(playersId);
		
		onlinePlayers().filter(p -> p.getWorld() != Worlds.LOBBY.get()).forEach(Worlds::teleportToLobby);
		Worlds.regenerateAll(true, () ->
		{
			onlinePlayers().forEach(Worlds::teleportToOverworld);
			onlinePlayers().forEach(this::initPlayer);
			scoreboards.forEach(SpeedrunScoreboard::start);
			
			World ow = Worlds.OVERWORLD.get();
			ow.setDifficulty(Difficulty.NORMAL);
			ow.setPVP(true);
			ow.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
			ow.setGameRule(GameRule.DO_WEATHER_CYCLE, true);
			ow.setGameRule(GameRule.KEEP_INVENTORY, false);
			ow.setTime(0);
			
			startTime = ow.getGameTime();
			
			Main.broadcast("Speedrun started ! The first to " + objective.description + " wins");
			Main.broadcast("May the odds be with you !");
		});
	}
	
	public void stop() {
		Main.inGamePlayers.removeIf(Main.inGamePlayers::contains);
		scoreboards.forEach(SpeedrunScoreboard::stop);
		Main.broadcast(ChatColor.RED + "Speedrun stopped");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules tomb false");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rules compassTargeting false");
		disable();
	}
	
	public Stream<? extends Player> onlinePlayers() {
		return Bukkit.getOnlinePlayers().stream().filter(p -> playersId.contains(p.getUniqueId()));
	}
	
	@EventHandler
	public void checkVictory(PlayerDeathEvent event) {
		Player killer = event.getEntity().getKiller();
		if (killer == null) return;
		
		switch (objective.type) {
			case KILL:
				endGame(killer);
				break;
			
			case HOUR_KILL:
				if (Worlds.OVERWORLD.get().getGameTime() > 72000)
					endGame(killer);
				break;
			
			case POTION_KILL:
				if (killer.getActivePotionEffects().stream().anyMatch(e -> Objective.bonusEffects.contains(e.getType())) || event.getEntity().getActivePotionEffects().stream().anyMatch(e -> Objective.malusEffects.contains(e.getType())))
					endGame(killer);
				break;
			
			default:
				break;
		}
	}
	
	@EventHandler
	public void checkVictory(PlayerAdvancementDoneEvent event) {
		if (objective.type != Objective.Types.ACHIEVEMENT) return;
		
		if (event.getAdvancement().getKey().getKey().equals(objective.data[0]))
			endGame(event.getPlayer());
	}
	
	@EventHandler
	public void addPlayer(PlayerJoinEvent event) {
		if (isDisabled()) return;
		
		Player p = event.getPlayer();
		
		if (!playersId.contains(p.getUniqueId())) {
			initPlayer(p);
			playersId.add(p.getUniqueId());
			Main.inGamePlayers.add(p.getUniqueId());
			
			Worlds.teleportToOverworld(p);
		}
		
		SpeedrunScoreboard scoreboard = new SpeedrunScoreboard(p, startTime);
		scoreboards.add(scoreboard);
		scoreboard.start();
	}
	
	private void endGame(Player winner) {
		Main.inGamePlayers.removeIf(Main.inGamePlayers::contains);
		
		String winCommand = "title %s title {\"text\":\"You won\",\"color\":\"green\"}",
				looseCommand = "title %s title {\"text\":\"%s won\",\"color\":\"red\",\"bold\":true}";
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format(winCommand, winner.getName()));
		onlinePlayers().filter(p -> p != winner).forEach(p ->
		{
			String command = String.format(looseCommand, p.getName(), winner.getName());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		});
		
		scoreboards.forEach(SpeedrunScoreboard::stop);
	}
	
	private void initPlayer(Player p) {
		p.setHealth(20);
		p.setGameMode(GameMode.SURVIVAL);
		p.getInventory().clear();
		p.setFoodLevel(20);
		p.setTotalExperience(0);
		p.setLevel(0);
		p.setStatistic(Statistic.PLAYER_KILLS, 0);
		p.setStatistic(Statistic.DEATHS, 0);
		p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
		
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement revoke " + p.getName() + " everything");
	}
}
