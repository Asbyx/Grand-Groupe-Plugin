package ch.grandgroupe.common.features.targeting;

import ch.grandgroupe.common.features.targeting.targets.WorldSpawn;
import org.bukkit.entity.Player;

public class TargetManager
{
	private final Player player;
	private Target target;
	
	public TargetManager(Player player) {
		this.player = player;
		target      = new WorldSpawn();
	}
	
	public void update() {
		player.setCompassTarget(target.getLocation(player));
	}
	
	public void setTarget(Target target) {
		if (target == null) return;
		
		this.target = target;
	}
}
