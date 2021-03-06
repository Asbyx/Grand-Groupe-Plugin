package ch.grandgroupe.minigames.speedrun;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.utils.Timer;
import ch.grandgroupe.common.worlds.Worlds;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class SpeedrunScoreboard
{
	private final Player player;
	private final Scoreboard scoreboard;
	private final org.bukkit.scoreboard.Objective objective;
	private final Timer timer;
	private final List<String> scores;
	private int taskId;
	
	public SpeedrunScoreboard(Player player) {
		this(player, Objects.requireNonNull(Worlds.OVERWORLD.get()).getGameTime());
	}
	public SpeedrunScoreboard(Player player, long startTime) {
		this.player = player;
		scoreboard  = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
		objective   = scoreboard.registerNewObjective(player.getName(), "dummy", "Duel");
		timer       = new Timer(startTime, Objects.requireNonNull(Worlds.OVERWORLD.get()));
		scores      = new ArrayList<>();
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(scoreboard);
	}
	
	public void start() {
		taskId = Main.SCHEDULER.scheduleSyncRepeatingTask(Main.PLUGIN, this::update, 0, 20);
	}
	
	void update() {
		scores.forEach(scoreboard::resetScores);
		scores.clear();
		
		scores.add("Time: " + timer.getElapsedTimeAsString());
		scores.add("Kills: " + player.getStatistic(Statistic.PLAYER_KILLS));
		scores.add("Deaths: " + player.getStatistic(Statistic.DEATHS));
		
		AtomicInteger i = new AtomicInteger();
		scores.forEach(s -> objective.getScore(s).setScore(i.getAndIncrement()));
	}
	
	void stop() {
		Main.SCHEDULER.cancelTask(taskId);
		objective.setDisplaySlot(null);
	}
}
