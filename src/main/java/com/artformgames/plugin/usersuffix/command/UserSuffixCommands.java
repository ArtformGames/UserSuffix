package com.artformgames.plugin.usersuffix.command;

import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import com.artformgames.plugin.usersuffix.conf.PluginMessages;
import com.artformgames.plugin.usersuffix.user.SuffixAccount;
import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.optional.OptionalArg;
import dev.rollczi.litecommands.annotations.permission.Permission;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "usersuffix", aliases = "suffix")
@Permission("usersuffix.use")
public class UserSuffixCommands {

    @Execute
    void set(@Context Player player, @Arg String suffix, @OptionalArg String blanketColor) {

    }

    public static boolean setSuffix(SuffixAccount data, Player player, String color, String suffixText) {
        String suffix = color + suffixText + color;
        if (!SuffixAccount.validColor(color)) {
            PluginMessages.INVALID_COLOR_CODE.send(player);
        } else if (suffixText.contains("&")) {
            PluginMessages.CONTAIN_COLOR_CODE.send(player);
        } else if (suffixText.length() > PluginConfig.MAX_LENGTH.getNotNull()) {
            PluginMessages.TOO_LONG.send(player, PluginConfig.MAX_LENGTH.getNotNull());
        } else {
            data.setSuffix(suffixText, ChatColor.getByChar(color));
            PluginMessages.SUCCESS.send(player, PlaceholderAPI.setPlaceholders(player, "%artessentials_suffix%"));
        }

        return true;
    }

}
