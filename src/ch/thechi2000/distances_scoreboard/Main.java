package ch.thechi2000.distances_scoreboard;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin
{
	DistanceBoardsManager manager;
	
	@Override
	public void onEnable()
	{
		manager = new DistanceBoardsManager();
		getServer().getPluginManager().registerEvents(manager, this);
	}
	
	
	@Override
	public void onDisable()
	{
		super.onDisable();
	}
}
