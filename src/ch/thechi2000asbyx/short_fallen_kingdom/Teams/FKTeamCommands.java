package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import ch.thechi2000asbyx.common.Coordinates;
import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class FKTeamCommands implements CommandExecutor
{
	@Override
	public boolean onCommand(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String s, String[] strings)
	{
		switch (strings[0])
		{
			case "create":
				return create(commandSender, strings);
			case "terminate":
				return terminate(commandSender, strings);
			case "revive":
				return revive(commandSender, strings);
			case "addPlayer":
				return addPlayer(commandSender, strings);
			case "removePlayer":
				return removePlayer(commandSender, strings);
			case "setBaseLocation":
				return setBaseLocation(commandSender, strings);
			case "getBaseLocation":
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
	
	private static boolean create(CommandSender commandSender, String[] strings)
	{
		if (!commandSender.isOp())
		{
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.registerNewTeam(strings[1]);
		if (team == null)
		{
			commandSender.sendMessage(ChatColor.RED + "This team already exists");
			return true;
		}
		
		Main.broadcast("Team " + team.getName() + " successfully created");
		return true;
	}
	
	private static boolean terminate(CommandSender commandSender, String[] strings)
	{
		if (!commandSender.isOp())
		{
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		team.eliminate();
		
		Main.broadcast("Team " + team.getName() + " successfully terminated");
		return true;
	}
	
	private static boolean revive(CommandSender commandSender, String[] strings)
	{
		if (!commandSender.isOp())
		{
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		team.revive();
		
		Main.broadcast("Team " + team.getName() + " successfully revived");
		return true;
	}
	
	private static boolean addPlayer(CommandSender commandSender, String[] strings)
	{
		if (!commandSender.isOp())
		{
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 3) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		if (team == null)
		{
			commandSender.sendMessage(ChatColor.RED + "Unknown team");
			return true;
		}
		
		Player player = Bukkit.getPlayer(strings[2]);
		if (player == null)
		{
			commandSender.sendMessage(ChatColor.RED + "Unknown player");
			return true;
		}
		
		if (!team.addPlayer(player))
			commandSender.sendMessage(ChatColor.RED + "This player already has a team");
		
		Main.broadcast(String.format("%s added to the team %s", player.getName(), team.getName()));
		return true;
	}
	
	private static boolean removePlayer(CommandSender commandSender, String[] strings)
	{
		if (!commandSender.isOp())
		{
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 2) return false;
		
		Player player = Bukkit.getPlayer(strings[1]);
		if (player == null)
		{
			commandSender.sendMessage(ChatColor.RED + "Unknown player");
			return true;
		}
		
		if (!FKTeam.makeOrphan(player))
			commandSender.sendMessage(ChatColor.RED + "The player has no team");
		
		Main.broadcast(String.format("%s removed from his team", player.getName()));
		return true;
	}
	
	private static boolean setBaseLocation(CommandSender commandSender, String[] strings)
	{
		if (!commandSender.isOp())
		{
			Main.broadcast(ChatColor.RED + "You do not have the permission to use this command");
			return true;
		}
		
		if (strings.length != 5) return false;
		
		FKTeam team = FKTeam.getTeam(strings[1]);
		if (team == null)
		{
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

	private static boolean getBaseLocation(CommandSender commandSender, String[] strings)
	{
		if (strings.length == 1 && commandSender instanceof Player)
		{
			FKTeam team = FKTeam.getTeam((Player) commandSender);
			if (team == null)
			{
				commandSender.sendMessage(ChatColor.RED + "You have no team. You are alone. How sad...");
				return true;
			}

			commandSender.sendMessage(String.format("Your base is located at %s", team.getBaseCenter()));
			return true;
		}
		else if (strings.length == 2)
		{
			FKTeam team = FKTeam.getTeam(strings[1]);
			if (team == null)
			{
				commandSender.sendMessage(ChatColor.RED + "Unknown team");
				return true;
			}

			commandSender.sendMessage(String.format("The base of %s is located at %s", team.getName(), team.getBaseCenter()));
		}
		else
			return false;
		return true;
	}

	private static boolean teams(CommandSender commandSender, String[] strings)
	{
		if (strings.length != 1) return false;

		StringBuilder sb = new StringBuilder().append("[");
		FKTeam.allTeams.forEach(t -> sb.append(String.format("'%s'", t.getName())));
		commandSender.sendMessage(sb.append("]").toString());

		return true;
	}

	private static boolean players(CommandSender commandSender, String[] strings)
	{
		if (strings.length != 2) return false;

		FKTeam team = FKTeam.getTeam(strings[1]);
		if (team == null)
		{
			commandSender.sendMessage(ChatColor.RED + "Unknown team");
			return true;
		}

		StringBuilder sb = new StringBuilder().append("[");
		Bukkit.getOnlinePlayers().stream().filter(team::contains).forEach(t -> sb.append(String.format("'%s'", t.getName())));
		commandSender.sendMessage(sb.append("]").toString());

		return true;
	}

	private static boolean help(CommandSender commandSender, String[] strings)
	{
		if (strings.length == 1)
		{
			commandSender.sendMessage("Choose a command for help :");
			HelpStrings.ALL.stream().filter(h -> !h.opRequired || commandSender.isOp()).forEach(helpStrings ->
			{
				TextComponent textComponent = new TextComponent(helpStrings.commandName + " [Get help]");
				textComponent.setColor(ChatColor.GREEN);
				textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fkteam help " + helpStrings.commandName));
				commandSender.spigot().sendMessage(textComponent);
			});
		}
		else if (strings.length == 2)
		{
			HelpStrings helpStrings = HelpStrings.ALL.stream().filter(h -> h.commandName.equals(strings[1])).findFirst().orElse(null);

			if (helpStrings == null)
			{
				commandSender.sendMessage(ChatColor.RED + "Unknown command");
				return true;
			}

			commandSender.sendMessage("Help about /fkteam " + helpStrings.commandName + ": ");

			Arrays.stream(helpStrings.argumentsDescriptions).forEach(ad ->
			{
				commandSender.sendMessage(ad.description + ": " + (ad.argumentsCount == 0 ? "No" : ad.argumentsCount) + " argument" + (ad.argumentsCount <= 1 ? "" : "s"));
				if (ad.argumentsCount != 0) Arrays.stream(ad.argumentsHelp).forEach(s -> commandSender.sendMessage("  - " + s));
			});
		}
		else
			return false;
		return true;
	}

	private enum HelpStrings
	{
		HELP(false, "help", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Display the list of commands", 0, null),
						new ArgumentsDescription("Display the help for a given command", 1, new String[]{ "A command to get help about" })
				}),
		CREATE(true, "create", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Create a new team", 1, new String[]{ "The name of the team" })
				}),
		REVIVE(true, "revive", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Revive an eliminated team", 1, new String[]{ "The name of the team" })
				}),
		TERMINATE(true, "terminate", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Eliminate a team", 1, new String[]{ "The name of the team" })
				}),
		ADD_PLAYER(true, "addPlayer", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Add a player to a team", 2, new String[]{ "The name of the team", "The name of the player" })
				}),
		REMOVE_PLAYER(true, "removePlayer", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Remove a player from his team", 1, new String[]{ "The name of the team" })
				}),
		SET_BASE_LOCATION(true, "setBaseLocation", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Set the location of the base of a team", 4, new String[]
								{ "The name of the team",
								  "The x coordinates of the base",
								  "The y coordinates of the base",
								  "The z coordinates of the base"
								})
				}),
		GET_BASE_LOCATION(false, "getBaseLocation", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Prints the position of your base", 0, null),
						new ArgumentsDescription("Prints the position of a team's base", 1, new String[]{ "The name of the team" })
				}),
		TEAMS(false, "teams", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Prints all the names of the teams", 0, null)
				}),
		PLAYERS(false, "players", new ArgumentsDescription[]
				{
						new ArgumentsDescription("Prints all the names of the player from a team", 1, new String[]{ "The name of the team" })
				});

		HelpStrings(boolean opRequired, String commandName, ArgumentsDescription[] argumentsDescriptions)
		{
			this.opRequired            = opRequired;
			this.argumentsDescriptions = argumentsDescriptions;
			this.commandName           = commandName;
		}

		public final boolean opRequired;

		public final String commandName;
		public final ArgumentsDescription[] argumentsDescriptions;
		public static final List<HelpStrings> ALL = Arrays.stream(values()).collect(Collectors.toList());

	}
	private static class ArgumentsDescription
	{

		public final String description;
		public final int argumentsCount;
		public final String[] argumentsHelp;
		public ArgumentsDescription(String description, int argumentsCount, String[] argumentsHelp)
		{
			this.description    = description;
			this.argumentsCount = argumentsCount;
			this.argumentsHelp  = argumentsHelp;
		}

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

