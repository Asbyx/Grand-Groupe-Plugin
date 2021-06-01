package ch.grandgroupe.minigames.trainingPack;

import ch.grandgroupe.common.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrainingExecutor implements CommandExecutor {
	int id = -1;

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if(commandSender.getServer().getOnlinePlayers().size() != 1) commandSender.sendMessage(ChatColor.RED + "Impossible de lancer un entraînement si tu n'es pas seul !");

		Player player = (Player) commandSender;

		switch (strings[0].toLowerCase()){
			case "start":
				TrainingMain main;
				if (Boolean.parseBoolean(strings[1]) || strings[1].equals("false")) main = new TrainingMain(player, Boolean.parseBoolean(strings[1])); else {
					try{
						main = new TrainingMain(player, strings[1]);
					} catch (IllegalArgumentException e){
						return false;
					}
				}
				id = Main.SCHEDULER.runTaskTimer(Main.PLUGIN, main::update, 0, 1).getTaskId();
				break;

			case "stop":
				Main.SCHEDULER.cancelTask(id);
				break;
		}
		return true;
	}
}
