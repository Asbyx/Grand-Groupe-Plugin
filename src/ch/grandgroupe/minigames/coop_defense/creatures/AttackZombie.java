package ch.grandgroupe.minigames.coop_defense.creatures;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.EntityTargetEvent;

public class AttackZombie extends EntityZombie
{
	public AttackZombie(Location location) {
		super(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle());
		setPosition(location.getX(), location.getY(), location.getZ());
	}
	
	@Override
	protected void initPathfinder() {
		;
		goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.2, true));
		setGoalTarget(CreatureToDefend.CREATURE_TO_DEFEND, EntityTargetEvent.TargetReason.CUSTOM, true);
	}
}
