package ch.grandgroupe.minigames.short_fallen_kingdom.Scoreboards;

import ch.grandgroupe.common.listeners.AbstractListener;
import ch.grandgroupe.minigames.short_fallen_kingdom.Events.EventsManager;
import ch.grandgroupe.common.Main;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.FKTeam;
import com.mojang.datafixers.util.Pair;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Compass extends AbstractListener
{
	private final Player owner;
	private final FKTeam team;
	private final Inventory inventory;
	private final EventsManager eventsManager;
	private final Map<Integer, Location> map;
	private final ItemStack middleChestItem;
	private final ItemStack randomChestItem;
	
	public Compass(Player owner, EventsManager eventsManager) {
		this.owner         = owner;
		team               = FKTeam.getTeam(owner);
		inventory          = Bukkit.createInventory(owner, 27, "Choose a target - " + owner.getName());
		this.eventsManager = eventsManager;
		map                = new HashMap<>();
		
		middleChestItem = new ItemStack(Material.CHEST);
		ItemMeta mciMeta = Objects.requireNonNull(middleChestItem.getItemMeta());
		
		mciMeta.setDisplayName("Middle chest");
		middleChestItem.setItemMeta(mciMeta);
		
		randomChestItem = new ItemStack(Material.ENDER_CHEST);
		ItemMeta rciMeta = Objects.requireNonNull(randomChestItem.getItemMeta());
		
		rciMeta.setDisplayName("Random chest");
		randomChestItem.setItemMeta(rciMeta);
		
		Bukkit.getPluginManager().registerEvents(this, Main.PLUGIN);
	}
	
	@EventHandler
	public void removeCompassOnDeath(PlayerDeathEvent event) {
		if (event.getEntity() == owner) event.getDrops().removeIf(i -> i.getType() == Material.COMPASS);
	}
	
	@EventHandler
	public void addCompassOnRespawn(PlayerRespawnEvent event) {
		if (event.getPlayer() == owner) event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
	}
	
	@EventHandler
	public void openGui(PlayerInteractEvent event) {
		if (isDisabled() || event.getPlayer() != owner) return;
		
		ItemStack item = event.getItem();
		if (item == null
				|| item.getType() != Material.COMPASS
				|| !(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK))
			return;
		
		List<Pair<ItemStack, Location>> list = new ArrayList<>();
		
		list.add(new Pair<>(middleChestItem, Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation()));
		if (eventsManager.getRandomChestCenter() != null) list.add(new Pair<>(randomChestItem, eventsManager.getRandomChestCenter()));
		
		FKTeam.allTeams.forEach(t ->
		{
			ItemStack stack = new ItemStack(team == t ? Material.LIME_WOOL : Material.RED_WOOL);
			ItemMeta meta = Objects.requireNonNull(stack.getItemMeta());
			meta.setDisplayName(t.getName());
			stack.setItemMeta(meta);
			
			list.add(new Pair<>(stack, t.getBaseCenter().toOverworldLocation()));
		});
		
		AtomicInteger i = new AtomicInteger();
		list.forEach(p ->
		{
			inventory.setItem(i.get(), p.getFirst());
			map.put(i.get(), p.getSecond());
			i.incrementAndGet();
		});
		
		event.getPlayer().openInventory(inventory);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (isDisabled()) return;
		
		if (event.getClickedInventory() == inventory) {
			owner.setCompassTarget(map.get(event.getSlot()));
			owner.closeInventory();
		}
	}
}
