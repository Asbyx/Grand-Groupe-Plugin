package ch.thechi2000asbyx.short_fallen_kingdom;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Date;
import java.time.Instant;
import java.util.Random;

public class EventListener implements Listener {
	private long lastMiddleChest, lastRandomChest;
	private final long timerOfMiddleChest, minTimeForRandomChest, maxTimeForRandomChest;
	private int timerOfBloodNight, nightsPassed;
	
	/**
	 * Construct the listener start the game !
	 * @param timerOfMiddleChest: time between 2 middle chests
	 * @param minTimeForRandomChest: minimum time between 2 random chests
	 * @param maxTimeForRandomChest: maximum time between 2 random chests
	 * @param timerOfBloodNight: number of nights between 2 blood nights
	 */
	EventListener(long timerOfMiddleChest, long minTimeForRandomChest, long maxTimeForRandomChest, int timerOfBloodNight){
		lastMiddleChest = lastRandomChest = Date.from(Instant.now()).getTime();
		nightsPassed = 0;
		this.timerOfMiddleChest = timerOfMiddleChest * 1000;
		this.minTimeForRandomChest = minTimeForRandomChest * 1000;
		this.maxTimeForRandomChest = maxTimeForRandomChest * 1000;
		this.timerOfBloodNight = timerOfBloodNight;
	}
	
	@EventHandler
	public void initEvent(PlayerMoveEvent event){
		long now = Date.from(Instant.now()).getTime();
		
		if ((now - lastMiddleChest) % timerOfMiddleChest == 0) {
			lastMiddleChest = now;
			spawnMiddleChest(event.getPlayer().getWorld());
		}
		
		if (now - lastRandomChest > minTimeForRandomChest){
			if (now - lastRandomChest > maxTimeForRandomChest) spawnRandomChest();
			else if(new Random().nextInt((int) (now - maxTimeForRandomChest)) == 1) spawnRandomChest();
		}
		
		if(event.getPlayer().getWorld().getTime() == 0) nightsPassed++;
		
		if (nightsPassed == timerOfBloodNight && event.getPlayer().getWorld().getTime() == 18000) setBloodNight();
	}
	
	private void setBloodNight() {
	
	}
	
	private void spawnRandomChest() {
	
	}
	
	private void spawnMiddleChest(World world) {
		world.getSpawnLocation().getBlock().setType(Material.CHEST);
		((Chest) world.getSpawnLocation().getBlock().getState()).getBlockInventory().addItem(generateItems());
	}
	private ItemStack[] generateItems() {
		ItemStack[] content = new ItemStack[new Random().nextInt(10)];
		for (int i = 0; i < content.length; i++) {
			//ItemStack item = new ItemStack();
			//item.getType() = ;
		}
		return content;
	}

	//private Enum useful;{
	
	//}
}
