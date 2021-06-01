package ch.grandgroupe.common.utils;

import ch.grandgroupe.common.worlds.Worlds;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.regex.*;

/**
 * Simplification of the Bukkit.Location class, use this to display coordinates
 */
public final class Coordinates
{
	public final int x, y, z;

	/**
	 * Construct Coordinates from a Location
	 * @param location the location given by the game
	 */
	public Coordinates(Location location) {
		x = location.getBlockX();
		y = location.getBlockY();
		z = location.getBlockZ();
	}

	/**
	 * Construct Coordinates from 3 int
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public Coordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return String.format("[%d, %d, %d]", x, y, z);
	}

	/**
	 * Return a Coordinate built from a string
	 * @param s the string
	 * @return the coordinates
	 */
	public static Coordinates fromString(String s) {
		Pattern pattern = Pattern.compile("\\[(-?\\d+), (-?\\d+), (-?\\d+)]");
		Matcher matcher = pattern.matcher(s);
		
		if (!matcher.matches()) throw new IllegalArgumentException(s);
		
		return new Coordinates(
				Integer.parseInt(matcher.group(1)),
				Integer.parseInt(matcher.group(2)),
				Integer.parseInt(matcher.group(3))
		);
	}

	/**
	 * Convert the Coordinate to a Location in the Overworld
	 * @return the location
	 */
	public Location toOverworldLocation() {
		return new Location(Worlds.OVERWORLD.get(), x, y, z);
	}

	/**
	 * Return a new Coordinates resulting from this one and the one given in argument
	 * @param that the other Coordinates
	 * @return a new Coordinates
	 */
	public Coordinates add(Coordinates that) {
		return new Coordinates(x + that.x,
				y + that.y,
				z + that.z);
	}

	/**
	 * Return a new Coordinates resulting from this one and a new Coordinates constructed from the arguments
	 * @param x x coordinate of the added coordinate
	 * @param y y coordinate of the added coordinate
	 * @param z z coordinate of the added coordinate
	 * @return a new Coordinates
	 */
	public Coordinates add(int x, int y, int z) {
		return new Coordinates(x + this.x,
				y + this.y,
				z + this.z);
	}

	/**
	 * Return the distance between 2 LOCATIONS !
	 * @param a first Location
	 * @param b second Location
	 * @return the distance, as an integer
	 */
	public static int distanceBetween(Location a, Location b) {
		int dx = a.getBlockX() - b.getBlockX(),
				dy = a.getBlockY() - b.getBlockY(),
				dz = a.getBlockZ() - b.getBlockZ();
		
		return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	/**
	 * Return the distance between 2 Players
	 * @param a first Player
	 * @param b second Player
	 * @return the distance, as an integer
	 */
	public static int distanceBetween(Player a, Player b) {
		return distanceBetween(a.getLocation(), b.getLocation());
	}
}
