package ch.thechi2000.distances_scoreboard;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class DistanceBoard
{
	private static int count = 0;
	
	private final Objective objective;
	private final Player owner;
	
	public DistanceBoard(Player player)
	{
		this.owner = player;
		
		Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();
		objective = scoreboard.registerNewObjective("distance_board-" + count, "dummy", "Distances");
		
		objective.getScore("Spawn").setScore(Coordinates.distanceBetween(owner.getWorld().getSpawnLocation(), owner.getLocation()));
		
		Bukkit.getOnlinePlayers().forEach(p -> objective.getScore(p.getName()).setScore(Coordinates.distanceBetween(p, player)));
		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		player.setScoreboard(scoreboard);
		
		++count;
	}
	
	public void addPlayer(Player player)
	{
		objective.getScore(player.getName()).setScore(Coordinates.distanceBetween(owner, player));
	}
	
	public void removePlayer(Player player)
	{
		objective.getScore(player.getName()).setScore(-1);
	}
	
	public void updatePlayer(Player player)
	{
		if (player.getUniqueId().equals(owner.getUniqueId()))
		{
			Bukkit.getOnlinePlayers().forEach(p -> objective.getScore(p.getName()).setScore(Coordinates.distanceBetween(p, player)));
			objective.getScore("Spawn").setScore(Coordinates.distanceBetween(owner.getWorld().getSpawnLocation(), owner.getLocation()));
		}
		else
			objective.getScore(player.getName()).setScore(Coordinates.distanceBetween(owner, player));
	}
	
	public void enable()
	{
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	public void disable()
	{
		objective.setDisplaySlot(null);
	}
}
