package lmaxplay.lifesteal;

// import com.comphenix.protocol.*;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.plugin.Plugin;

public class LPacketListener {

    public static void register(Plugin plugin, ProtocolManager protocolManager) {
        protocolManager.addPacketListener(new PacketAdapter(plugin, ListenerPriority.LOWEST, PacketType.Play.Server.LOGIN) {
            @Override
            public void onPacketSending(PacketEvent event) {
                //if it's a login packet write the first boolean to true (hardcore flag)
                if(event.getPacketType().equals(PacketType.Play.Server.LOGIN)) {
                    if(plugin.getConfig().getBoolean("HardcoreHearts")) {
                        event.getPacket().getBooleans().write(0, true);
                    }
                }
            }
        });
    }
}
