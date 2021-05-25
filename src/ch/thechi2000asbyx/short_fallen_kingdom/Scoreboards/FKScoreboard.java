package ch.thechi2000asbyx.short_fallen_kingdom.Scoreboards;

import ch.thechi2000asbyx.common.Coordinates;
import ch.thechi2000asbyx.short_fallen_kingdom.Events.EventsManager;
import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.FKTeam;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FKScoreboard implements Listener
{
	private static int count = 0;
	
	private final FKTeam ownerTeam;
	private final Player owner;
	private final EventsManager eventsManager;
	private final Objective objective;
	private int id = -1;
	
	public FKScoreboard(Player owner, EventsManager eventsManager)
	{
		this.owner         = owner;
		this.eventsManager = eventsManager;
		
		Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
		objective = scoreboard.registerNewObjective("side_bar_" + count, "dummy", "Fallen Kingdom - CTF");
		ownerTeam = FKTeam.getTeam(owner);
		++count;
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		owner.setScoreboard(scoreboard);
		
		start();
	}
	
	public void update()
	{
		Objects.requireNonNull(objective.getScoreboard()).resetScores(objective.getName());
		
		AtomicInteger i = new AtomicInteger();
		
		objective.getScore("Kills: " + owner.getStatistic(Statistic.PLAYER_KILLS)).setScore(i.get());
		
		if (ownerTeam != null)
		{
			ownerTeam.stream().filter(p -> p != owner).forEach(p -> objective.getScore(p.getName() + ": ").setScore(i.getAndIncrement()));
			objective.getScore("Distance to base: " + ownerTeam.getBaseCenter().distanceTo(new Coordinates(owner.getLocation()))).setScore(i.getAndIncrement());
		}
		
		objective.getScore(ChatColor.DARK_GREEN + "-=-=-=-=-=-=-=-=-").setScore(i.getAndIncrement());
		objective.getScore("Random chest: " + eventsManager.getMaxTimerForRandomChest()).setScore(i.getAndIncrement());
		objective.getScore("Blood night: " + eventsManager.getTimerOfBloodNight()).setScore(i.getAndIncrement());
		objective.getScore("Middle chest: " + eventsManager.getTimerOfMiddleChest()).setScore(i.getAndIncrement());
		objective.getScore("Pvp " + (eventsManager.getPvpAllowed() ? "enabled" : "disabled")).setScore(i.getAndIncrement());
		objective.getScore("Events").setScore(i.getAndIncrement());
		objective.getScore(ChatColor.DARK_GREEN + "=-=-=-=-=-=-=-=-=").setScore(i.getAndIncrement());
		objective.getScore("Day: " + eventsManager.getTotalDays()).setScore(i.getAndIncrement());
	}
	
	private void start()
	{
		id = Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN, this::update, 20);
	}
	
	public void stop()
	{
		Main.SCHEDULER.cancelTask(id);
	}
}
