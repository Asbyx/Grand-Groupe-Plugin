package ch.grandgroupe.minigames.coop_defense;

import ch.grandgroupe.minigames.coop_defense.creatures.*;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CoopCommands implements CommandExecutor
{
	private EventsManager eventsManager;
	
	@Override
	public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
		if (!s.equals("coop") || strings.length != 1 || !(commandSender instanceof Player)) return true;
		
		switch (strings[0]) {
			case "start":
				((CraftWorld) ((Player) commandSender).getWorld()).addEntity(new CreatureToDefend(((Player) commandSender).getLocation()), CreatureSpawnEvent.SpawnReason.COMMAND);
				eventsManager = new EventsManager(3000);
				break;
			
			default:
				return false;
		}
		
		return true;
	}
}
