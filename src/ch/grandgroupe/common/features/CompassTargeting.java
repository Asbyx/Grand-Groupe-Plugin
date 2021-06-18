package ch.grandgroupe.common.features;

import ch.grandgroupe.common.features.targeting.*;
import ch.grandgroupe.common.features.targeting.targets.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CompassTargeting extends AbstractListener
{
	private static final int INVENTORY_SIZE = 18;
	
	private final Map<Player, TargetManager> playerTargets;
	private final List<Target> availableTargets;
	private Map<Player, Inventory> inventories;
	private List<Player> players;
	
	public CompassTargeting() {
		playerTargets    = new HashMap<>();
		inventories      = new HashMap<>();
		availableTargets = new ArrayList<>();
	}
	
	@Override
	public void enable() {
		super.enable();
		
		players = new ArrayList<>(Bukkit.getOnlinePlayers());
		
		players.forEach(p -> playerTargets.put(p, new TargetManager(p)));
		
		availableTargets.add(new WorldSpawn());
		availableTargets.addAll(players.stream().map(TargetPlayer::new).collect(Collectors.toList()));
		availableTargets.add(new NetherFortress());
		
		inventories = players.stream()
							 .collect(HashMap::new,
									 (map, player) -> map.put(player, generateInventory(player)),
									 HashMap::putAll);
	}
	
	@EventHandler
	public void updateCompasses(PlayerMoveEvent event) {
		if (isDisabled()) return;
		playerTargets.values().forEach(TargetManager::update);
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
		final ItemStack item = event.getCurrentItem();
		
		if (isDisabled() || item == null) return;
		
		inventories.entrySet()
				   .stream()
				   .filter(e -> e.getValue() == event.getClickedInventory())
				   .map(Map.Entry::getKey)
				   .findFirst()
				   .ifPresent(player ->
				   {
					   player.closeInventory();
					   availableTargets.stream().filter(t -> t.getRepresentativeItem().equals(item)).findAny().ifPresent(playerTargets.get(player)::setTarget);
				   });
	}
	
	@EventHandler
	public void addPlayer(PlayerJoinEvent event) {
		if (isDisabled()) return;
		
		Player player = event.getPlayer();
		
		players.add(player);
		players.forEach(p -> inventories.put(p, generateInventory(p)));
	}
	
	private Inventory generateInventory(Player player) {
		Inventory inv = Bukkit.createInventory(player, INVENTORY_SIZE, "Choose a target");
		AtomicInteger i = new AtomicInteger(0);
		availableTargets.forEach(t -> inv.setItem(i.getAndIncrement(), t.getRepresentativeItem()));
		return inv;
	}
}
