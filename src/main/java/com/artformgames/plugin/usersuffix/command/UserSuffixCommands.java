package com.artformgames.plugin.usersuffix.command;

import cc.carm.lib.easyplugin.utils.ColorParser;
import com.artformgames.core.ArtCore;
import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import com.artformgames.plugin.usersuffix.conf.PluginMessages;
import com.artformgames.plugin.usersuffix.user.SuffixAccount;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import org.bukkit.entity.Player;

import java.util.Map;

@Command(name = "usersuffix", aliases = "suffix")
@Permission("usersuffix.use")
public class UserSuffixCommands {

    @Execute(name = "clear")
    void clearSuffix(@Context Player player) {
        SuffixAccount account = ArtCore.getHandler(player, SuffixAccount.class);
        account.setContent(null);
        account.setColor(null);
        PluginMessages.CLEARED.send(player);
    }

    @Execute(name = "content")
    void setContent(@Context Player player, @Join String content) {
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

        if (ColorParser.clear(content).isBlank()) {
            PluginMessages.TOO_SHORT.send(player);
            return;
        }

        SuffixAccount account = ArtCore.getHandler(player, SuffixAccount.class);
        account.setContent(content);
        PluginMessages.SUCCESS.send(player, account.getSuffix());
    }


    @Execute(name = "color")
    void setBracketsColor(@Context Player player, @Arg String color) {
        if (!SuffixAccount.validColor(color)) {
            PluginMessages.INVALID_COLOR_CODE.send(player);
            return;
        }

        SuffixAccount account = ArtCore.getHandler(player, SuffixAccount.class);
        account.setColor(color);
        PluginMessages.SUCCESS.send(player, account.getSuffix());
    }

}
