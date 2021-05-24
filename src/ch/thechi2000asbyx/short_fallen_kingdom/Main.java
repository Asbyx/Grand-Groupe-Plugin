package ch.thechi2000asbyx.short_fallen_kingdom;

import ch.thechi2000asbyx.short_fallen_kingdom.Teams.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		Bukkit.getPluginManager().registerEvents(new FlagEvents(), this);
		Bukkit.getPluginManager().registerEvents(new BuildEvents(), this);
		getCommand("team").setExecutor(new FKTeamCommands());
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
				try
				{
					if (args.length != 5) throw new NumberFormatException();
					for (String s : args)
					{
						Integer.parseInt(s);
					}
					getServer().getPluginManager().registerEvents(new EventsManager(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4])), this);
				}
				catch (NumberFormatException e)
				{
					sender.sendMessage(ChatColor.RED + "Invalid arguments for command startGame: \n" +
							"<number of seconds between each middle chest>\n" +
							"<minimum number of seconds between each random chest>\n" +
							"<maximum number of seconds between each random chest>\n" +
							"<number of nights between each blood night> \n" +
							"<radius of random events, center is the server spawn location> (must include both bases, or stop just in front of them)");
				}
				break;
			
			case "stopGame":
				//fixme
				break;
			
			case "middleChest":
				EventsManager.initMiddleChest();
				break;
		}
		
		return true;
	}
}
