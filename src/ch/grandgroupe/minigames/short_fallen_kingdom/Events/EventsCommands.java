package ch.grandgroupe.minigames.short_fallen_kingdom.Events;

import ch.grandgroupe.common.Main;
import ch.grandgroupe.common.worlds.Worlds;
import ch.grandgroupe.minigames.short_fallen_kingdom.Scoreboards.*;
import ch.grandgroupe.minigames.short_fallen_kingdom.Teams.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class EventsCommands implements CommandExecutor
{
	private int eventsId;
	private EventsManager eventsManager;
	private final FlagEvents flagEvents;
	private final BuildEvents buildEvents;
	private List<FKScoreboard> scoreboards;
	private List<Compass> compasses;
	
	public EventsCommands() {
		eventsId      = -1;
		eventsManager = null;
		
		flagEvents = new FlagEvents();
		flagEvents.disable();
		Bukkit.getPluginManager().registerEvents(flagEvents, Main.PLUGIN);
		
		buildEvents = new BuildEvents();
		buildEvents.disable();
		Bukkit.getPluginManager().registerEvents(buildEvents, Main.PLUGIN);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[1].toLowerCase()) {
			case "startgame":
				startGame(sender, Arrays.copyOfRange(args, 1, args.length));
				break;
			
			case "parameters":
			case "getparmaters":
			case "getgameparameters":
				getGameParameters(sender);
				break;
			
			case "stopgame":
				stopGame(sender);
				break;
			
			case "middlechest":
				EventsManager.initMiddleChest(Objects.requireNonNull(Worlds.OVERWORLD.get()));
				break;
			
			case "help":
				help(sender);
				break;
		}
		return true;
	}
	
	private void startGame(CommandSender sender, String[] args) {
		if (sender.isOp()) {
			if (eventsId != -1) return;
			try {
				if (args.length != 7 && args.length != 2) throw new IllegalArgumentException();

				if (args[1].equals("default")) eventsManager = new EventsManager();
				else eventsManager = new EventsManager(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));

				Bukkit.getPluginManager().registerEvents(eventsManager, Main.PLUGIN);
				eventsId = Main.SCHEDULER.runTaskTimer(Main.PLUGIN, () -> eventsManager.update(Objects.requireNonNull(Worlds.OVERWORLD.get()).getGameTime()), 0, 1).getTaskId();

				flagEvents.enable();
				buildEvents.enable();
				
				scoreboards = Bukkit.getOnlinePlayers().stream().map(p -> new FKScoreboard(p, eventsManager)).collect(Collectors.toList());
				scoreboards.forEach(FKScoreboard::start);
				
				compasses = Bukkit.getOnlinePlayers().stream().map(p -> new Compass(p, eventsManager)).collect(Collectors.toList());
				
				Bukkit.getOnlinePlayers().stream().filter(p -> FKTeam.getTeam(p) != null).forEach(this::initPlayer);
				FKTeam.allTeams.forEach(FKTeam::init);
			}
			catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Invalid arguments:\n" +
						"<number of seconds between each middle chest>\n" +
						"<minimum number of seconds between each random chest>\n" +
						"<maximum number of seconds between each random chest>\n" +
						"<number of nights between each blood night>\n" +
						"<radius of random events, center is the server spawn location> (must include both bases, or stop just in front of them)\n" +
						"<day where PvP is allowed>");
			}
			catch (IllegalArgumentException i) {
				sender.sendMessage(String.format(ChatColor.RED + "Invalid number of parameters (%s).", args.length));
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "You are not allowed to use this command");
		}
	}
	private void getGameParameters(CommandSender sender) {
		if (eventsManager == null) sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
		else sender.sendMessage(eventsManager.getGameParameters());
	}
	private void stopGame(CommandSender sender) {
		if (sender.isOp()) {
			if (eventsId == -1) {
				sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
				return;
			}
			Main.SCHEDULER.cancelTask(eventsId);
			eventsId      = -1;
			eventsManager = null;
			
			flagEvents.disable();
			buildEvents.disable();
			
			if (scoreboards != null) scoreboards.forEach(FKScoreboard::stop);
			if (compasses != null) compasses.forEach(Compass::disable);
			
			Main.broadcast("The game is over !");
		}
	}
	private void help(CommandSender sender) {
		if (sender.isOp()) sender.sendMessage(
				"startgame : start the game (use default as argument to use default parameters)\n" +
						"stopgame : stop the current game"
		);
		sender.sendMessage("parameters : display the current game parameters\n" + "middlechest : show the coordinates of the middle chest");
	}
	private void initPlayer(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		player.setExp(0);
		player.setLevel(0);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
		
		player.getInventory().addItem(new ItemStack(Material.BREAD, 5), new ItemStack(Material.CLOCK), new ItemStack(Material.COMPASS));
		
		player.setInvulnerable(true);
		player.teleport(FKTeam.getTeam(player).getBaseCenter().add(0, 30, 0).toOverworldLocation());
		
		Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN, () -> player.setInvulnerable(false), 200);
	}
}
