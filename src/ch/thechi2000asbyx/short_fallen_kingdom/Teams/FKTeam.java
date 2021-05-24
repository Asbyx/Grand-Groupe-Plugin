package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.*;

public class FKTeam
{
	private final static double BASE_SIDE_LENGTH = 15;
	
	public final static List<FKTeam> allTeams = new ArrayList<>();
	
	private final List<UUID> players;
	private final String name;
	//private final Team bukkitTeam;
	private Location baseCenter;
	private Location flagLocation;
	private boolean isEliminated;
	
	private FKTeam(String name/*, ChatColor color*/)
	{
		this.name    = name;
		players      = new ArrayList<>();
		baseCenter   = new Location(null, 0, 0, 0);
		isEliminated = false;
		
		allTeams.add(this);
	}
	
	private void removeOldFlag()
	{
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		
		int x = flagLocation.getBlockX(),
				y = flagLocation.getBlockY(),
				z = flagLocation.getBlockZ();
		
		world.getBlockAt(x, y, z).setType(Material.AIR);
		world.getBlockAt(x, y + 1, z).setType(Material.AIR);
	}
	
	public boolean isFlagDestroyed()
	{
		if (flagLocation == null) return false;
		
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		
		int x = flagLocation.getBlockX(),
				y = flagLocation.getBlockY(),
				z = flagLocation.getBlockZ();
		
		return world.getBlockAt(x, y, z).getType() == Material.AIR
				|| world.getBlockAt(x, y + 1, z).getType() == Material.AIR;
	}
	
	public boolean setFlagLocation(Location flagLocation)
	{
		if (!isInBase(flagLocation, true)) return false;
		
		if (this.flagLocation != null) removeOldFlag();
		
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		this.flagLocation = flagLocation;
		
		int x = flagLocation.getBlockX(),
				y = flagLocation.getBlockY(),
				z = flagLocation.getBlockZ();
		
		world.getBlockAt(x, y, z).setType(Material.OAK_FENCE);
		world.getBlockAt(x, y + 1, z).setType(Material.BLACK_WOOL);
		
		return true;
	}
	
	public boolean contains(Player player)
	{
		return players.contains(player.getUniqueId());
	}
	
	public void addPlayer(Player player)
	{
		players.add(player.getUniqueId());
	}
	
	public void setBaseLocation(Location base)
	{
		baseCenter = base;
	}
	
	public Location getBaseCenter()
	{
		return baseCenter;
	}
	
	public boolean isInBase(Location location)
	{
		return isInBase(location, false);
	}
	public boolean isInBase(Location location, boolean checkY)
	{
		int dx = location.getBlockX() - baseCenter.getBlockX(),
				dy = location.getBlockY() - baseCenter.getBlockY(),
				dz = location.getBlockZ() - baseCenter.getBlockZ();
		
		return Math.abs(dx) <= BASE_SIDE_LENGTH / 2
				&& (!checkY || Math.abs(dy) <= 20)
				&& Math.abs(dz) <= BASE_SIDE_LENGTH / 2;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static FKTeam registerNewTeam(String name)
	{
		return new FKTeam(name);
	}
	
	public static FKTeam getTeam(Player player)
	{
		return allTeams.stream().filter(t -> t.contains(player)).findAny().orElse(null);
	}
	public static FKTeam getTeam(String name)
	{
		return allTeams.stream().filter(t -> t.getName().equals(name)).findAny().orElse(null);
	}
	
	public void eliminate()
	{
		isEliminated = true;
	}
	public void revive()
	{
		isEliminated = false;
	}
	public boolean isEliminated()
	{
		return isEliminated;
	}
}
