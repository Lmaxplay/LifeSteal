package lmaxplay.lifesteal;

import org.bukkit.command.*;
import org.bukkit.entity.*;

public class HealthCommand implements CommandExecutor {
    @Override
    @SuppressWarnings( "deprecation" )
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if(args.length <= 0) {
                return false;
            }
            Player player = (Player) sender;
            player.setMaxHealth(10.0d);
        }
        return true;
    };
}
