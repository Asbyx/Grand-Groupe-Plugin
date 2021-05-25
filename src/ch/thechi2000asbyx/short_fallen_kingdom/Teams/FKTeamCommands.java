package ch.thechi2000asbyx.short_fallen_kingdom.Teams;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class FKTeamCommands implements CommandExecutor
{
	@Override public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
	{
		switch (strings[0])
		{
			case "create":
			{
				if (strings.length != 2) return false;
				
				FKTeam team = FKTeam.registerNewTeam(strings[1]);
				
				Bukkit.broadcastMessage("Team " + team.getName() + " successfully created");
				return true;
			}
			
			case "revive":
			{
				if (strings.length != 2) return false;
				
				FKTeam team = FKTeam.getTeam(strings[1]);
				team.revive();
				
				Bukkit.broadcastMessage("Team " + team.getName() + " successfully revived");
				return true;
			}
			
			case "addPlayer":
			{
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
				
				team.addPlayer(player);
				Bukkit.broadcastMessage(String.format("%s added to the team %s", player.getName(), team.getName()));
				return true;
			}
			
			case "setBaseLocation":
			{
				if (strings.length != 5) return false;
				
				FKTeam team = FKTeam.getTeam(strings[1]);
				if (team == null)
				{
					commandSender.sendMessage(ChatColor.RED + "Unknown team");
					return false;
				}
				
				team.setBaseLocation(new Location(
						Bukkit.getWorld("world"),
						Integer.parseInt(strings[2]),
						Integer.parseInt(strings[3]),
						Integer.parseInt(strings[4])
				));
				
				Bukkit.broadcastMessage("Base location of  " + team.getName() + " set to " + team.getBaseCenter());
				
				return true;
			}
			
			case "getBaseLocation":
			{
				if (strings.length == 1)
				{
					if (commandSender instanceof Player)
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
			}
			
			case "teams":
			{
				if (strings.length != 1) return false;
				
				StringBuilder sb = new StringBuilder().append("[");
				FKTeam.allTeams.forEach(t -> sb.append(String.format("'%s'", t.getName())));
				commandSender.sendMessage(sb.append("]").toString());
				
				return true;
			}
			
			case "players":
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
		}
		
		return false;
	}
}

