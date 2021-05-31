package ch.grandgroupe.common.utils;

import org.bukkit.World;

public class Timer
{
	private final long startTime;
	private final World world;
	
	public Timer(World world) {
		startTime  = world.getGameTime();
		this.world = world;
	}
	
	public long getElapsedTime() {
		return world.getGameTime() - startTime;
	}
	
	public final String getElapsedTimeAsString() {
		long ticks = getElapsedTime();
		long secs = (ticks / 20) % 60,
				min = (ticks / 1200) % 60,
				hours = ticks / 72000;
		
		if (min == 0 && hours == 0)
			return String.format("%ds", secs);
		else if (hours == 0)
			return String.format("%dm %ds", min, secs);
		else
			return String.format("%dh %dm %ds", hours, min, secs);
	}
}
