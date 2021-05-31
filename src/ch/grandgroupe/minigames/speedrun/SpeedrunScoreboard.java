package ch.grandgroupe.minigames.speedrun;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.utils.Timer;
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
	private final int taskId;
	private final Timer timer;
	private final List<String> scores;
	
	public SpeedrunScoreboard(Player player) {
		this.player = player;
		scoreboard  = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
		objective   = scoreboard.registerNewObjective(player.getName(), "dummy", "Duel");
		taskId      = Main.SCHEDULER.scheduleSyncRepeatingTask(Main.PLUGIN, this::update, 0, 20);
		timer       = new Timer(Objects.requireNonNull(Bukkit.getWorld("world")));
		scores      = new ArrayList<>();
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(scoreboard);
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
	}
}
