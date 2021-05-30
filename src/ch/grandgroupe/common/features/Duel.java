package ch.grandgroupe.common.features;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Duel extends AbstractListener
{
	Player player1, player2;
	
	public Duel() {
	}
	
	@Override
	public void enable() {
		super.enable();
		
		if (Bukkit.getOnlinePlayers().size() != 2) return;
		
		Player[] players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
		player1 = players[0];
		player2 = players[1];
	}
	
	@EventHandler
	public void updateCompasses(PlayerMoveEvent event) {
		if (isDisabled()) return;
		
		player1.setCompassTarget(player2.getLocation());
		player2.setCompassTarget(player1.getLocation());
	}
}
