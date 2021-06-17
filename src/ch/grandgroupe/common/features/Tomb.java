package ch.grandgroupe.common.features;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.utils.Coordinates;
import ch.grandgroupe.common.worlds.Worlds;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.*;

import javax.annotation.Nullable;
import java.util.*;

public class Tomb extends AbstractListener
{
	private static final Map<TombLocation, TombContent> tombs = new HashMap<>();
	
	private static String worldName(@Nullable World world) {
		if (world == null) return "Unknown world";
		
		String n = world.getName();
		
		if (n.matches("world(_\\d+)?")) return "Overworld";
		else if (n.matches("world_nether(_\\d+)?")) return "Nether";
		else if (n.matches("world_the_end(_\\d+)?")) return "End";
		else if (n.equals("world_lobby")) return "Lobby";
		else return "Unknown world";
	}
	
	@EventHandler
	void createTomb(PlayerDeathEvent deathEvent) {
		if (isDisabled() || deathEvent.getDrops().stream().noneMatch(Objects::nonNull) || deathEvent.getEntity().getLocation().getWorld() == Worlds.LOBBY.get()) return;
		
		Inventory inv = Bukkit.createInventory(null, 45);
		inv.addItem(deathEvent.getDrops().toArray(new ItemStack[0]));
		
		Location loc = deathEvent.getEntity().getLocation().clone();
		loc.setPitch(0);
		loc.setYaw(0);
		while (!loc.getBlock().isEmpty()) loc.add(0, 1, 0);
		
		TombContent content = new TombContent(loc, inv);
		content.spawn();
		
		tombs.put(new TombLocation(loc), content);
		deathEvent.getDrops().clear();
		
		Location ground = loc.clone().add(0, -1, 0);
		if (ground.getBlock().isLiquid() || ground.getBlock().isEmpty()) ground.getBlock().setType(Material.DIRT);
		
		loc.getBlock().setType(Material.OAK_SIGN);
		
		Sign sign = (Sign) loc.getBlock().getState();
		sign.setLine(1, "Here lies " + deathEvent.getEntity().getPlayerListName());
		sign.setLine(2, "Aged " + deathEvent.getEntity().getStatistic(Statistic.TIME_SINCE_DEATH));
		sign.update();
		
		deathEvent.setDeathMessage(deathEvent.getDeathMessage() + " at " + new Coordinates(loc) + " in the " + worldName(loc.getWorld()));
	}
	
	@EventHandler
	void breakTomb(BlockBreakEvent event) {
		if (isDisabled()) return;
		
		Location loc = event.getBlock().getLocation();
		TombLocation tombLoc = new TombLocation(loc);
		Objects.requireNonNull(loc.getWorld());
		
		if (tombs.containsKey(tombLoc)) {
			TombContent cont = tombs.get(tombLoc);
			Arrays.stream(cont.inv.getContents()).filter(Objects::nonNull).forEach(item -> loc.getWorld().dropItemNaturally(loc, item));
			cont.inv.clear();
			cont.remove();
			tombs.remove(tombLoc);
			
			event.setDropItems(false);
		}
	}
	
	private static class TombLocation
	{
		private final int x, y, z;
		private final World world;
		
		TombLocation(Location loc) {
			x     = loc.getBlockX();
			y     = loc.getBlockY();
			z     = loc.getBlockZ();
			world = Objects.requireNonNull(loc.getWorld());
		}
		
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			
			TombLocation that = (TombLocation) o;
			return x == that.x && y == that.y && z == that.z && world == that.world;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(x, y, z, world);
		}
	}
	
	private static class TombContent implements Listener
	{
		public final Inventory inv;
		private final Location loc;
		private MagmaCube glowingCube;
		
		private TombContent(Location loc, Inventory inv) {
			this.loc = loc;
			this.inv = inv;
		}
		
		void spawn() {
			glowingCube = (MagmaCube) loc.getWorld().spawnEntity(loc.clone().subtract(0, 1, 0), EntityType.MAGMA_CUBE);
			
			glowingCube.setAI(false);
			glowingCube.setGlowing(true);
			glowingCube.setInvulnerable(true);
			glowingCube.setInvisible(true);
			glowingCube.setPersistent(true);
			glowingCube.setSilent(true);
			glowingCube.setSize(1);
		}
		
		void remove() {
			glowingCube.setInvulnerable(false);
			glowingCube.setHealth(0);
		}
		
		@EventHandler
		void removeLoot(EntityDeathEvent event)
		{
			if (event.getEntity() == glowingCube) event.getDrops().clear();
		}
	}
}
