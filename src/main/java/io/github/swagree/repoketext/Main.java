package io.github.swagree.repoketext;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private long lastConfigModified = 0L;

    public static Main plugin;
    @Override
    public void onEnable() {
        getCommand("rpt").setExecutor(new CommandText());
        Bukkit.getPluginManager().registerEvents(new TextGuiListener(),this);
        getDataFolder().mkdirs();
        saveDefaultConfig();
        reloadConfig();

        Bukkit.getConsoleSender().sendMessage("§7[RePokeText] §b作者§fSwagRee §cQQ:§f352208610");

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            File configFile = new File("plugins\\RePokeText\\config.yml");

            long lastModified = configFile.lastModified();

            if (lastModified > lastConfigModified) {
                reloadConfig();
                lastConfigModified = lastModified;
            }
        }, 0L, 40L);

        plugin = this;

    }

    @Override
    public void onDisable() {
        System.out.println("1");
    }
}
