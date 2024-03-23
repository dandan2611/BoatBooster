package fr.codinbox.boatbooster.command;

import fr.codinbox.boatbooster.BoatBooster;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BoosterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {
            BoatBooster.getInstance().reloadConfig();
            sender.sendMessage(Component.text("§aConfig reloaded!"));
        } catch (Exception exception) {
            sender.sendMessage(Component.text("§cAn error occured, is the new config file correct?"));
        }
        return true;
    }

}
