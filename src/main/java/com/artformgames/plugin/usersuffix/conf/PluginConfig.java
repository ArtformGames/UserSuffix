package com.artformgames.plugin.usersuffix.conf;

import cc.carm.lib.configuration.core.Configuration;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.ConfigValue;
import cc.carm.lib.configuration.core.value.type.ConfiguredList;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;
import org.bukkit.ChatColor;

public interface PluginConfig extends Configuration {

    ConfiguredValue<Boolean> DEBUG = ConfiguredValue.of(false);

    @HeaderComment({
            "Check update settings",
            "This option is used by the plug-in to determine whether to check for updates.",
            "If you do not want the plug-in to check for updates and prompt you, you can choose to close.",
            "Checking for updates is an asynchronous operation that will never affect performance and user experience."
    })
    ConfiguredValue<Boolean> CHECK_UPDATE = ConfiguredValue.of(true);

    @HeaderComment({"Suffix format. The `%(suffix)` will be replaced with the suffix content."})
    ConfigValue<String> FORMAT = ConfiguredValue.of(" %(color)[%(suffix)%(color)]");

    @HeaderComment("The default color for the suffix format section.")
    ConfigValue<ChatColor> DEFAULT_COLOR = ConfiguredValue.builderOf(ChatColor.class).fromString()
            .parseValue(c -> ChatColor.valueOf(c.toUpperCase())).serializeValue(ChatColor::name)
            .defaults(ChatColor.GRAY).build();

    @HeaderComment("Allowed colors for the suffix format section.")
    ConfiguredList<ChatColor> ALLOWED_COLORS = ConfiguredList.builderOf(ChatColor.class).fromString()
            .parseValue(c -> ChatColor.valueOf(c.toUpperCase())).serializeValue(ChatColor::name)
            .defaults(
                    ChatColor.BLACK, ChatColor.DARK_BLUE,
                    ChatColor.DARK_GREEN, ChatColor.DARK_AQUA,
                    ChatColor.DARK_RED, ChatColor.DARK_PURPLE,
                    ChatColor.GOLD, ChatColor.GRAY,
                    ChatColor.DARK_GRAY, ChatColor.BLUE,
                    ChatColor.GREEN, ChatColor.AQUA,
                    ChatColor.RED, ChatColor.LIGHT_PURPLE,
                    ChatColor.YELLOW, ChatColor.WHITE
            ).build();

    @HeaderComment("Maximum length of the suffix (excluding color symbols)")
    ConfigValue<Integer> MAX_LENGTH = ConfiguredValue.of(8);

}
