package a4.papers.privatedeath.message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Fights implements Listener
	{
		public List<Fight> fights = new ArrayList<Fight>();

		Main main;
		ListenersClass lc;

		public Fights(Main instance)
			{
				main = instance;
			}

		public Fights(ListenersClass instance)
			{
				lc = instance;
			}

		public class Fight
			{
				private String attacker;
				private String victim;

				Fight(String attacker, String victim)
					{
						this.attacker = attacker;
						this.victim = victim;
					}

				public String getAttacker()
					{
						return attacker;
					}

				public String getVictim()
					{
						return victim;
					}
			}

		private Fight getFight(String attacker, String victim)
			{
				for (Fight f : fights)
					{
						if (f.getAttacker().equals(attacker) && f.getVictim().equals(victim))
							return f;
						if (f.getAttacker().equals(victim) && f.getVictim().equals(attacker))
							return f;
					}
				return null;
			}

		private boolean isFighting(String attacker, String victim)
			{
				return getFight(attacker, victim) != null;
			}

		@EventHandler
		public void onHit(EntityDamageByEntityEvent event)
			{
				if (!(event.getEntity() instanceof Player && event.getDamager() instanceof Player))
					return;
				String attacker = ((Player) event.getDamager()).getName();
				String victim = ((Player) event.getEntity()).getName();
				if (isFighting(attacker, victim))
					return;
				fights.add(new Fight(attacker, victim));
			}

		@EventHandler
		public void onDeath(PlayerDeathEvent event)
			{
				event.setDeathMessage(null);
				if (!(event.getEntity().getKiller() instanceof Player))
					return;
				final String killer = event.getEntity().getKiller().getName();
				String victim = event.getEntity().getName();
				String prefixColour = main.getConfig().getString("Prefix.colour");
				String prefixText = main.getConfig().getString("Prefix.text");
				String killedByColour = main.getConfig().getString("Colour.by");
				String KilledColour = main.getConfig().getString("Colour.killed");
				String killerColour = main.getConfig().getString("Colour.killer");
				int time = main.getConfig().getInt("CmdBlock.blockTime");
				Location location = event.getEntity().getKiller().getLocation();
				Fight f = getFight(killer, victim);
				if (f == null)
					return;
				String dMSG = prefixColour + prefixText + " " + KilledColour + victim + killedByColour + " was killed by " + killerColour + killer;
				String dMSGColour = dMSG.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
				event.getEntity().sendMessage(dMSGColour);
				event.getEntity().getKiller().sendMessage(dMSGColour);
				main.log.info(victim + " was killed by " + killer + " after " + f.getAttacker() + " started the fight.");
				for (Player player : Bukkit.getServer().getOnlinePlayers())
					{
						if (player.hasPermission("pdm.toggle"))
							{
								if (!(main.pdmtoggle.contains(player.getName())))
									{
										if (!(player.equals(event.getEntity())))
											{
												if (!(player.equals(event.getEntity().getKiller())))
													{
														String pdMSG = prefixColour + prefixText + " " + KilledColour + victim + killedByColour + " was killed by "
																+ killerColour + killer + killedByColour + " at the location of" + ChatColor.WHITE + ": " + ChatColor.YELLOW
																+ location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + killedByColour + " after "
																+ killerColour + f.getAttacker() + killedByColour + " started the fight.";
														String pdMSGColour = pdMSG.replaceAll("&([a-z0-9])", ChatColor.COLOR_CHAR + "$1");
														player.sendMessage(pdMSGColour);
													}
											}
									}
							}
					}
				main.cmdblock.add(killer);
				this.fights.remove(f);
				Date now = new Date();
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy HH:mm");
				String log = "&3" + format.format(now) + " &f-&c " + killer + "&b killed&a " + victim + "&b at the location of&f:&e " + location.getBlockX()
						+ ", " + location.getBlockY() + ", " + location.getBlockZ() + "&b after&c " + f.getAttacker() + " &bstarted the fight.";
				List<String> list = main.logger.getStringList("log");
				list.add(log);
				main.logger.set("log", list);
				main.saveLog();
				Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(main, new Runnable()
					{
						public void run()
							{
								main.cmdblock.remove(killer);
							}
					}, (time * 20));
			}

	}