package ch.grandgroupe.minigames.coop_defense.creatures;

import ch.grandgroupe.common.Main;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;


public class CreatureToDefend extends EntityZombieVillager
{
	private static final int HEALTH = 100;
	public static CreatureToDefend CREATURE_TO_DEFEND;
	private final BossBar bar;
	private final int id;
	
	public CreatureToDefend(Location location) {
		super(EntityTypes.ZOMBIE_VILLAGER, ((CraftWorld) location.getWorld()).getHandle());
		setCustomName(new ChatComponentText("Zombie Villager"));
		setPosition(location.getX(), location.getY(), location.getZ());
		setAbsorptionHearts(HEALTH - 20);
		setBaby(false);
		
		CREATURE_TO_DEFEND = this;
		
		bar = Bukkit.createBossBar("Zombie Villager", BarColor.GREEN, BarStyle.SEGMENTED_10);
		bar.setVisible(true);
		Bukkit.getOnlinePlayers().forEach(bar::addPlayer);
		
		id = Main.SCHEDULER.scheduleSyncRepeatingTask(Main.PLUGIN, this::update, 0, 1);
	}
	
	private void update() {
		bar.setProgress((getHealth() + getAbsorptionHearts()) / HEALTH);
	}
	
	public void stop() {
		Main.SCHEDULER.cancelTask(id);
		bar.setVisible(false);
	}
	
	public BossBar getBar() {
		return bar;
	}
	
	@Override
	protected void initPathfinder() {
	}
	
	public static boolean equal(org.bukkit.entity.Entity entity) {
		return entity.getCustomName() != null && entity.getCustomName().equals(CreatureToDefend.CREATURE_TO_DEFEND.getCustomName().getString());
	}
}
