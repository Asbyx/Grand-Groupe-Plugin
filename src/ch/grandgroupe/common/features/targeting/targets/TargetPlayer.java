package ch.grandgroupe.common.features.targeting.targets;

import ch.grandgroupe.common.features.targeting.Target;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class TargetPlayer extends Target
{
	private final Player player;
	
	public TargetPlayer(org.bukkit.entity.Player player) {
		super(getHead(player));
		this.player = player;
	}
	
	private static ItemStack getHead(Player p) {
		ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = Objects.requireNonNull((SkullMeta) stack.getItemMeta());
		meta.setOwningPlayer(p);
		meta.setDisplayName(p.getName());
		stack.setItemMeta(meta);
		
		return stack;
	}
	
	@Override
	public Location getLocation(org.bukkit.entity.Player player) {
		return player.getWorld() == this.player.getWorld() ? this.player.getLocation() : null;
	}
	
	@Override
	public boolean canBeSetFor(Player player) {
		return player != this.player && player.getWorld() == this.player.getWorld();
	}
}
