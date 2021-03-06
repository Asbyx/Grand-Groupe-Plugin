package ch.grandgroupe.minigames.short_fallen_kingdom.teams;

import ch.grandgroupe.common.utils.Coordinates;
import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.worlds.Worlds;
import org.bukkit.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
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
	private boolean isEliminated, isTerminated;
	private Coordinates baseCenter;
	
	private Coordinates flagLocation;
	
	private static final File configFile = new File(Main.PLUGIN.getDataFolder().getAbsolutePath() + "/teams.yml");
	private static final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
	
	private FKTeam(String name/*, ChatColor color*/) {
		this.name = name;
		
		try {
			config.load(Main.PLUGIN.getDataFolder().getAbsolutePath() + "/teams.yml");
		}
		catch (InvalidConfigurationException | IOException e) {
			e.printStackTrace();
		}
		
		List<String> names = config.getStringList("names");
		if (config.getStringList("names").contains(name)) {
			isEliminated = config.getBoolean(name + ".isEliminated");
			isTerminated = config.getBoolean(name + ".isTerminated");
			baseCenter   = Coordinates.fromString(config.getString(name + ".base-center"));
			flagLocation = Coordinates.fromString(config.getString(name + ".flag-location"));
			players      = config.getStringList(name + ".players").stream().map(UUID::fromString).collect(Collectors.toList());
			
			players.forEach(p -> playerTeams.put(p, this));
		}
		else {
			names.add(name);
			config.set("names", names);
			
			isEliminated = false;
			isTerminated = false;
			baseCenter   = new Coordinates(0, 0, 0);
			flagLocation = new Coordinates(0, 0, 0);
			players      = new ArrayList<>();
			
			save();
		}
		
		allTeams.add(this);
	}
	
	private void save() {
		config.set(name + ".isEliminated", isEliminated);
		config.set(name + ".isTerminated", isTerminated);
		config.set(name + ".base-center", baseCenter.toString());
		config.set(name + ".flag-location", flagLocation.toString());
		config.set(name + ".players", players.stream().map(UUID::toString).collect(Collectors.toList()));
		
		try {
			config.save(configFile);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the location of the team's flag
	 */
	public Coordinates getFlagLocation() {
		return flagLocation;
	}
	
	/**
	 * @return whether the flag is destroyed
	 */
	public boolean isFlagDestroyed() {
		if (flagLocation == null) return false;
		
		World world = Objects.requireNonNull(Worlds.OVERWORLD.get());
		
		int x = flagLocation.x,
				y = flagLocation.y,
				z = flagLocation.z;
		
		return world.getBlockAt(x, y, z).getType() != Material.OAK_FENCE
				|| world.getBlockAt(x, y + 1, z).getType() != Material.BLACK_WOOL;
	}
	
	/**
	 * Set a new location for the flag
	 *
	 * @param flagLocation the new location
	 *
	 * @return a value indicating whether a problem occurred, and if yes, which one it is
	 */
	public FlagPlacementProblems setFlagLocation(Location flagLocation) {
		World world = Objects.requireNonNull(Worlds.OVERWORLD.get());
		int x = flagLocation.getBlockX(),
				y = flagLocation.getBlockY(),
				z = flagLocation.getBlockZ();
		
		if (isEliminated()) return FlagPlacementProblems.TEAM_ELIMINATED;
		if (flagLocation.getWorld() == null || flagLocation.getWorld().getEnvironment() != World.Environment.NORMAL)
			return FlagPlacementProblems.INVALID_WORLD;
		if (!isInBase(flagLocation)) return FlagPlacementProblems.OUTSIDE_BASE;
		if (!checkFlagHeight(flagLocation.getBlockY())) return FlagPlacementProblems.INVALID_HEIGHT;
		
		if (world.getBlockAt(x, y, z).getType() != Material.AIR
				|| world.getBlockAt(x, y + 1, z).getType() != Material.AIR)
			return FlagPlacementProblems.BLOCKING_BLOCKS;
		
		if (this.flagLocation != null) removeOldFlag();
		
		this.flagLocation = new Coordinates(flagLocation);
		world.getBlockAt(x, y, z).setType(Material.OAK_FENCE);
		world.getBlockAt(x, y + 1, z).setType(Material.BLACK_WOOL);
		
		save();
		
		return FlagPlacementProblems.NONE;
	}
	
	private void removeOldFlag() {
		World world = Objects.requireNonNull(Worlds.OVERWORLD.get());
		
		int x = flagLocation.x,
				y = flagLocation.y,
				z = flagLocation.z;
		
		world.getBlockAt(x, y, z).setType(Material.AIR);
		world.getBlockAt(x, y + 1, z).setType(Material.AIR);
	}
	
	/**
	 * @return the total count of players in the team (including offline ones)
	 */
	public int playersCount() {
		return players.size();
	}
	
	/**
	 * @return the count of players of the team currently online;
	 */
	public int onlinePlayersCount() {
		return (int) stream().count();
	}
	
	/**
	 * Add a player to the team
	 *
	 * @param player the player to be added
	 *
	 * @return whether it was successful (it is not if the player already is in a team)
	 */
	public boolean addPlayer(Player player) {
		if (FKTeam.getTeam(player) != null)
			return false;
		
		players.add(player.getUniqueId());
		playerTeams.put(player.getUniqueId(), this);
		save();
		return true;
	}
	
	/**
	 * Remove a player from the team
	 *
	 * @param player the player to be removed
	 */
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		save();
	}
	
	/**
	 * @param player a player to check
	 *
	 * @return whether the given player is in the team
	 */
	public boolean contains(Player player) {
		return players.contains(player.getUniqueId());
	}
	
	/**
	 * @return a stream containing all the online players of the team
	 */
	public Stream<? extends Player> stream() {
		return Bukkit.getOnlinePlayers().stream().filter(p -> players.contains(p.getUniqueId()));
	}
	
	/**
	 * Mark the team as eliminated
	 */
	public void eliminate() {
		
		String winCommand = "title %s title {\"text\":\"%s eliminated\",\"color\":\"green\"}",
				looseCommand = "title %s title {\"text\":\"Eliminated\",\"color\":\"red\",\"bold\":true}";
		
		Bukkit.getOnlinePlayers().forEach(p -> {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), contains(p)
															  ? String.format(looseCommand, p.getName())
															  : String.format(winCommand, p.getName(), getName()));
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), String.format("title %s subtitle clear", p.getName()));
			
		});
		
		stream().forEach(p -> p.setGameMode(GameMode.SPECTATOR));
		
		isEliminated = true;
		save();
	}
	
	/**
	 * Mark the team as terminated
	 */
	public void terminate() {
		isTerminated = true;
		save();
	}
	
	/**
	 * Mark the team as alive
	 */
	public void revive() {
		isEliminated = false;
		setFlagLocation(baseCenter.toOverworldLocation());
		save();
	}
	
	/**
	 * @return whether the team was eliminated
	 */
	public boolean isEliminated() {
		return isTerminated || isEliminated;
	}
	
	/**
	 * Set a new center for the base of the team
	 *
	 * @param base the location of the new center
	 */
	public void setBaseCenter(Location base) {
		baseCenter = new Coordinates(base);
		setFlagLocation(base);
		save();
	}
	
	/**
	 * @return the current center of the team's base
	 */
	public Coordinates getBaseCenter() {
		return baseCenter;
	}
	
	/**
	 * @param location the location to check
	 *
	 * @return whether the given location is in the base (regardless of its Y-axis position)
	 */
	public boolean isInBase(Location location) {
		int dx = location.getBlockX() - baseCenter.x,
				dz = location.getBlockZ() - baseCenter.z;
		
		return Math.abs(dx) <= BASE_SIDE_LENGTH / 2
				&& Math.abs(dz) <= BASE_SIDE_LENGTH / 2;
	}
	
	/**
	 * @param height the height to check
	 *
	 * @return whether the height in a good range to move the flag
	 */
	public boolean checkFlagHeight(int height) {
		return Math.abs(baseCenter.y - height) <= 20;
	}
	
	/**
	 * @return the name of the team
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Init the team. It only be called at the start of the game
	 */
	public void init() {
		if (isTerminated) {
			isEliminated = false;
			return;
		}
		
		isEliminated = false;
		createBase(baseCenter);
		setFlagLocation(baseCenter.toOverworldLocation());
		
		save();
	}
	
	/**
	 * Register a new team in the game
	 *
	 * @param name the name of the new team
	 *
	 * @return the newly created team
	 */
	public static FKTeam registerNewTeam(String name) {
		if (FKTeam.getTeam(name) == null)
			return new FKTeam(name);
		else
			return null;
	}
	
	/**
	 * @param player the player to get the team from
	 *
	 * @return the current team of the Player (or null if it has none)
	 */
	@Nullable
	public static FKTeam getTeam(Player player) {
		return playerTeams.get(player.getUniqueId());
	}
	
	/**
	 * @param name the name of the seeked team
	 *
	 * @return the team with the given name (or null if there is none)
	 */
	@Nullable
	public static FKTeam getTeam(String name) {
		return allTeams.stream().filter(t -> t.getName().equals(name)).findAny().orElse(null);
	}
	
	/**
	 * Remove a player from his team
	 *
	 * @param player the player to be removed
	 *
	 * @return whether it was successful(false iff the player had no team)
	 */
	public static boolean makeOrphan(Player player) {
		FKTeam team = FKTeam.getTeam(player);
		
		if (team == null) return false;
		
		team.removePlayer(player);
		playerTeams.remove(player.getUniqueId());
		return true;
	}
	
	/**
	 * Load all the teams from the config
	 */
	public static void loadTeamsFromConfig() {
		List<String> names = config.getStringList("names");
		names.forEach(FKTeam::registerNewTeam);
	}
	
	private static void createBase(Coordinates baseCenter) {
		World world = Objects.requireNonNull(Worlds.OVERWORLD.get());
		
		int baseRad = (int) (BASE_SIDE_LENGTH / 2),
				rad = baseRad + 5;
		
		for (int z = baseCenter.z - rad; z <= baseCenter.z + rad; ++z) {
			for (int x = baseCenter.x - rad; x <= baseCenter.x + rad; ++x) {
				int highestBlock = world.getHighestBlockYAt(x, z);
				
				while (highestBlock != baseCenter.y - 1) {
					if (highestBlock > baseCenter.y - 1)
						world.getBlockAt(x, highestBlock, z).setType(Material.AIR);
					else
						world.getBlockAt(x, highestBlock + 1, z).setType(world.getBlockAt(x, highestBlock, z).getType());
					
					highestBlock = world.getHighestBlockYAt(x, z);
				}
			}
		}
		
		for (int z = -7; z <= 7; z += 14) {
			for (int x = -7; x <= 7; x += 2) {
				world.getBlockAt(new Location(world, baseCenter.x + x, baseCenter.y, baseCenter.z + z)).setType(Material.COBBLESTONE);
			}
		}
		for (int x = -7; x <= 7; x += 14) {
			for (int z = -7; z <= 7; z += 2) {
				world.getBlockAt(new Location(world, baseCenter.x + x, baseCenter.y, baseCenter.z + z)).setType(Material.COBBLESTONE);
			}
		}
	}
}
