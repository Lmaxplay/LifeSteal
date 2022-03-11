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
import org.bukkit.potion.*;

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
            cal.add(Calendar.HOUR_OF_DAY, LifeSteal.plugin.getConfig().getInt("KillBanTime"));      // adds one hour
            BanEntry banEntry = banList.addBan(killedPlayer.getName(), "No health left", cal.getTime(), "LifeSteal");
            killedPlayer.kickPlayer("No health left");
            return;
        }

        killerPlayer.setMaxHealth(killerPlayer.getMaxHealth() + 2);
        killerPlayer.setHealth(killerPlayer.getMaxHealth());
        killerPlayer.sendMessage("§aYou stole §ca heart§a from §b" + killedPlayer.getName() + "§a!");
        killedPlayer.setMaxHealth(killedPlayer.getMaxHealth() - 2);
        killedPlayer.sendMessage("§aYou lost §cone of your hearts§a due to §b" + killerPlayer.getName() + "§a!");
        killedPlayer.setAbsorptionAmount(20);
        killedPlayer.sendMessage("§aYou lost §cone of your hearts§a due to §b" + killerPlayer.getName() + "§a!");

        // Add potion effects
        killedPlayer.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(120, 9));
        killedPlayer.addPotionEffect(PotionEffectType.REGENERATION.createEffect(120, 9));
        killedPlayer.addPotionEffect(PotionEffectType.SPEED.createEffect(120, 9));

        killedPlayer.kickPlayer("§aAutomatic kick for: §cLosing a heart\n§aPlease rejoin");
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
            event.getPlayer().sendMessage("§aYou gained §ca new heart§a from the almighty §eenchanted golden apple§a!");
            event.getPlayer().sendMessage("§aYou feel stronger now");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
        }
    }
}
