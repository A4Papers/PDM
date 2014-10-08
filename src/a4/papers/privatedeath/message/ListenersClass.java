package a4.papers.privatedeath.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenersClass implements Listener
	{
		Main main;
		Fights f;

		public ListenersClass(Main instance)
			{
				main = instance;
			}

		public ListenersClass(Fights instance)
			{
				f = instance;
			}

		@EventHandler
		public void onLeave(PlayerQuitEvent event)
			{
				Player player = event.getPlayer();
				main.pdmtoggle.remove(player);
				main.cmdblock.remove(player);
			}

		@EventHandler
		public void onKick(PlayerKickEvent event)
			{
				Player player = event.getPlayer();
				main.pdmtoggle.remove(player);
				main.cmdblock.remove(player);
			}

	
		
		@EventHandler
		public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
			{
				String p = event.getPlayer().getName();
				Player pp = event.getPlayer();
				if (!main.toggled == false)
					;
				if (main.cmdblock.contains(p))
					{
						if (event.getMessage().equalsIgnoreCase("/spawn") || event.getMessage().equalsIgnoreCase("/warp")
								|| event.getMessage().equalsIgnoreCase("/tp") || event.getMessage().equalsIgnoreCase("/tpaccept")
								|| event.getMessage().equalsIgnoreCase("/tpa") || event.getMessage().equalsIgnoreCase("/home"))
							{
								event.setCancelled(true);
								pp.sendMessage(ChatColor.YELLOW + "You have just killed a player, You can rerun the command soon!");
							}
					}
			}
	}