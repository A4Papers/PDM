package a4.papers.privatedeath.message;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CmdExecutor implements Listener, CommandExecutor
	{

		Main main;

		public CmdExecutor(Main instance)
			{
				main = instance;
			}

		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
			{
				if (cmd.getName().equalsIgnoreCase("pdm"))
					{
						if (args.length == 0)
							{
								sender.sendMessage(ChatColor.AQUA + "=------------------+ " + ChatColor.BLUE + "Private Death" + ChatColor.AQUA
										+ " +------------------=");
								sender.sendMessage(ChatColor.DARK_AQUA + "Available commands" + ChatColor.WHITE + ":");
								sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "info  " + ChatColor.WHITE + "-"
										+ ChatColor.YELLOW + "Shows plugin info.");
								if (sender.hasPermission("pdm.toggle"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "toggle  " + ChatColor.WHITE
												+ "-" + ChatColor.YELLOW + " Toggles seeing private death messages.");
									}
								if (sender.hasPermission("pdm.log") || sender.hasPermission("pdm.toggle"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "log  " + ChatColor.WHITE + "-"
												+ ChatColor.YELLOW + " View the kill log.");
									}
								if (sender.hasPermission("pdm.reload"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "reload  " + ChatColor.WHITE
												+ "-" + ChatColor.YELLOW + " Reload the config file.");
									}
								if (sender.hasPermission("pdm.config"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "set  " + ChatColor.WHITE + "-"
												+ ChatColor.YELLOW + " Set config.");
									}
								if (sender.hasPermission("pdm.total"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "total  " + ChatColor.WHITE
												+ "-" + ChatColor.YELLOW + " Total deaths.");
									}
								sender.sendMessage(ChatColor.AQUA + "=---------------------------------------------------=");
								return true;
							}
						if (args[0].equalsIgnoreCase("set"))
							{
								if (sender.hasPermission("pdm.config"))
									{
										if (args.length == 1 || (args.length == 2))
											{
												sender.sendMessage(ChatColor.BLUE + "Usage: /pdm set " + ChatColor.YELLOW
														+ "Radiuson, Radiusarea, CmdBlock, CmdBlockTime, ColourKiller," + " ColourKilled, ColourBy, PrefixColour, PrefixText"
														+ ChatColor.GOLD + " (value)");
												return true;
											}
										else
											{
												String arg = args[2];

												if (args[1].equalsIgnoreCase("CmdBlock"))
													{
														if (arg.equals("true"))
															{
																main.getConfig().set("CmdBlock.Use", true);
																main.saveConfig();
																main.reloadConfig();
																main.toggled = main.getConfig().getBoolean("CmdBlock.Use", true);
																sender.sendMessage(ChatColor.RED + "CmdBlock " + ChatColor.AQUA + "is now set to " + ChatColor.YELLOW + "true");
															}
														else if (arg.equals("false"))
															{
																main.getConfig().set("CmdBlock.Use", false);
																main.saveConfig();
																main.reloadConfig();
																main.toggled = main.getConfig().getBoolean("CmdBlock.Use", false);
																sender.sendMessage(ChatColor.RED + "CmdBlock " + ChatColor.AQUA + "is now set to " + ChatColor.YELLOW + "false");

															}
														else
															{
																sender.sendMessage(ChatColor.RED + "Error: True/false only!");
															}
													}
												if (args[1].equalsIgnoreCase("Radiuson"))
													{
														if (arg.equals("true"))
															{
																main.getConfig().set("radius.on", true);
																main.saveConfig();
																main.reloadConfig();
																main.toggled = main.getConfig().getBoolean("radius.on", true);
																sender.sendMessage(ChatColor.RED + "Radiuson " + ChatColor.AQUA + "is now set to " + ChatColor.YELLOW + "true");
															}
														else if (arg.equals("false"))
															{
																main.getConfig().set("radius.on", false);
																main.saveConfig();
																main.reloadConfig();
																main.toggled = main.getConfig().getBoolean("radius.on", false);
																sender.sendMessage(ChatColor.RED + "Radiuson " + ChatColor.AQUA + "is now set to " + ChatColor.YELLOW + "false");

															}
														else
															{
																sender.sendMessage(ChatColor.RED + "Error: True/false only!");
															}
													}
												else if (args[1].equalsIgnoreCase("CmdBlockTime"))
													{
														int num = 0;
														try
															{
																num = Integer.valueOf(arg);
																main.getConfig().set("CmdBlock.blockTime", num);
																sender.sendMessage(ChatColor.RED + "CmdBlockTime " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
															}
														catch (NumberFormatException e)
															{
																sender.sendMessage(ChatColor.RED + "Error: Numbers only!");
																main.getConfig().set("CmdBlock.blockTime", 5);
															}
														main.saveConfig();
														main.reloadConfig();
													}
												else if (args[1].equalsIgnoreCase("radiusarea"))
													{
														int num = 0;
														try
															{
																num = Integer.valueOf(arg);
																main.getConfig().set("radius.radius", num);
																sender.sendMessage(ChatColor.RED + "Radiusarea " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
															}
														catch (NumberFormatException e)
															{
																sender.sendMessage(ChatColor.RED + "Error: Numbers only!");
																main.getConfig().set("radius.radius", 25);
															}
														main.saveConfig();
														main.reloadConfig();
													}
												else if (args[1].equalsIgnoreCase("ColourKiller"))
													{
														main.getConfig().set("Colour.Killer", arg);
														main.saveConfig();
														main.reloadConfig();
														sender.sendMessage(ChatColor.RED + "ColourKiller " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
													}
												else if (args[1].equalsIgnoreCase("ColourKilled"))
													{
														main.getConfig().set("Colour.Killed", arg);
														main.saveConfig();
														main.reloadConfig();
														sender.sendMessage(ChatColor.RED + "ColourKiller " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
													}
												else if (args[1].equalsIgnoreCase("ColourBy"))
													{
														main.getConfig().set("Colour.By", arg);
														main.saveConfig();
														main.reloadConfig();
														sender.sendMessage(ChatColor.RED + "ColourBy " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
													}
												else if (args[1].equalsIgnoreCase("PrefixColour"))
													{
														main.getConfig().set("Prefix.Colour", arg);
														main.saveConfig();
														main.reloadConfig();
														sender.sendMessage(ChatColor.RED + "PrefixColour " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
													}
												else if (args[1].equalsIgnoreCase("PrefixText"))
													{
														String Br = "[" + arg + "]";
														main.getConfig().set("Prefix.Text", Br);
														main.saveConfig();
														main.reloadConfig();
														sender.sendMessage(ChatColor.RED + "PrefixText " + ChatColor.AQUA + "is now set as " + ChatColor.YELLOW + arg);
													}
												else if (args[1].equalsIgnoreCase("Help"))
													{
														return true;
													}
											}
									}
								sender.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
								return true;
							}
						if (args[0].equalsIgnoreCase("total"))
							{
								if (sender.hasPermission("pdm.total"))
									{
										ArrayList<String> loglist = (ArrayList<String>) main.logger.getStringList("log");
										int i = loglist.size();
										sender.sendMessage(ChatColor.LIGHT_PURPLE + "Total pvp kills" + ChatColor.WHITE + ": " + ChatColor.RED + i);
										return true;
									}
								sender.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
								return true;
							}
						if (args[0].equalsIgnoreCase("update"))
							{
								if (sender.hasPermission("pdm.update"))
									{
										if (main.update == true)
											{
												try
													{
														sender.sendMessage(ChatColor.AQUA + "Checking for update...");
														main.nVersion = main.update(main.cVersion);
														if (main.nVersion > main.cVersion)
															{
																sender.sendMessage(ChatColor.AQUA + "Latest Version: " + ChatColor.YELLOW + main.nVersionT + ChatColor.AQUA
																		+ " is out! You are still have version: " + ChatColor.YELLOW + main.thisVersionT);
																sender.sendMessage(ChatColor.AQUA + "Get the update at:" + ChatColor.YELLOW
																		+ " http://dev.bukkit.org/bukkit-plugins/privatedeath/");
																return true;

															}
														else if (main.cVersion > main.nVersion)
															{
																sender.sendMessage(ChatColor.AQUA + "You are useing a newer version: " + ChatColor.YELLOW + main.thisVersionT);
																return true;

															}
														else
															{
																sender.sendMessage(ChatColor.AQUA + "No new version available");
																return true;
															}
													}
												catch (Exception e)
													{
													}

											}
										else
											{
												sender.sendMessage(ChatColor.GOLD + "Update checking is disabled.");
												return true;

											}
									}
								else
									{
										sender.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
										return true;
									}
							}
						if (args[0].equalsIgnoreCase("info"))
							{
								String v = main.getDescription().getVersion();
								sender.sendMessage(ChatColor.AQUA + "=------------------+ " + ChatColor.BLUE + "Private Death" + ChatColor.AQUA
										+ " +------------------=");
								sender.sendMessage(ChatColor.GREEN + "Version" + ChatColor.WHITE + ": " + ChatColor.YELLOW + v);
								sender.sendMessage(ChatColor.GREEN + "Creator" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "A4_Papers" + ChatColor.WHITE + ", "
										+ ChatColor.YELLOW + "_SaBrEWoLf");
								sender.sendMessage(ChatColor.GREEN + "Tester" + ChatColor.WHITE + ": " + ChatColor.YELLOW + "Joshylake");
								sender.sendMessage(ChatColor.LIGHT_PURPLE + "Thanks for using our plugin!");
								sender.sendMessage(ChatColor.AQUA + "=---------------------------------------------------=");
								return true;
							}
						if (args[0].equalsIgnoreCase("reload"))
							{
								if (sender.hasPermission("pdm.reload"))
									{
										main.reloadConfig();
										main.saveConfig();
										sender.sendMessage(ChatColor.GREEN + "Configuration Reloaded!");
										return true;
									}
								sender.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
								return true;
							}
						if (args[0].equalsIgnoreCase("log"))
							{

								ArrayList<String> loglist = (ArrayList<String>) main.logger.getStringList("log");

								if (sender.hasPermission("pdm.log") || sender.hasPermission("pdm.toggle"))
									{
										if (args.length < 2)
											{
												sender.sendMessage(ChatColor.GOLD + "Please enter a page number.");
												return true;
											}

										int arg = 0;
										try
											{
												arg = Integer.valueOf(args[1]);

											}
										catch (NumberFormatException e)
											{
												sender.sendMessage(ChatColor.RED + "Error: Numbers only!");
												return true;
											}

										int i = loglist.size();
										int total = i / 5;
										int num = arg;

										if (i < 5)
											{
												sender.sendMessage(ChatColor.RED + "y u do dis?");
												return true;
											}

										else if (args[1].equals("0"))
											{
												String first = loglist.get(i - 5);
												String second = loglist.get(i - 4);
												String third = loglist.get(i - 3);
												String fourth = loglist.get(i - 2);
												String fifth = loglist.get(i - 1);
												String l5 = fifth.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l4 = fourth.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l3 = third.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l2 = second.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l1 = first.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												sender.sendMessage(ChatColor.LIGHT_PURPLE + "=------------------+ " + ChatColor.BLUE + "Private Death"
														+ ChatColor.LIGHT_PURPLE + " +------------------=");
												sender.sendMessage(ChatColor.WHITE + "Log" + ":");
												sender.sendMessage(l5 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l4 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l3 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l2 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l1 + " ");
												sender.sendMessage(ChatColor.LIGHT_PURPLE + "=---------------------------------------------------=");
												sender.sendMessage("Page: " + ChatColor.GRAY + "" + arg + "/" + total + "");
												return true;
											}
										else if (arg > total)
											{
												sender.sendMessage(ChatColor.GOLD + "Page does not exist! Try upto page" + ChatColor.WHITE + ": " + ChatColor.GRAY + total
														+ ChatColor.GOLD + ".");
												return true;
											}
										else
											{
												String first = loglist.get(i - 5 * num);
												String second = loglist.get(i - 4 * num);
												String third = loglist.get(i - 3 * num);
												String fourth = loglist.get(i - 2 * num);
												String fifth = loglist.get(i - 1 * num);
												String l5 = fifth.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l4 = fourth.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l3 = third.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l2 = second.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												String l1 = first.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
												sender.sendMessage(ChatColor.LIGHT_PURPLE + "=------------------+ " + ChatColor.BLUE + "Private Death"
														+ ChatColor.LIGHT_PURPLE + " +------------------=");
												sender.sendMessage(ChatColor.WHITE + "Log" + ":");
												sender.sendMessage(l5 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l4 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l3 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l2 + " ");
												sender.sendMessage(ChatColor.WHITE + "- - -");
												sender.sendMessage(l1 + " ");
												sender.sendMessage(ChatColor.LIGHT_PURPLE + "=---------------------------------------------------=");
												sender.sendMessage("Page: " + ChatColor.GRAY + "" + arg + "/" + total + "");
												return true;
											}
									}
								sender.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
								return true;

							}
						if (args[0].equalsIgnoreCase("toggle"))
							{
								if ((sender instanceof Player))
									{
										if (sender.hasPermission("pdm.toggle"))
											{
												if (!main.pdmtoggle.contains(sender.getName()))
													{
														sender.sendMessage(ChatColor.RED + "You can no longer see private death messages!");
														main.pdmtoggle.add(sender.getName());
														return true;
													}
												sender.sendMessage(ChatColor.GREEN + "You can now see private death messages!");
												main.pdmtoggle.remove(sender.getName());
												return true;
											}
										sender.sendMessage(ChatColor.GOLD + "You do not have permission to use this command.");
										return true;
									}
								sender.sendMessage("Player only.");
								return true;
							}
						else
							{
								sender.sendMessage(ChatColor.AQUA + "=------------------+ " + ChatColor.BLUE + "Private Death" + ChatColor.AQUA
										+ " +------------------=");
								sender.sendMessage(ChatColor.DARK_AQUA + "Available commands" + ChatColor.WHITE + ":");
								sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "info  " + ChatColor.WHITE + "-"
										+ ChatColor.YELLOW + "Shows plugin info.");
								if (sender.hasPermission("pdm.toggle"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "toggle  " + ChatColor.WHITE
												+ "-" + ChatColor.YELLOW + " Toggles seeing private death messages.");
									}
								if (sender.hasPermission("pdm.log") || sender.hasPermission("pdm.toggle"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "log  " + ChatColor.WHITE + "-"
												+ ChatColor.YELLOW + " View the kill log.");
									}
								if (sender.hasPermission("pdm.reload"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "reload  " + ChatColor.WHITE
												+ "-" + ChatColor.YELLOW + " Reload the config file.");
									}
								if (sender.hasPermission("pdm.config"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "set  " + ChatColor.WHITE + "-"
												+ ChatColor.YELLOW + " Set config.");
									}
								if (sender.hasPermission("pdm.total"))
									{
										sender.sendMessage(ChatColor.DARK_PURPLE + "  -  " + ChatColor.GREEN + "/pdm " + ChatColor.RED + "total  " + ChatColor.WHITE
												+ "-" + ChatColor.YELLOW + " Total deaths.");
									}
								sender.sendMessage(ChatColor.AQUA + "=---------------------------------------------------=");
								return true;
							}
					}
				return false;
			}
	}
