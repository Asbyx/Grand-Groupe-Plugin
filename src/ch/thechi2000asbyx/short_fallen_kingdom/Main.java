package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.short_fallen_kingdom.Teams.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin
{
	public static JavaPlugin PLUGIN;
	public static BukkitScheduler SCHEDULER;
	private EventsManager eventsManager;
	private int eventsId;

	@Override
	public void onEnable()
	{
		PLUGIN = this;
		SCHEDULER = getServer().getScheduler();
		eventsId = -1;
		eventsManager = null;

		Bukkit.getPluginManager().registerEvents(new FlagEvents(), this);
		Bukkit.getPluginManager().registerEvents(new BuildEvents(), this);
		getCommand("fkteam").setExecutor(new FKTeamCommands());
	}
	
	@Override
	public void onDisable()
	{
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		switch (command.getName())
		{
			case "startGame":
				if(eventsId != -1) break;
				try
				{
					if (args.length != 6 && args.length != 1) throw new NumberFormatException();
					for (String s : args)
					{
						if(!s.equals("default")) Integer.parseInt(s);
					}

					if(args[0].equals("default")) eventsManager = new EventsManager();
					else eventsManager = new EventsManager(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
					eventsId = SCHEDULER.runTaskTimer(PLUGIN, () -> eventsManager.update(getServer().getWorld("world").getGameTime()), 0, 1).getTaskId();
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage(ChatColor.RED + "Invalid arguments for command startGame: \n" +
							"<number of seconds between each middle chest>\n" +
							"<minimum number of seconds between each random chest>\n" +
							"<maximum number of seconds between each random chest>\n" +
							"<number of nights between each blood night> \n" +
							"<radius of random events, center is the server spawn location> (must include both bases, or stop just in front of them)\n" +
							"<day where PvP is allowed>" + ChatColor.WHITE);
				}
				break;

			case "getGameParameters":
				if(eventsManager == null)  sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
				else sender.sendMessage(eventsManager.getGameParameters());
				break;

			case "stopGame":
				if(eventsId == -1) {
					sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
					break;
				}
				SCHEDULER.cancelTask(eventsId);
				eventsId = -1;
				eventsManager = null;
				broadcast("The game is over !");
				break;
			
			case "middleChest":
				EventsManager.initMiddleChest(getServer().getWorld("world"));
				break;
		}
		
		return true;
	}

	public static void broadcast(String arg) {
		getPlugin(Main.class).getServer().broadcastMessage(ChatColor.BLUE + "[Server] " + arg + ChatColor.WHITE);
	}
}
