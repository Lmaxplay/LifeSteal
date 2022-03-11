package lmaxplay.lifesteal;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.material.*;

import java.util.Calendar;
import java.util.Date;

public class Events implements Listener {
    @SuppressWarnings( "deprecation" )
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill(PlayerDeathEvent event) {
        Entity killed = event.getEntity();
        Entity killer = event.getEntity().getKiller();

        if(!(killed instanceof Player)) {
            return;
        }

        if(!(killer instanceof Player)) {
            return;
        }


        Player killedPlayer = (Player)killed;
        Player killerPlayer = (Player)killer;

        if(killerPlayer == killedPlayer) return;

        if(killedPlayer.getMaxHealth() <= 2) {
            BanList banList = Bukkit.getServer().getBanList(BanList.Type.NAME);
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date());               // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 24);      // adds one hour
            BanEntry banEntry = banList.addBan(killedPlayer.getName(), "No health left", cal.getTime(), "LifeSteal");
            killedPlayer.kickPlayer("No health left");
            return;
        }

        killerPlayer.setMaxHealth(killerPlayer.getMaxHealth() + 2);
        killerPlayer.setHealth(killerPlayer.getMaxHealth());
        killerPlayer.sendMessage("§aYou stole a heart from §b" + killedPlayer.getName() + "§a!");
        killedPlayer.setMaxHealth(killedPlayer.getMaxHealth() - 2);
        killedPlayer.sendMessage("§aYou lost a heart due to §b" + killerPlayer.getName() + "§a!");
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            // Player ate an enchanted golden apple
            event.getPlayer().setMaxHealth(
                    event.getPlayer().getMaxHealth() + 2 // Change the number to the amount of health to add
                );
            event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
            event.getPlayer().sendMessage("§aYou gained a new heart from the almighty enchanted golden apple!");
            event.getPlayer().sendMessage("§aYou feel stronger now");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
        }
    }
}
