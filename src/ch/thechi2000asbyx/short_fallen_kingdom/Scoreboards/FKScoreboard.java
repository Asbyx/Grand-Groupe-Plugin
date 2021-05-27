package ch.thechi2000asbyx.short_fallen_kingdom.Scoreboards;

import ch.thechi2000asbyx.common.listeners.AbstractListener;
import ch.thechi2000asbyx.short_fallen_kingdom.Events.EventsManager;
import ch.thechi2000asbyx.common.Main;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.FKTeam;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FKScoreboard extends AbstractListener
{
	private static int count = 0;
	
	private final FKTeam ownerTeam;
	private final Player owner;
	private EventsManager eventsManager;
	private final Objective objective;
	private final Scoreboard scoreboard;
	private int id = -1;
	
	private final List<String> scores;
	
	/**
	 * Create a Scoreboard for a given Player, using the values from the EventsManager
	 *
	 * @param owner         the Player who owns the scoreboard (i.e. it will be displayed on his screen and use his data)
	 * @param eventsManager the EventsManager containing the data to be displayed
	 */
	public FKScoreboard(Player owner, EventsManager eventsManager) {
		this.owner         = owner;
		this.eventsManager = eventsManager;
		scores             = new ArrayList<>();
		
		scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
		objective  = scoreboard.registerNewObjective("side_bar_" + count, "dummy", "Fallen Kingdom - CTF");
		ownerTeam  = FKTeam.getTeam(owner);
		++count;
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		owner.setScoreboard(scoreboard);
		
		Bukkit.getPluginManager().registerEvents(this, Main.PLUGIN);
		
		enable();
	}
	
	/**
	 * Display the Scoreboard and start updating its data every 5 ticks
	 */
	public void start() {
		id = Main.SCHEDULER.scheduleSyncRepeatingTask(Main.PLUGIN, this::update, 0, 5);
	}
	
	/**
	 * Hide the Scoreboard and stop updating it
	 */
	public void stop() {
		objective.setDisplaySlot(null);
		Main.SCHEDULER.cancelTask(id);
		eventsManager = null;
	}
	
	private void update() {
		if (isDisabled()) return;
		
		scores.forEach(scoreboard::resetScores);
		scores.clear();
		
		scores.add("Kills: " + owner.getStatistic(Statistic.PLAYER_KILLS));
		
		scores.add(ChatColor.DARK_GREEN + "-=-=-=-=-=-=-=-=-");
		scores.add("Random chest: " + ticksToMinSec(eventsManager.getMaxTimerForRandomChest()));
		scores.add("Blood night: " + eventsManager.getTimerOfBloodNight());
		scores.add("Middle chest: " + ticksToMinSec(eventsManager.getTimerOfMiddleChest()));
		scores.add("Pvp " + (eventsManager.getPvpAllowed() ? "enabled" : "disabled"));
		scores.add("Events");
		scores.add(ChatColor.DARK_GREEN + "=-=-=-=-=-=-=-=-=");
		scores.add("Day: " + eventsManager.getTotalDays());
		
		AtomicInteger i = new AtomicInteger();
		scores.forEach(s -> objective.getScore(s).setScore(i.getAndIncrement()));
	}
	
	@EventHandler
	public void stop(PlayerQuitEvent event) {
		if (event.getPlayer() == owner) stop();
	}
	
	@EventHandler
	public void start(PlayerJoinEvent event) {
		if (eventsManager != null && event.getPlayer() == owner) start();
	}
	
	private String ticksToMinSec(long ticks) {
		long secs = (ticks / 20) % 60,
				min = ticks / 1200;
		
		if (min == 0)
			return String.format("%ds", secs);
		else
			return String.format("%dm %ds", secs, min);
	}
}
