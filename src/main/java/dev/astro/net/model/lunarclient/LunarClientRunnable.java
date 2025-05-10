package dev.astro.net.model.lunarclient;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.nametag.Nametag;
import com.lunarclient.apollo.module.nametag.NametagModule;
import com.lunarclient.apollo.recipients.Recipients;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.Comet;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Risas
 * Project: MysticHQ
 * Date: 23-01-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Setter
public class LunarClientRunnable implements Runnable {

	private final Comet plugin;

	public LunarClientRunnable(Comet plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			List<Component> components = ChatUtil.placeholder(player, plugin.getConfigFile().getStringList("lunar-client.nametag")).stream()
				.map(text -> Component.text(text)) // o net.kyori.adventure.text.Component.text(text)
				.collect(Collectors.toList());

			Apollo.getModuleManager().getModule(NametagModule.class)
				.overrideNametag(Recipients.ofEveryone(), player.getUniqueId(), Nametag.builder()
					.lines(components)
					.build()
				);
		}
	}
}
