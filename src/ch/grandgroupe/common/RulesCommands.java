package ch.grandgroupe.common;

import ch.grandgroupe.common.features.*;
import org.bukkit.*;
import org.bukkit.command.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Class which manages all the listeners and their commands.
 */
public final class RulesCommands implements CommandExecutor
{
	private final Map<String, AbstractListener> features = new TreeMap<>();
	
	/**
	 * Default constructor: initiate all listeners, disabled by default
	 */
	public RulesCommands() {
		features.put("tntBow", new TntBow());
		features.put("nudeBow", new NudeBow());
		features.put("harvester", new Harvester());
		features.put("hook", new Hook());
		features.put("compassTargeting", new CompassTargeting());
		features.put("tomb", new Tomb());
		
		features.values().forEach(abstractListener -> {
			abstractListener.disable();
			Bukkit.getPluginManager().registerEvents(abstractListener, Main.PLUGIN);
		});
	}
	
	@SuppressWarnings("SpellCheckingInspection")
	@Override
	public boolean onCommand(CommandSender sender, @Nonnull Command command, @Nonnull String label, String[] arg) {
		if (sender.isOp()) {
			if (arg.length == 2) {
				switch (arg[0].toLowerCase()) {
					case "tntbow":
						if (arg[1].equalsIgnoreCase("true") || arg[1].equalsIgnoreCase("false")) {
							enabler(Boolean.parseBoolean(arg[1]), "tntBow", "Tnt Bow");
						}
						else {
							try {
								((TntBow) features.get("tntBow")).setPower(Float.parseFloat(arg[1]));
								Main.broadcast("Power of Tnt bow set to: " + ChatColor.GOLD + Float.parseFloat(arg[1]));
							}
							catch (NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "Invalid arguments: boolean (enabling) or float value (set the power)");
							}
						}
						break;
					
					case "nudebow":
						enabler(Boolean.parseBoolean(arg[1]), "nudeBow", "Nude Bow");
						break;
					
					case "harvester":
						if (arg[1].equalsIgnoreCase("true") || arg[1].equalsIgnoreCase("false")) {
							enabler(Boolean.parseBoolean(arg[1]), "harvester", "Harvester");
						}
						else {
							try {
								((Harvester) features.get("harvester")).setRadius(Integer.parseInt(arg[1]));
								Main.broadcast("Radius of action of the harvester set to: " + ChatColor.GOLD + Integer.parseInt(arg[1]));
							}
							catch (NumberFormatException e) {
								sender.sendMessage(ChatColor.RED + "Invalid arguments: boolean (enabling) or float value (set the radius)");
							}
						}
						break;
					
					case "hook":
						enabler(Boolean.parseBoolean(arg[1]), "hook", "Hook");
						break;
					
					case "compasstargeting":
						enabler(Boolean.parseBoolean(arg[1]), "compassTargeting", "Compass Targeting");
						break;
					
					case "tomb":
						enabler(Boolean.parseBoolean(arg[1]), "tomb", "Tomb");
						break;
				}
			}
			else
				sender.sendMessage(ChatColor.RED + "Invalid command parameters");
		}
		else
			sender.sendMessage(ChatColor.RED + "You are not allowed to use this commmand");
		return true;
	}
	
	/**
	 * Enable/Disable a feature (it has to be registered in the features map
	 *
	 * @param newValue the new state of the feature (true => enable and false => disable)
	 * @param key      the key of the feature in the map
	 * @param name     the name of the feature (to be printed in the chat)
	 */
	private void enabler(boolean newValue, String key, String name) {
		if (newValue) {
			features.get(key).enable();
			Main.broadcast(name + ChatColor.GREEN + " enabled");
		}
		else {
			features.get(key).disable();
			Main.broadcast(name + ChatColor.RED + " disabled");
		}
	}
}
