package ch.grandgroupe.common.listeners;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.Objects;

/**
 * Tnt bow: If a player use a bow and have a TNT block in his left hand, the arrow he will throw will explode at the impact and the TNT block will be consumed.
 * 			The power of the explosion can be set by using /rules tntBow [float]
 */
public class TntBow extends AbstractListener {
	private float power = 2F;

	@EventHandler
	public void onTntProjectileLaunch(ProjectileLaunchEvent event) {
		if (isDisabled()) {
			return;
		}

		if (event.getEntity().getType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player) {
			Player player = (Player) event.getEntity().getShooter();
			Arrow arrow = (Arrow) event.getEntity();

			if (player.getInventory().getItemInOffHand().getType() == Material.TNT) {
				player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
				arrow.setCustomName("tntArrow");
				arrow.setColor(Color.RED);
			}
		}
	}

	@EventHandler
	public void onTntProjectileArrive(ProjectileHitEvent event) {
		if (isDisabled()) return;

		if (Objects.equals(event.getEntity().getCustomName(), "tntArrow")) {
			event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), power);
			event.getEntity().remove();
		}
	}

	/**
	 * Set the power of the explosion at value (TNT = 4F)
	 * @param value new value of the explosion
	 */
	public void setPower(float value){
		power = value;
	}
}
