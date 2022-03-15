package lmaxplay.lifesteal;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.*;
import org.bukkit.command.Command;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.material.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.potion.*;

public class AboutCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            player.sendMessage("SimpleLifeSteal\nVersion " + LifeSteal.plugin.getDescription().getVersion());
        }
        return false;
    }
}
