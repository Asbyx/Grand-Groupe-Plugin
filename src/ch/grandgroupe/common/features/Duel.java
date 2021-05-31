package ch.grandgroupe.common.features;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.utils.Timer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Duel extends AbstractListener
{
	Player player1, player2;
	DuelScoreboard player1DuelScoreboard, player2DuelScoreboard;
	
	@Override
	public void enable() {
		if (Bukkit.getOnlinePlayers().size() != 2) return;
		
		super.enable();
		
		Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
		player1 = players[0];
		player2 = players[1];
		
		player1DuelScoreboard = new DuelScoreboard(player1);
		player2DuelScoreboard = new DuelScoreboard(player2);
	}
	
	@EventHandler
	public void updateCompasses(PlayerMoveEvent event) {
		if (isDisabled()) return;
		
		player1.setCompassTarget(player2.getLocation());
		player2.setCompassTarget(player1.getLocation());
	}
	
	@EventHandler
	public void checkVictory(PlayerDeathEvent event) {
		if (event.getEntity() == player1 && player2.getStatistic(Statistic.PLAYER_KILLS) >= 1)
			endGame(player2);
		else if (event.getEntity() == player2 && player1.getStatistic(Statistic.PLAYER_KILLS) >= 1)
			endGame(player1);
	}
	
	private void endGame(Player winner) {
		Main.broadcast(ChatColor.GREEN + winner.getName() + " won !");
		Main.broadcast(ChatColor.GREEN + "GG to everyone");
		
		player1DuelScoreboard.stop();
		player2DuelScoreboard.stop();
	}
	
	private static class DuelScoreboard
	{
		private final Player player;
		private final Scoreboard scoreboard;
		private final Objective objective;
		private final int taskId;
		private final Timer timer;
		private final List<String> scores;
		
		public DuelScoreboard(@NotNull Player player) {
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
			scores.add("Deaths: " + player.getStatistic(Statistic.DEATHS));
			
			AtomicInteger i = new AtomicInteger();
			scores.forEach(s -> objective.getScore(s).setScore(i.getAndIncrement()));
		}
		
		void stop() {
			Main.SCHEDULER.cancelTask(taskId);
		}
	}
}
