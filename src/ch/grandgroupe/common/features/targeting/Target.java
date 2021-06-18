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
	public ItemStack getRepresentativeItem() {
		return itemStack;
	}
}
