package dev.astro.net.utilities;

import dev.astro.net.integrations.PlaceholderAPIHook;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Risas
 * Project: Comet
 * Date: 05-12-2022
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class ChatUtil {
    public String NORMAL_LINE = "&7&m-----------------------------";

    public String translate(String text) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String hexCode = text.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');

            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();

            for (char c : ch) {
                builder.append("&").append(c);
            }

            text = text.replace(hexCode, builder.toString());
            matcher = pattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> translate(List<String> list) {
        return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public String[] translate(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = translate(array[i]);
        }
        return array;
    }

    public String placeholder(Player player, String text) {
        return translate(PlaceholderAPIHook.isEnabled() ? PlaceholderAPI.setPlaceholders(player, text) : text);
    }

    public List<String> placeholder(Player player, List<String> list) {
        return translate(PlaceholderAPIHook.isEnabled() ? PlaceholderAPI.setPlaceholders(player, list) : list);
    }

    public void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(translate(text));
    }

    public void sendMessage(CommandSender sender, String[] array) {
        sender.sendMessage(translate(array));
    }

    public void logger(String text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public void logger(String[] text) {
        Bukkit.getConsoleSender().sendMessage(translate(text));
    }

    public void broadcast(String text) {
        Bukkit.broadcastMessage(translate(text));
    }
}