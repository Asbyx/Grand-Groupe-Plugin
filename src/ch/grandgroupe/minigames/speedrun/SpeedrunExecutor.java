package ch.grandgroupe.minigames.speedrun;

import org.bukkit.ChatColor;
import org.bukkit.command.*;


public class SpeedrunExecutor implements CommandExecutor
{
	private Speedrun speedrun;
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!s.equals("speedrun") || strings.length == 0) return true;
		
		switch (strings[0]) {
			case "startGame":
				if (commandSender.isOp() && strings.length == 2) {
					int index = Objective.strings.indexOf(strings[1].toLowerCase());
					if (index == -1) commandSender.sendMessage(ChatColor.RED + "Unknown objective");
					
					speedrun = new Speedrun(Objective.values()[index]);
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
