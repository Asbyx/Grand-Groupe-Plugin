package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.common.Coordinates;
import ch.thechi2000asbyx.short_fallen_kingdom.Commands.Commands;
import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Objects;

public class FKTeamCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, String[] strings) {
		switch (strings[0].toLowerCase()) {
			case "create":
				return create(commandSender, strings);
			case "terminate":
				return terminate(commandSender, strings);
			case "revive":
				return revive(commandSender, strings);
			case "addplayer":
				return addPlayer(commandSender, strings);
			case "removeplayer":
				return removePlayer(commandSender, strings);
			case "setbaselocation":
				return setBaseLocation(commandSender, strings);
			case "getbaselocation":
				return getBaseLocation(commandSender, strings);
			case "teams":
				return teams(commandSender, strings);
			case "players":
				return players(commandSender, strings);
			case "help":
				return help(commandSender, strings);
		}
		
		return false;
	}
	
	private static boolean create(CommandSender commandSender, String[] strings) {
		if (!commandSender.isOp()) {
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.registerNewTeam(strings[1]);
		if (team == null) {
			commandSender.sendMessage(ChatColor.RED + "This team already exists");
			return true;
		}
		
		Main.broadcast("Team " + team.getName() + " successfully created");
		return true;
	}
	private static boolean terminate(CommandSender commandSender, String[] strings) {
		if (!commandSender.isOp()) {
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		team.eliminate();
		
		Main.broadcast("Team " + team.getName() + " successfully terminated");
		return true;
	}
	private static boolean revive(CommandSender commandSender, String[] strings) {
		if (!commandSender.isOp()) {
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		team.revive();
		
		Main.broadcast("Team " + team.getName() + " successfully revived");
		return true;
	}
	private static boolean addPlayer(CommandSender commandSender, String[] strings) {
		if (!commandSender.isOp()) {
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 3) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		if (team == null) {
			commandSender.sendMessage(ChatColor.RED + "Unknown team");
			return true;
		}
		
		Player player = Bukkit.getPlayer(strings[2]);
		if (player == null) {
			commandSender.sendMessage(ChatColor.RED + "Unknown player");
			return true;
		}
		
		if (!team.addPlayer(player))
			commandSender.sendMessage(ChatColor.RED + "This player already has a team");
		
		Main.broadcast(String.format("%s added to the team %s", player.getName(), team.getName()));
		return true;
	}
	private static boolean removePlayer(CommandSender commandSender, String[] strings) {
		if (!commandSender.isOp()) {
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		Player player = Bukkit.getPlayer(strings[1]);
		if (player == null) {
			commandSender.sendMessage(ChatColor.RED + "Unknown player");
			return true;
		}
		
		if (!FKTeam.makeOrphan(player))
			commandSender.sendMessage(ChatColor.RED + "The player has no team");
		
		Main.broadcast(String.format("%s removed from his team", player.getName()));
		return true;
	}
	private static boolean setBaseLocation(CommandSender commandSender, String[] strings) {
		if (!commandSender.isOp()) {
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 5) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		if (team == null) {
			commandSender.sendMessage(ChatColor.RED + "Unknown team");
			return false;
		}
		
		team.setBaseCenter(new Location(
				Bukkit.getWorld("world"),
				Integer.parseInt(strings[2]),
				Integer.parseInt(strings[3]),
				Integer.parseInt(strings[4])
		));
		
		createBase(team.getBaseCenter());
		
		Main.broadcast("Base location of " + team.getName() + " set to " + team.getBaseCenter());
		return true;
	}
	private static boolean getBaseLocation(CommandSender commandSender, String[] strings) {
		if (strings.length == 1 && commandSender instanceof Player) {
			FKTeam team = FKTeam.getTeam((Player) commandSender);
			if (team == null) {
				commandSender.sendMessage(ChatColor.RED + "You have no team. You are alone. How sad...");
				return true;
			}
			
			commandSender.sendMessage(String.format("Your base is located at %s", team.getBaseCenter()));
			return true;
		}
		else if (strings.length == 2) {
			FKTeam team = FKTeam.getTeam(strings[1]);
			if (team == null) {
				commandSender.sendMessage(ChatColor.RED + "Unknown team");
				return true;
			}
			
			commandSender.sendMessage(String.format("The base of %s is located at %s", team.getName(), team.getBaseCenter()));
		}
		else
			return false;
		return true;
	}
	private static boolean teams(CommandSender commandSender, String[] strings) {
		if (strings.length != 1) return false;
		
		StringBuilder sb = new StringBuilder().append("[");
		FKTeam.allTeams.forEach(t -> sb.append(String.format("'%s'", t.getName())));
		commandSender.sendMessage(sb.append("]").toString());
		
		return true;
	}
	private static boolean players(CommandSender commandSender, String[] strings) {
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		if (team == null) {
			commandSender.sendMessage(ChatColor.RED + "Unknown team");
			return true;
		}
		
		StringBuilder sb = new StringBuilder().append("[");
		Bukkit.getOnlinePlayers().stream().filter(team::contains).forEach(t -> sb.append(String.format("'%s'", t.getName())));
		commandSender.sendMessage(sb.append("]").toString());
		
		return true;
	}
	private static boolean help(CommandSender commandSender, String[] strings) {
		if (strings.length == 1) {
			commandSender.sendMessage("Choose a command for help :");
			Commands.ALL.stream().filter(h -> !h.opRequired || commandSender.isOp()).forEach(helpStrings ->
			{
				TextComponent textComponent = new TextComponent(helpStrings.commandName + " [Get help]");
				textComponent.setColor(ChatColor.GREEN);
				textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fkteam help " + helpStrings.commandName));
				commandSender.spigot().sendMessage(textComponent);
			});
			commandSender.sendMessage("\n");
		}
		else if (strings.length == 2) {
			Commands helpStrings = Commands.ALL.stream().filter(h -> h.commandName.equals(strings[1])).findFirst().orElse(null);
			
			if (helpStrings == null) {
				commandSender.sendMessage(ChatColor.RED + "Unknown command");
				return true;
			}
			
			commandSender.sendMessage("Help about /fkteam " + helpStrings.commandName + ": ");
			
			helpStrings.argumentsList.forEach(al ->
			{
				int size = al.arguments.stream().mapToInt(a -> a.ignoredInHelp ? 0 : 1).sum();
				commandSender.sendMessage(al.description + ": " + (size == 0 ? "No" : size) + " argument" + (size <= 1 ? "" : "s"));
				if (size != 0) al.arguments.stream().filter(a -> !a.ignoredInHelp).forEach(a -> commandSender.sendMessage("  - " + a.description));
				commandSender.sendMessage("\n");
			});
		}
		else
			return false;
		return true;
	}
	
	private static void createBase(Coordinates location) {
		World world = Objects.requireNonNull(Bukkit.getWorld("world"));
		
		for (int z = -7; z <= 7; z += 14) {
			for (int x = -7; x <= 7; x += 2) {
				world.getBlockAt(new Location(world, location.x + x, world.getHighestBlockYAt(location.x + x, location.z + z), location.z + z)).setType(Material.COBBLESTONE);
			}
		}
		for (int x = -7; x <= 7; x += 14) {
			for (int z = -7; z <= 7; z += 2) {
				world.getBlockAt(new Location(world, location.x + x, world.getHighestBlockYAt(location.x + x, location.z + z), location.z + z)).setType(Material.COBBLESTONE);
			}
		}
	}
}

