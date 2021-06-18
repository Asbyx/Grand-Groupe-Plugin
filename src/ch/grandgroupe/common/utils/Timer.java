package ch.grandgroupe.common.utils;

import org.bukkit.World;

import javax.annotation.Nonnull;

public class Timer
{
	private final World world;
	private long startTime;
	
	/**
	 * Construct a Timer
	 *
	 * @param world the world for which the time will be measured
	 */
	public Timer(@Nonnull World world) {
		this(world.getGameTime(), world);
	}
	
	/**
	 * Construct a Timer
	 *
	 * @param startTime the time on which the timer started
	 * @param world the world for which the time will be measured
	 */
	public Timer(long startTime, @Nonnull World world) {
		this.startTime = startTime;
		this.world     = world;
	}
	
	/**
	 * @return the time elapsed since the construction of the timer (in ticks)
	 */
	public long getElapsedTime() {
		return world.getGameTime() - startTime;
	}
	
	/**
	 * @return a String representing the time elapsed (format hh mm ss)
	 */
	public String getElapsedTimeAsString() {
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
	
	/**
	 * Restart the timer
	 */
	public void restart() {
		startTime = world.getGameTime();
	}
}
