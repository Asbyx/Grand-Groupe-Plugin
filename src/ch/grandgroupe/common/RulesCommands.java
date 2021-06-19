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
	private final Map<String, AbstractListener> listeners = new TreeMap<>();
	
	/**
	 * Default constructor: initiate all listeners, disabled by default
	 */
	public RulesCommands() {
		listeners.put("tntBow", new TntBow());
		listeners.put("nudeBow", new NudeBow());
		listeners.put("harvester", new Harvester());
		listeners.put("hook", new Hook());
		listeners.put("compassTargeting", new CompassTargeting());
		listeners.put("tomb", new Tomb());
		
		listeners.values().forEach(abstractListener -> {
			abstractListener.disable();
			Bukkit.getPluginManager().registerEvents(abstractListener, Main.PLUGIN);
		});
	}
	
	@Override
	public boolean onCommand(CommandSender sender, @Nonnull Command command,@Nonnull String label, String[] arg) {
		if (sender.isOp()) {
			switch (arg[0].toLowerCase()) {
				case "tntbow":
					if (arg[1].equalsIgnoreCase("true") || arg[1].equalsIgnoreCase("false")) {
						enabler(Boolean.parseBoolean(arg[1]), "tntBow", "Tnt Bow");
					}
					else {
						try {
							((TntBow) listeners.get("tntBow")).setPower(Float.parseFloat(arg[1]));
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
							((Harvester) listeners.get("harvester")).setRadius(Integer.parseInt(arg[1]));
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
		return true;
	}
	
	//method to be called for basic enable/disable event: new value = enable value | index = index in the list | name = name of the Listener for the players
	private void enabler(boolean newValue, String key, String name) {
		if (newValue) {
			listeners.get(key).enable();
			enable(name);
		}
		else {
			listeners.get(key).disable();
			disable(name);
		}
	}
	
	//do not use
	private void disable(String str) {
		Main.broadcast(str + ChatColor.RED + " disabled");
	}
	
	private void enable(String str) {
		Main.broadcast(str + ChatColor.GREEN + " enabled");
	}
}
