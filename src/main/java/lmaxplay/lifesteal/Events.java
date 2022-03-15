package lmaxplay.lifesteal;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.material.*;
import org.bukkit.potion.*;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;

public class Events implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    @SuppressWarnings("ConstantConditions")
    //@Nullable
    public void onKill(PlayerDeathEvent event) {
        Player killed = event.getEntity().getPlayer();
        Player killer = event.getEntity().getKiller();

        if(killer == null) return;

        if(killer == killed) return;

        if(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() <= 2) {
            BanList banList = Bukkit.getServer().getBanList(BanList.Type.NAME);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.HOUR_OF_DAY, LifeSteal.plugin.getConfig().getInt("KillBanTime"));
            /*BanEntry banEntry = */banList.addBan(killed.getName(), "No health left", cal.getTime(), "LifeSteal");
            killed.kickPlayer("No health left");
            return;
        }

        killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + LifeSteal.plugin.getConfig().getInt("HealthGain.Kill"));
        killer.setHealth(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        killer.sendMessage("§aYou stole §cA heart§a from §b" + killed.getName() + "§a!");
        killed.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killed.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - LifeSteal.plugin.getConfig().getInt("HealthGain.Kill"));
        killed.sendMessage("§aYou lost §cone of your hearts§a due to §b" + killer.getName() + "§a!");
        killed.setAbsorptionAmount(20);
        killed.sendMessage("§aYou lost §cone of your hearts§a due to §b" + killer.getName() + "§a!");

        // killed.kickPlayer("§aAutomatic kick for: §cLosing a heart\n§aPlease rejoin");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity killed = event.getEntity();
        Entity killer = event.getEntity().getKiller();

        if( killed instanceof EnderDragon ) {
            if(!(killer instanceof Player)) {
                return;
            }
            Player killerPlayer = (Player)killer;
            if(LifeSteal.plugin.getConfig().getInt("HealthGain.EnderDragon") == 0) {
                return;
            }
            killerPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killerPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + LifeSteal.plugin.getConfig().getInt("HealthGain.EnderDragon"));
            killerPlayer.setHealth(killerPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            killerPlayer.sendMessage("§aYou gained §cA heart§a from killing §bThe ender dragon!§a!");
        }

        if( killed instanceof Wither ) {
            if(!(killer instanceof Player)) {
                return;
            }
            Player killerPlayer = (Player)killer;
            if(LifeSteal.plugin.getConfig().getInt("HealthGain.Wither") == 0) {
                return;
            }
            killerPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killerPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + LifeSteal.plugin.getConfig().getInt("HealthGain.Wither"));
            killerPlayer.setHealth(killerPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            killerPlayer.sendMessage("§aYou gained §cA heart§a from killing §bThe Wither§a!");
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if(event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            // Player ate an enchanted golden apple
            if(LifeSteal.plugin.getConfig().getInt("HealthGain.EnchantedGoldenApple") == 0) {
                return;
            }
            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(
                    event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + LifeSteal.plugin.getConfig().getInt("HealthGain.EnchantedGoldenApple"));
            event.getPlayer().setHealth(event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            event.getPlayer().sendMessage("§aYou gained §ca new heart§a from the almighty §eenchanted golden apple§a!");
            // event.getPlayer().sendMessage("§aYou feel stronger now");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        //player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(2400, 9));
        //player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(2400, 9));
        //player.addPotionEffect(PotionEffectType.SPEED.createEffect(2400, 4));
    }
}
