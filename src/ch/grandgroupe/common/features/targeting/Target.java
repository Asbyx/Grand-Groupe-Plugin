package ch.grandgroupe.common.features.targeting;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Target
{
	private final ItemStack itemStack;
	
	protected Target(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
	
	/**
	 * Return the location of the target
	 *
	 * @param player the player who is targeting
	 *
	 * @return the location of the target
	 */
	public abstract Location getLocation(Player player);
	
	/**
	 * @return the ItemStack representing the target
	 */
	public final ItemStack getRepresentativeItem() {
		return itemStack;
	}
	
	/**
	 * Indicates whether this target is valid for a given player
	 *
	 * @param player the player who wants to choose this target
	 *
	 * @return whether the target is valid
	 */
	public boolean isValidFor(Player player) {
		return true;
	}
}
