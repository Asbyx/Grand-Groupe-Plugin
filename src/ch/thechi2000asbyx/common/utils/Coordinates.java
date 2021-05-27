package ch.thechi2000asbyx.common.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.regex.*;

public class Coordinates
{
	public final int x, y, z;
	
	public Coordinates(Location location) {
		x = location.getBlockX();
		y = location.getBlockY();
		z = location.getBlockZ();
	}
	public Coordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public String toString() {
		return String.format("[%d, %d, %d]", x, y, z);
	}
	
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
	
	public Location toOverworldLocation() {
		return new Location(Bukkit.getWorld("world"), x, y, z);
	}
	public Coordinates add(Coordinates that) {
		return new Coordinates(x + that.x,
				y + that.y,
				z + that.z);
	}
	public Coordinates add(int x, int y, int z) {
		return new Coordinates(x + this.x,
				y + this.y,
				z + this.z);
	}
	
	public int distanceTo(Coordinates that) {
		int dx = that.x - x,
				dy = that.y - y,
				dz = that.z - z;
		
		return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public static int distanceBetween(Location a, Location b) {
		int dx = a.getBlockX() - b.getBlockX(),
				dy = a.getBlockY() - b.getBlockY(),
				dz = a.getBlockZ() - b.getBlockZ();
		
		return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	public static int distanceBetween(Player a, Player b) {
		return distanceBetween(a.getLocation(), b.getLocation());
	}
}
