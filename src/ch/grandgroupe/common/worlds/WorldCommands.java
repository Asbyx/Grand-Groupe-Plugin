package ch.grandgroupe.common.worlds;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class WorldCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!s.equals("worlds")) return true;
		
		if (!commandSender.isOp()) {
			commandSender.sendMessage(ChatColor.RED + "You have no permission to use this command");
			return true;
		}
		
		switch (strings[0]) {
			case "tp":
				Player toTeleport;
				if (strings.length == 3)
					toTeleport = Bukkit.getPlayer(strings[2]);
				else if (commandSender instanceof Player)
					toTeleport = (Player) commandSender;
				else
					return false;
				
				if (toTeleport == null) {
					commandSender.sendMessage(ChatColor.RED + "Unable to find a target to teleport");
					return true;
				}
				
				Worlds.teleportTo(toTeleport, Worlds.Type.fromString(strings[1]));
				break;
			
			case "regenerate":
				if (strings.length == 2 || strings.length == 3) {
					WorldManager manager = Worlds.getCorrespondingWorldManager(Worlds.Type.fromString(strings[1]));
					
					if (manager == null) {
						commandSender.sendMessage(ChatColor.RED + "No corresponding world");
						return true;
					}
					
					if (!manager.canBeRegenerated()) {
						commandSender.sendMessage(ChatColor.RED + "This world cannot be regenerated");
						return true;
					}
					
					commandSender.sendMessage(ChatColor.GREEN + "Starting regeneration");
					manager.regenerate(WorldType.NORMAL,
							strings.length == 2 || Boolean.parseBoolean(strings[2]),
							() -> commandSender.sendMessage(ChatColor.GREEN + "World regenerated"));
					return true;
				}
		}
		
		return true;
	}
}
