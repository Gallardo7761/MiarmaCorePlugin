package net.miarma.toorbo.commands.roleplay;

import net.miarma.toorbo.config.CommandWrapper;
import net.miarma.toorbo.config.providers.CommandProvider;
import net.miarma.toorbo.util.PlayerUtil;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.stream.Collectors;

import static net.miarma.toorbo.config.providers.CommandProvider.Arguments.MESSAGE;

public class MeCommand {
    public static void register() {
        CommandWrapper meCmd = CommandProvider.getMeCommand();
        new CommandAPICommand(meCmd.getName())
            .withArguments(MESSAGE)
            .withFullDescription(meCmd.getDescription())
            .withPermission(meCmd.getPermission().base())
            .withShortDescription(meCmd.getDescription())
            .executesPlayer((sender, args) -> {
                String joinedArgs = Arrays.stream(args.rawArgs()).collect(Collectors.joining(" "));
                String msg = "§6(" + sender.getName() + ") [Me] §7" + joinedArgs;
                Bukkit.getServer().getOnlinePlayers().stream()
                        .filter(p -> (p.getWorld() == sender.getWorld()) && (PlayerUtil.distance(sender, p) < 25 || sender.equals(p)))
                        .forEach(p -> p.sendMessage(msg));
            })
            .register();
    }
}
