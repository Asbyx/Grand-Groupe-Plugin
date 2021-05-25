package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.common.Coordinates;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Objects;
import java.util.Random;

public class EventsManager implements Listener {
    public static final int TICK = 20;
    private long lastMiddleChest, nextRandomChest;
    private final long timerOfMiddleChest, minTimeForRandomChest, maxTimeForRandomChest;
    private final int timerOfBloodNight, radius, pvpAllowed;
    private int totalDays = 1;

    /**
     * Construct the listener start the game !
     *
     * @param timerOfMiddleChest:    time between 2 middle chests
     * @param minTimeForRandomChest: minimum time between 2 random chests
     * @param maxTimeForRandomChest: maximum time between 2 random chests
     * @param timerOfBloodNight:     number of nights between 2 blood nights
     */
    EventsManager(long timerOfMiddleChest, long minTimeForRandomChest, long maxTimeForRandomChest, int timerOfBloodNight, int radius, int pvpAllowed) {
        World world = Objects.requireNonNull(Bukkit.getWorld("world"));
        this.lastMiddleChest = world.getGameTime();
        this.radius = radius;
        this.timerOfMiddleChest = timerOfMiddleChest * TICK;
        this.minTimeForRandomChest = minTimeForRandomChest * TICK;
        this.maxTimeForRandomChest = maxTimeForRandomChest * TICK;
        this.timerOfBloodNight = timerOfBloodNight;
        this.pvpAllowed = pvpAllowed;
        this.nextRandomChest = getNextRandomChest(world.getGameTime());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "weather clear");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "time set 1");

        Main.broadcast("The game is on ! May the odds be with you !");
        world.setPVP(false);
        initMiddleChest(world);
    }

    EventsManager(){
        this(30, 30, 60, 1, 100, 2);
    }

    public void update(long now) {
        World world = Objects.requireNonNull(Bukkit.getWorld("world"));

        if ((now - lastMiddleChest) >= timerOfMiddleChest) {
            lastMiddleChest = now;
            spawnMiddleChest(world);
        }

        if (now >= nextRandomChest) {
            spawnRandomChest(world);
            nextRandomChest = getNextRandomChest(now);
        }

        if (totalDays % timerOfBloodNight == 0 && world.getTime() == 18000) {
            setBloodNight(world);
        }
        if (world.getTime() == 0) {
            Main.broadcast("Day " + ++totalDays + " !");
            if (totalDays % timerOfBloodNight == 0) Main.broadcast("Next night will be bloody...");

            if (totalDays == pvpAllowed) {
                world.setPVP(true);
                Main.broadcast("PvP is now allowed !");
            }
        }
    }

    public static void initMiddleChest(World world) {
        Main.broadcast("The middle chest is at : " + new Coordinates(world.getSpawnLocation()));
        if (world.getSpawnLocation().getBlock().getType() != Material.CHEST)
            world.getSpawnLocation().getBlock().setType(Material.CHEST);
    }

    @EventHandler
    public void onChargedCreeperDeath(EntityDeathEvent event) {
        if (event.getEntity().getType() == EntityType.CREEPER && ((Creeper) event.getEntity()).isPowered()) {
            event.getDrops().clear();
            ItemStack tnt = new ItemStack(Material.TNT);
            tnt.setAmount(new Random().nextInt(4) + 1);
            event.getDrops().add(tnt);
        }
    }

    private void setBloodNight(World world) {
        for (int i = 0; i < radius * 2; i++) {
            world.strikeLightning(world.getSpawnLocation().clone().add(new Random().nextInt(radius*2) - radius, 0, new Random().nextInt(radius*2) - radius));
            EntityType ent = mobs[new Random().nextInt(mobs.length)];
            world.spawnEntity(world.getSpawnLocation().clone().add(new Random().nextInt(radius*2) - radius, 0, new Random().nextInt(radius*2) - radius), ent);
        }

        world.getEntities().stream().filter(e -> e.getType() == EntityType.CREEPER).forEach(c -> ((Creeper) c).setPowered(true));

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather rain");
        Main.broadcast("Good luck...");
    }

    private void spawnRandomChest(World world) {
        Location middleCircle = world.getSpawnLocation().clone().add(new Vector(new Random().nextInt(radius * 2) - radius, 0, new Random().nextInt(radius * 2) - radius));
        Location chestLocation = middleCircle.clone();
        chestLocation.add(new Vector(new Random().nextInt(40) - 20, 0, new Random().nextInt(40) - 20));

        while (world.getBlockAt(chestLocation.clone().add(0, 0, 0)).getType() != Material.AIR ||
                world.getBlockAt(chestLocation.clone().add(0, -1, 0)).getType() == Material.AIR) {
            if (world.getBlockAt(chestLocation).getType() == Material.AIR) chestLocation.add(0, -1, 0);
            else chestLocation.add(0, 1, 0);
        }

        world.getBlockAt(chestLocation).setType(Material.CHEST);
        ((Chest) world.getBlockAt(chestLocation).getState()).getBlockInventory().addItem(generateItems(8, 9, 11, stuff));
        Main.broadcast("A chest has spawned near " + new Coordinates(middleCircle) + ": the stuff is waiting for you !");
    }

    private void spawnMiddleChest(World world) {
        world.getSpawnLocation().getBlock().setType(Material.CHEST);

        ((Chest) world.getSpawnLocation().getBlock().getState()).getBlockInventory().clear();
        ((Chest) world.getSpawnLocation().getBlock().getState()).getBlockInventory().addItem(generateItems(15, 2, 5, useful));
        Main.broadcast("The middle chest has been filled !");
    }

    private long getNextRandomChest(long now) {
        return now + new Random().nextInt((int) (maxTimeForRandomChest - minTimeForRandomChest)) + minTimeForRandomChest;
    }

    private ItemStack[] generateItems(int nb, int notMuch, int normal, Material[] all) {
        ItemStack[] content = new ItemStack[new Random().nextInt(nb) + 1];
        for (int i = 0; i < content.length; i++) {
            int index = new Random().nextInt(all.length);
            ItemStack item = new ItemStack(all[index]);
            item.setAmount(
                    index <= notMuch ? new Random().nextInt(2) + 1 :
                            index <= normal ? new Random().nextInt(5) + 1 : new Random().nextInt(10) + 1
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
    private final EntityType[] mobs = new EntityType[]{
            EntityType.CREEPER,
            EntityType.ZOMBIE,
            EntityType.CAVE_SPIDER,
            EntityType.SKELETON_HORSE,
            EntityType.SKELETON,
            EntityType.WITCH,
            EntityType.SPIDER
    };
}
