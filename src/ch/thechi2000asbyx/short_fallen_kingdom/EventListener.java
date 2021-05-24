package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class EventListener implements Listener {
    private long lastMiddleChest, lastRandomChest;
    private final long timerOfMiddleChest, minTimeForRandomChest, maxTimeForRandomChest;
    private int timerOfBloodNight, nightsPassed;

    /**
     * Construct the listener start the game !
     *
     * @param timerOfMiddleChest:    time between 2 middle chests
     * @param minTimeForRandomChest: minimum time between 2 random chests
     * @param maxTimeForRandomChest: maximum time between 2 random chests
     * @param timerOfBloodNight:     number of nights between 2 blood nights
     */
    EventListener(long timerOfMiddleChest, long minTimeForRandomChest, long maxTimeForRandomChest, int timerOfBloodNight) {
        lastMiddleChest = lastRandomChest = Date.from(Instant.now()).getTime();
        nightsPassed = 0;
        this.timerOfMiddleChest = timerOfMiddleChest * 1000;
        this.minTimeForRandomChest = minTimeForRandomChest * 1000;
        this.maxTimeForRandomChest = maxTimeForRandomChest * 1000;
        this.timerOfBloodNight = timerOfBloodNight;
    }

    @EventHandler
    public void initEvent(PlayerMoveEvent event) {
        long now = Date.from(Instant.now()).getTime();
        if(event.getPlayer().getWorld() == Bukkit.getServer().getWorld("world")){
            World world = event.getPlayer().getWorld();

            if ((now - lastMiddleChest) >= timerOfMiddleChest ) {
                lastMiddleChest = now;
                spawnMiddleChest(world);
            }

            if (now - lastRandomChest > minTimeForRandomChest) {
                if (now - lastRandomChest > maxTimeForRandomChest) {
                    spawnRandomChest(world);
                    lastRandomChest = now;
                }
                else if (new Random().nextInt((int) (1e6) + 1) == 1) {
                    spawnRandomChest(world);
                    lastRandomChest = now;
                }
            }

            if (world.getTime() == 0) nightsPassed++;

            if (nightsPassed == timerOfBloodNight && world.getTime() == 18000) setBloodNight();
        }
    }

    private void setBloodNight() {

    }

    private void spawnRandomChest(World world) {
        Location middleCircle = world.getSpawnLocation().clone().add(new Vector(new Random().nextInt(100) - 50, 0, new Random().nextInt(100) - 50));
        Location chestLocation = middleCircle.clone();
        chestLocation.add(new Vector(new Random().nextInt(20), new Random().nextInt(20), 0));

        while(  world.getBlockAt(chestLocation.clone().add(0, 0, 0)).getType() != Material.AIR ||
                world.getBlockAt(chestLocation.clone().add(0, -1, 0)).getType() == Material.AIR ){
            if(world.getBlockAt(chestLocation).getType() == Material.AIR) chestLocation.add(0, -1, 0);
            else chestLocation.add(0, 1, 0);
        }

        world.getBlockAt(chestLocation).setType(Material.CHEST);
        ((Chest) world.getBlockAt(chestLocation).getState()).getBlockInventory().addItem(generateItems(8, 9, 11, stuff));
        Bukkit.broadcastMessage("[SERVER] A chest has spawned near " + new Coordinates(middleCircle) + ": the stuff is waiting for you !");
    }

    private void spawnMiddleChest(World world) {
        world.getSpawnLocation().getBlock().setType(Material.CHEST);

        ((Chest) world.getSpawnLocation().getBlock().getState()).getBlockInventory().clear();
        ((Chest) world.getSpawnLocation().getBlock().getState()).getBlockInventory().addItem(generateItems(15, 2, 5, useful));
        Bukkit.broadcastMessage("[SERVER] The middle chest has been filled !");
    }

    private ItemStack[] generateItems(int nb, int notMuch, int normal, Material[] all) {
        ItemStack[] content = new ItemStack[new Random().nextInt(nb) + 1];
        for (int i = 0; i < content.length; i++) {
            int index = new Random().nextInt(all.length);
            ItemStack item = new ItemStack(all[index]);
            item.setAmount(
                    index <= notMuch ? new Random().nextInt(3) + 1 :
                            index <= normal ? new Random().nextInt(15) + 1 : new Random().nextInt(31) + 1
            );
            content[i] = item;
        }
        return content;
    }

    private final Material[] useful = new Material[]{
            Material.TNT,
            Material.DIAMOND,
            Material.MUSHROOM_STEW,

            Material.IRON_INGOT,
            Material.COAL_BLOCK,
            Material.COOKED_BEEF,

            Material.BREAD,
            Material.ARROW,
            Material.EXPERIENCE_BOTTLE
    };
    private final Material[] stuff = new Material[]{
            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS,
            Material.DIAMOND_BOOTS,
            Material.DIAMOND_SWORD,
            Material.IRON_SWORD,
            Material.DIAMOND_PICKAXE,
            Material.BOW,

            Material.GOLDEN_APPLE,
            Material.DIAMOND,

            Material.ARROW,
            Material.COOKED_BEEF
    };
}
