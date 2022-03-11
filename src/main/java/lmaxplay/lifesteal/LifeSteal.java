package lmaxplay.lifesteal;

import org.bukkit.plugin.java.JavaPlugin;

public final class LifeSteal extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("health").setExecutor(new HealthCommand());
        this.getServer().getPluginManager().registerEvents(new Events(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
