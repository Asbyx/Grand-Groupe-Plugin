package ch.grandgroupe.minigames.coop_defense.creatures;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.EntityTargetEvent;

public class AttackSkeleton extends EntitySkeleton
{
	public AttackSkeleton(Location location) {
		super(EntityTypes.SKELETON, ((CraftWorld) location.getWorld()).getHandle());
		setPosition(location.getX(), location.getY(), location.getZ());
	}
	
	@Override
	protected void initPathfinder() {
		;
		goalSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, CreatureToDefend.class, true));
		setGoalTarget(CreatureToDefend.CREATURE_TO_DEFEND, EntityTargetEvent.TargetReason.CUSTOM, true);
	}
}
