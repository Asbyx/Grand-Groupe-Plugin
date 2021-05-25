package ch.thechi2000asbyx.short_fallen_kingdom.Scoreboards;

import ch.thechi2000asbyx.common.Coordinates;
import ch.thechi2000asbyx.short_fallen_kingdom.Events.EventsManager;
import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.FKTeam;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scoreboard.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FKScoreboard implements Listener
{
	private static int count = 0;
	
	private final FKTeam ownerTeam;
	private final Player owner;
	private EventsManager eventsManager;
	private final Objective objective;
	private final Scoreboard scoreboard;
	private int id = -1;
	
	private final List<String> scores;
	
	public FKScoreboard(Player owner, EventsManager eventsManager)
	{
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
		
		start();
	}
	
	public void update()
	{
		scores.forEach(scoreboard::resetScores);
		scores.clear();
		
		scores.add("Kills: " + owner.getStatistic(Statistic.PLAYER_KILLS));
		
		if (ownerTeam != null)
		{
			if (ownerTeam.hasOnlinePlayer())
				ownerTeam.stream()
						 .filter(p -> p != owner)
						 .forEach(p -> scores.add(p.getName() + ": "));
			scores.add("Distance to base: " + ownerTeam.getBaseCenter().distanceTo(new Coordinates(owner.getLocation())));
		}
		
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
	
	private void start()
	{
		id = Main.SCHEDULER.scheduleSyncRepeatingTask(Main.PLUGIN, this::update, 0, 5);
	}
	
	public void stop()
	{
		Main.SCHEDULER.cancelTask(id);
		eventsManager = null;
	}
	
	@EventHandler
	public void stop(PlayerQuitEvent event)
	{
		if (event.getPlayer() == owner) stop();
	}
	
	@EventHandler
	public void start(PlayerJoinEvent event)
	{
		if (eventsManager != null && event.getPlayer() == owner) start();
	}
	
	private String ticksToMinSec(long ticks)
	{
		long secs = (ticks / 20) % 60,
				min = ticks / 1200;
		
		if (min == 0)
			return String.format("%ds", secs);
		else
			return String.format("%dm %ds", secs, min);
	}
}
