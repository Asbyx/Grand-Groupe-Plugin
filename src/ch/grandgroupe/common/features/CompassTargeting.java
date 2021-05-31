package ch.grandgroupe.common.features;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompassTargeting extends AbstractListener
{
	private static final int INVENTORY_SIZE = 18;
	
	private final Map<Player, Player> playerTargets;
	private Map<Player, Inventory> inventories;
	private List<Player> players;
	
	public CompassTargeting() {
		playerTargets = new HashMap<>();
		inventories   = new HashMap<>();
	}
	
	@Override
	public void enable() {
		super.enable();
		players = new ArrayList<>(Bukkit.getOnlinePlayers());
		
		if (players.size() == 2) {
			Player player1 = players.get(0), player2 = players.get(1);
			playerTargets.put(player1, player2);
			playerTargets.put(player2, player1);
		}
		else
			inventories = players.stream()
								 .collect(HashMap::new,
										 (map, player) -> map.put(player, generateInventory(player)),
										 HashMap::putAll);
	}
	
	@EventHandler
	public void updateCompasses(PlayerMoveEvent event) {
		if (isDisabled()) return;
		
		playerTargets.entrySet()
					 .stream()
					 .filter(e -> e.getValue() == event.getPlayer())
					 .forEach(e -> e.getKey().setCompassTarget(event.getPlayer().getLocation()));
	}
	
	@EventHandler
	public void openGUI(PlayerInteractEvent event) {
		if (isDisabled()) return;
		
		if (players.size() != 2
				&& players.contains(event.getPlayer())
				&& (event.getAction() == Action.RIGHT_CLICK_BLOCK
				|| event.getAction() == Action.RIGHT_CLICK_AIR))
			event.getPlayer().openInventory(inventories.get(event.getPlayer()));
	}
	
	@EventHandler
	public void setTarget(InventoryClickEvent event) {
		if (isDisabled()) return;
		
		inventories.entrySet()
				   .stream()
				   .filter(e -> e.getValue() == event.getClickedInventory())
				   .map(Map.Entry::getKey)
				   .findFirst()
				   .ifPresent(player ->
				   {
					   player.closeInventory();
			
					   Player target = players.get(event.getSlot());
			
					   playerTargets.put(player, target);
					   player.setCompassTarget(target.getLocation());
				   });
	}
	
	@EventHandler
	public void addPlayer(PlayerJoinEvent event) {
		if (isDisabled()) return;
		
		Player player = event.getPlayer();
		
		players.add(player);
		
		if (players.size() == 2) {
			Player player1 = players.get(0), player2 = players.get(1);
			playerTargets.put(player1, player2);
			playerTargets.put(player2, player1);
		}
		else {
			inventories.forEach((p, inv) -> inv.setItem(players.size() - 1, getHead(player)));
			inventories.put(player, generateInventory(player));
		}
	}
	
	private Inventory generateInventory(Player player) {
		Inventory inv = Bukkit.createInventory(player, INVENTORY_SIZE, "Choose a target");
		AtomicInteger i = new AtomicInteger(0);
		players.forEach(p -> inv.setItem(i.getAndIncrement(), getHead(p)));
		return inv;
	}
	
	private ItemStack getHead(Player p) {
		ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = Objects.requireNonNull((SkullMeta) stack.getItemMeta());
		meta.setOwningPlayer(p);
		meta.setDisplayName(p.getName());
		stack.setItemMeta(meta);
		
		return stack;
	}
}
