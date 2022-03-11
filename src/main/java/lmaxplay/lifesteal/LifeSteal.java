package lmaxplay.lifesteal;

import org.bukkit.plugin.java.JavaPlugin;

public final class LifeSteal extends JavaPlugin {

    static JavaPlugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        plugin = this;
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
