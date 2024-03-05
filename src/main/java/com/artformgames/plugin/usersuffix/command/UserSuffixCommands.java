package com.artformgames.plugin.usersuffix.command;

import cc.carm.lib.easyplugin.utils.ColorParser;
import com.artformgames.core.ArtCore;
import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import com.artformgames.plugin.usersuffix.conf.PluginMessages;
import com.artformgames.plugin.usersuffix.migrator.ArtEssMigrator;
import com.artformgames.plugin.usersuffix.user.SuffixAccount;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import jdk.jfr.Description;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

@Command(name = "usersuffix", aliases = "suffix")
@Permission("usersuffix.use")
public class UserSuffixCommands {

    @Execute(name = "clear")
    void clearSuffix(@Context Player player) {
        SuffixAccount account = ArtCore.getHandler(player, SuffixAccount.class);
        account.setSuffix(null, null);
        PluginMessages.CLEARED.send(player);
    }

    @Execute(name = "set")
    void setContent(@Context Player player, @Arg("format-color") String formatColor, @Join String content) {
        SuffixAccount account = ArtCore.getHandler(player, SuffixAccount.class);

        if (account.isCoolingDown()) {
            PluginMessages.COOLING.send(player, account.getCoolDownSeconds());
            return;
        }

        if (!SuffixAccount.validColor(formatColor)) {
            PluginMessages.INVALID_COLOR_CODE.send(player);
            return;
        }

        int maxLength = PluginConfig.MAX_LENGTH.entrySet().stream()
                .filter(e -> player.hasPermission(e.getValue()))
                .mapToInt(Map.Entry::getKey).max().orElse(-1);

        if (maxLength <= 0) {
            PluginMessages.NO_PERMISSION.send(player);
            return;
        }

        if (content.contains(" ")) {
            PluginMessages.NO_SPACE.send(player);
            return;
        }

        if (ColorParser.clear(content).length() > maxLength) {
            PluginMessages.TOO_LONG.send(player, PluginConfig.MAX_LENGTH.getNotNull());
            return;
        }

        if (content.length() > 200) {
            PluginMessages.TOO_LONG.send(player, 200);
            return;
        }

        if (ColorParser.clear(content).isBlank()) {
            PluginMessages.TOO_SHORT.send(player);
            return;
        }

        account.setSuffix(content, formatColor);
        PluginMessages.SUCCESS.send(player, account.getSuffix());
    }

    @Execute(name = "import")
    @Description("Import suffixes from ArtEssentials(outdated).")
    @Permission("usersuffix.admin")
    void importLuckPerms(@Context CommandSender sender,
                         @Arg("perms-table") String sourceTable, @Arg("users-table") String usersTable, @Arg boolean purge) {
        try {
            int count = ArtEssMigrator.importData(sourceTable, usersTable, purge);
            sender.sendMessage("Successfully imported " + count + " suffixes from LuckPerms.");
        } catch (Exception ex) {
            sender.sendMessage("An error occurred while importing suffixes from LuckPerms!");
            ex.printStackTrace();
        }
    }

}
