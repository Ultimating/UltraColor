package me.ultimategamer200.ultracolor.util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.mineacademy.fo.remain.Remain;

public class SaveOnlinePlayersTask extends BukkitRunnable {
	@Override
	public void run() {
		for (final Player player : Remain.getOnlinePlayers()) {
			
		}
	}
}
