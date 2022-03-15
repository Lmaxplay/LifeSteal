package lmaxplay.lifesteal;

import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.java.JavaPlugin;
import com.comphenix.protocol.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LifeSteal extends JavaPlugin {

    static JavaPlugin plugin;

    private ProtocolManager protocolManager;
    private Logger logger;

    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
        this.logger = this.getLogger();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        this.getServer().getPluginCommand("lifesteal").setExecutor(new AboutCommand());
        plugin = this;
        if(protocolManager != null) {
            LPacketListener.register(this, protocolManager);
        } else {
            logger.log(Level.INFO, "ProtocolManager = NULL");
        }
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
