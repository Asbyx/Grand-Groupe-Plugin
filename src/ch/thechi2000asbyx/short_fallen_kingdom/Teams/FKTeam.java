package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class FKTeam
{
	private final static double BASE_SIDE_LENGTH = 15;
	
	public final static List<FKTeam> allTeams = new ArrayList<>();
	
	private List<UUID> players;
	private final String name;
	private boolean isEliminated;
	private Coordinates baseCenter;
	private Coordinates flagLocation;
	
	private FileConfiguration config;
	
	private FKTeam(String name/*, ChatColor color*/)
	{
		this.name = name;
		
		try
		{
			config = new YamlConfiguration();
			
			try
			{
				config.load("teams.yml");
			}
			catch (InvalidConfigurationException e)
			{
				e.printStackTrace();
			}
			
			isEliminated = config.getBoolean(name + ".isEliminated");
			baseCenter   = (Coordinates) config.get(name + ".base-center");
			flagLocation = (Coordinates) config.get(name + ".flag-location");
			players      = (List<UUID>) config.getList(name + ".players");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		players      = players == null ? new ArrayList<>() : players;
		baseCenter   = baseCenter == null ? new Coordinates(0, 0, 0) : baseCenter;
		flagLocation = flagLocation == null ? new Coordinates(0, 0, 0) : flagLocation;
		
		allTeams.add(this);
	}
	
	private void removeOldFlag()
	{
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		
		int x = flagLocation.x,
				y = flagLocation.y,
				z = flagLocation.z;
		
		world.getBlockAt(x, y, z).setType(Material.AIR);
		world.getBlockAt(x, y + 1, z).setType(Material.AIR);
	}
	
	public boolean isFlagDestroyed()
	{
		if (flagLocation == null) return false;
		
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		
		int x = flagLocation.x,
				y = flagLocation.y,
				z = flagLocation.z;
		
		return world.getBlockAt(x, y, z).getType() == Material.AIR
				|| world.getBlockAt(x, y + 1, z).getType() == Material.AIR;
	}
	
	public boolean setFlagLocation(Location flagLocation)
	{
		if (!isInBase(flagLocation, true)) return false;
		
		if (this.flagLocation != null) removeOldFlag();
		
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		this.flagLocation = new Coordinates(flagLocation);
		
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
		baseCenter = new Coordinates(base);
	}
	
	public Coordinates getBaseCenter()
	{
		return baseCenter;
	}
	
	public boolean isInBase(Location location)
	{
		return isInBase(location, false);
	}
	public boolean isInBase(Location location, boolean checkY)
	{
		int dx = location.getBlockX() - baseCenter.x,
				dy = location.getBlockY() - baseCenter.y,
				dz = location.getBlockZ() - baseCenter.z;
		
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
