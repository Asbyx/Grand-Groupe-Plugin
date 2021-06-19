package ch.grandgroupe.minigames.speedrun;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;


public class SpeedrunExecutor implements CommandExecutor
{
	private Speedrun speedrun;
	
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, String s, String[] strings) {
		if (!s.equals("speedrun") || strings.length == 0) return true;
		
		switch (strings[0]) {
			case "startGame":
				if (commandSender.isOp() && strings.length == 2) {
					Objective o = Objective.fromCommandString(strings[1]);
					if (o == null) {
						commandSender.sendMessage(ChatColor.RED + "Unknown objective");
						return true;
					}
					speedrun = new Speedrun(o);
				}
				break;
			
			case "stopGame":
				if (commandSender.isOp() && speedrun != null)
					speedrun.stop();
				break;
		}
		
		return true;
	}
}
