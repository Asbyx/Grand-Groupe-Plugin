package ch.grandgroupe.minigames.coop_defense.creatures;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class DefendZombie extends EntityZombie
{
	public DefendZombie(Location location) {
		super(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle());
		setPosition(location.getX(), location.getY(), location.getZ());
	}
	
	@Override
	protected void initPathfinder() {
		this.goalSelector.a(2, new PathfinderGoalZombieAttack(this, 1.0D, false));
		this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
		this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
	}
}
