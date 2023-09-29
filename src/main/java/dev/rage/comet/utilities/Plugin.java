package dev.rage.comet.utilities;

import dev.rage.comet.CometPlugin;
import dev.rage.comet.model.actionbar.ActionBarRunnable;
import dev.rage.comet.model.cosmetics.impl.balloons.tasks.BalloonRunnable;
import dev.rage.comet.model.lunarclient.LunarClientRunnable;
import dev.rage.comet.model.queue.thread.QueuePositionThread;
import dev.rage.comet.model.queue.thread.QueueReminderThread;
import lombok.Getter;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

@Getter
public class Plugin {

    private final String licenseKey;
    private final org.bukkit.plugin.Plugin pluginClass;

    private int statusCode;
    private String discordName;
    private String discordID;
    private final String statusMsg;

    private final String product;
    private final String version;

    public Plugin(String licenseKey, org.bukkit.plugin.Plugin pluginClass) {
        this.licenseKey = licenseKey;
        this.pluginClass = pluginClass;
        this.product = pluginClass.getName();
        this.version = pluginClass.getDescription().getVersion();

        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"licensekey\": \"" + licenseKey + "\",\n    \"product\": \"" + product + "\",\n    \"version\": \"" + version + "\"\n}");

            Request request = new Request.Builder()
                    .url("https://astro-operations.com/api/client")
                    .method("POST", body)
                    .addHeader("Authorization", "czJL6eALYaAyqzbmRX6hv3qVdQOnMdLJsDyacztANx5AFU81")
                    .build();

            Response response = client.newCall(request).execute();

            String data = response.body().string();
            JSONObject obj = new JSONObject(data);

            if (obj.has("status_msg") && obj.has("status_id")) {
                statusCode = obj.getInt("status_code");
                statusMsg = obj.getString("status_msg");

                if (obj.has("status_overview")) {
                    discordName = obj.getString("discord_username");
                    discordID = obj.getString("discord_id");

                    Arrays.asList("      &f&l* &b&lComet &7(&fHubCore&7) &f&l*       ",
                                    "",
                                    " &f&l* &bAuthors&7: &f" + CometPlugin.getPlugin().getDescription().getAuthors(),
                                    " &f&l* &bPlugin Version&7: &f" + CometPlugin.getPlugin().getDescription().getVersion(),
                                    " &f&l* &bLicense Status&7: &aVALID",
                                    " &f&l* &bThanks for using Comet Hubcore <3",
                                    " &f&l* &bIf u have support join to &ndiscord.gg/6GpV8ARYsG")
                            .forEach(s ->
                                    Bukkit.getConsoleSender().sendMessage(ChatUtil.translate(s)));



                    CometPlugin.get().registerManagers();
                    CometPlugin.get().registerListeners();
                    CometPlugin.get().registerCommands();

                    if (CometPlugin.get().getConfigFile().getBoolean("lunar-client.enabled")) {
                        Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new LunarClientRunnable(CometPlugin.get()), 0L, 20L);
                    }
                    if (CometPlugin.get().getConfigFile().getBoolean("action-bar.enabled")) {
                        int interval = CometPlugin.get().getConfigFile().getInt("action-bar.interval");
                        Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new ActionBarRunnable(CometPlugin.get(), interval), interval * 20L, interval * 20L);
                    }

                    QueueReminderThread reminderThread = new QueueReminderThread(CometPlugin.get());
                    reminderThread.start();

                    QueuePositionThread positionThread = new QueuePositionThread(CometPlugin.get());
                    positionThread.start();

                    CometPlugin.get().removeBuggedEntities();

                    Bukkit.getScheduler().runTaskTimer(CometPlugin.getPlugin(), new BalloonRunnable(CometPlugin.get().getUserManager()), 0L, 1L);
                    Bukkit.getMessenger().registerOutgoingPluginChannel(CometPlugin.getPlugin(), "BungeeCord");
                }
            } else {
                statusMsg = obj.getString("status_msg");

            }
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to connect to the license server.");
        }
    }
}