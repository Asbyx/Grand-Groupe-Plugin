package ch.grandgroupe.common.worlds;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		if (!s.equals("worlds")) return true;
		
		switch (strings[0]) {
			case "generate":
				Worlds.OVERWORLD.regenerate(true);
				break;
				
			case "lobby":
				if (commandSender instanceof Player)
					Worlds.teleportToLobby((Player) commandSender);
				break;
				
			case "overworld":
				if (commandSender instanceof Player)
					Worlds.teleportToWorld(((Player) commandSender), Worlds.Type.NORMAL);
				break;
		}
		
		return true;
	}
}
