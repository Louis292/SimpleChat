package fr.Louis292.simpleChat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleChat extends JavaPlugin implements Listener {
    private FileConfiguration config;

    public static boolean REQUIRE_PERMISSION;
    public static String PERMISSION;

    public static String CHAT_FORMAT;

    @Override
    public void onEnable() {
        getLogger().info("Starting...");

        this.saveDefaultConfig();
        config = getConfig();

        CHAT_FORMAT = config.getString("format");

        REQUIRE_PERMISSION = config.getBoolean("color_chat.require_permission");
        PERMISSION = config.getString("color_chat.permission");

        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("The plugin was been start !");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin was been disable");
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if (REQUIRE_PERMISSION) {
            if (player.hasPermission(PERMISSION)) {
                message = ChatColor.translateAlternateColorCodes('&', message);
            }
        } else {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        String newMessage = CHAT_FORMAT
                .replace("{PLAYER_NAME}", player.getName())
                .replace("{MESSAGE}", message)
                .replace("{PLAYER_DISPLAY_NAME}", player.getDisplayName());

        event.setMessage(newMessage);
    }
}
