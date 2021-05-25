package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.common.Coordinates;
import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class FKTeam
{
	enum FlagPlacementProblems
	{
		NONE,
		OUTSIDE_BASE,
		INVALID_HEIGHT,
		INVALID_WORLD,
		BLOCKING_BLOCKS,
		TEAM_ELIMINATED
	}
	
	private final static double BASE_SIDE_LENGTH = 15;
	
	public final static List<FKTeam> allTeams = new ArrayList<>();
	private final static Map<UUID, FKTeam> playerTeams = new HashMap<>();
	
	private final List<UUID> players;
	private final String name;
	private boolean isEliminated;
	private Coordinates baseCenter;
	
	
	private Coordinates flagLocation;
	
	private static final File configFile = new File(Main.PLUGIN.getDataFolder().getAbsolutePath() + "/teams.yml");
	private static final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	
	private FKTeam(String name/*, ChatColor color*/)
	{
		this.name = name;
		
		try
		{
			config.load(Main.PLUGIN.getDataFolder().getAbsolutePath() + "/teams.yml");
		}
		catch (InvalidConfigurationException | IOException e)
		{
			e.printStackTrace();
		}
		
		List<String> names = config.getStringList("names");
		if (config.getStringList("names").contains(name))
		{
			isEliminated = config.getBoolean(name + ".isEliminated");
			baseCenter   = Coordinates.fromString(config.getString(name + ".base-center"));
			flagLocation = Coordinates.fromString(config.getString(name + ".flag-location"));
			players      = config.getStringList(name + ".players").stream().map(UUID::fromString).collect(Collectors.toList());
			
			players.forEach(p -> playerTeams.put(p, this));
		}
		else
		{
			names.add(name);
			config.set("names", names);
			
			isEliminated = false;
			baseCenter   = new Coordinates(0, 0, 0);
			flagLocation = new Coordinates(0, 0, 0);
			players      = new ArrayList<>();
			
			save();
		}
		
		allTeams.add(this);
	}
	
	private void save()
	{
		config.set(name + ".isEliminated", isEliminated);
		config.set(name + ".base-center", baseCenter.toString());
		config.set(name + ".flag-location", flagLocation.toString());
		config.set(name + ".players", players.stream().map(UUID::toString).collect(Collectors.toList()));
		
		try
		{
			config.save(configFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Coordinates getFlagLocation()
	{
		return flagLocation;
	}
	public boolean isFlagDestroyed()
	{
		if (flagLocation == null) return false;
		
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		
		int x = flagLocation.x,
				y = flagLocation.y,
				z = flagLocation.z;
		
		return world.getBlockAt(x, y, z).getType() != Material.OAK_FENCE
				|| world.getBlockAt(x, y + 1, z).getType() != Material.BLACK_WOOL;
	}
	public FlagPlacementProblems setFlagLocation(Location flagLocation)
	{
		if (isEliminated) return FlagPlacementProblems.TEAM_ELIMINATED;
		if (flagLocation.getWorld().getEnvironment() != World.Environment.NORMAL) return FlagPlacementProblems.INVALID_WORLD;
		if (!isInBase(flagLocation)) return FlagPlacementProblems.OUTSIDE_BASE;
		if (!checkHeight(flagLocation.getBlockY())) return FlagPlacementProblems.INVALID_HEIGHT;
		
		if (this.flagLocation != null) removeOldFlag();
		
		
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		this.flagLocation = new Coordinates(flagLocation);
		
		int x = flagLocation.getBlockX(),
				y = flagLocation.getBlockY(),
				z = flagLocation.getBlockZ();
		
		if (world.getBlockAt(x, y, z).getType() != Material.AIR
				|| world.getBlockAt(x, y + 1, z).getType() != Material.AIR) return FlagPlacementProblems.BLOCKING_BLOCKS;
		
		world.getBlockAt(x, y, z).setType(Material.OAK_FENCE);
		world.getBlockAt(x, y + 1, z).setType(Material.BLACK_WOOL);
		
		save();
		
		return FlagPlacementProblems.NONE;
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
	
	public boolean addPlayer(Player player)
	{
		if (FKTeam.getTeam(player) != null)
			return false;
		
		players.add(player.getUniqueId());
		playerTeams.put(player.getUniqueId(), this);
		save();
		return true;
	}
	public void removePlayer(Player player)
	{
		players.remove(player.getUniqueId());
		save();
	}
	public boolean contains(Player player)
	{
		return players.contains(player.getUniqueId());
	}
	public Stream<Player> stream()
	{
		return players.stream().map(uuid -> Bukkit.getOnlinePlayers()
												  .stream()
												  .filter(player -> player.getUniqueId().equals(uuid))
												  .findAny().orElse(null));
	}
	
	public void eliminate()
	{
		isEliminated = true;
		save();
	}
	public void revive()
	{
		isEliminated = false;
		save();
	}
	public boolean isEliminated()
	{
		return isEliminated;
	}
	
	public void setBaseCenter(Location base)
	{
		baseCenter = new Coordinates(base);
		setFlagLocation(base);
		save();
	}
	public Coordinates getBaseCenter()
	{
		return baseCenter;
	}
	
	public boolean isInBase(Location location)
	{
		int dx = location.getBlockX() - baseCenter.x,
				dz = location.getBlockZ() - baseCenter.z;
		
		return Math.abs(dx) <= BASE_SIDE_LENGTH / 2
				&& Math.abs(dz) <= BASE_SIDE_LENGTH / 2;
	}
	public boolean checkHeight(int height)
	{
		return Math.abs(baseCenter.y - height) <= 20;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static FKTeam registerNewTeam(String name)
	{
		if (FKTeam.getTeam(name) == null)
			return new FKTeam(name);
		else
			return null;
	}
	
	public static FKTeam getTeam(Player player)
	{
		return playerTeams.get(player.getUniqueId());
	}
	public static FKTeam getTeam(String name)
	{
		return allTeams.stream().filter(t -> t.getName().equals(name)).findAny().orElse(null);
	}
	
	public static boolean makeOrphan(Player player)
	{
		FKTeam team = FKTeam.getTeam(player);
		
		if (team == null) return false;
		
		team.removePlayer(player);
		playerTeams.remove(player.getUniqueId());
		return true;
	}
	
	public static void loadTeamsFromConfig()
	{
		List<String> names = config.getStringList("names");
		names.forEach(FKTeam::registerNewTeam);
	}
}
