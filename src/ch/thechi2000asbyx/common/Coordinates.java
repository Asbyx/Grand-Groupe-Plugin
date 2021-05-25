package ch.thechi2000asbyx.common;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.regex.*;

public class Coordinates
{
	public final int x, y, z;
	
	public Coordinates(Location location)
	{
		x = location.getBlockX();
		y = location.getBlockY();
		z = location.getBlockZ();
	}
	public Coordinates(int i, int i1, int i2)
	{
		x = i;
		y = i1;
		z = i2;
	}
	
	@Override
	public String toString()
	{
		return String.format("[%d, %d, %d]", x, y, z);
	}
	
	public static Coordinates fromString(String s)
	{
		Pattern pattern = Pattern.compile("^\\[(\\d+), (\\d+), (\\d+)]$");
		Matcher matcher = pattern.matcher(s);
		
		if (!matcher.matches()) throw new IllegalArgumentException();
		
		return new Coordinates(
				Integer.parseInt(matcher.group(1)),
				Integer.parseInt(matcher.group(2)),
				Integer.parseInt(matcher.group(3))
		);
	}
	
	public int distanceTo(Coordinates that)
	{
		int dx = that.x - x,
				dy = that.y - y,
				dz = that.z - z;
		
		return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public static int distanceBetween(Location a, Location b)
	{
		int dx = a.getBlockX() - b.getBlockX(),
				dy = a.getBlockY() - b.getBlockY(),
				dz = a.getBlockZ() - b.getBlockZ();
		
		return (int) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public static int distanceBetween(Player a, Player b)
	{
		return distanceBetween(a.getLocation(), b.getLocation());
	}
}
