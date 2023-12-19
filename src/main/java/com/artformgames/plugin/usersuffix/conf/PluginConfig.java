package com.artformgames.plugin.usersuffix.conf;

import cc.carm.lib.configuration.core.ConfigurationRoot;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.ConfigValue;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;

public class PluginConfig extends ConfigurationRoot {

    public static final ConfiguredValue<Boolean> DEBUG = ConfiguredValue.of(Boolean.class, false);

    @HeaderComment({
            "Check update settings",
            "This option is used by the plug-in to determine whether to check for updates.",
            "If you do not want the plug-in to check for updates and prompt you, you can choose to close.",
            "Checking for updates is an asynchronous operation that will never affect performance and user experience."
    })
    public static final ConfiguredValue<Boolean> CHECK_UPDATE = ConfiguredValue.of(Boolean.class, true);

    @HeaderComment("Suffix weight in LuckPerms")
    public static final ConfigValue<Integer> WEIGHT = ConfiguredValue.of(Integer.class, 1);

    @HeaderComment({"Suffix format. The `%(suffix)` will be replaced with the suffix content."})
    public static final ConfigValue<String> FORMAT = ConfiguredValue.of(String.class, " %(color)[%(suffix)%(color)]");

    @HeaderComment("The default color for the suffix format section.")
    public static final ConfigValue<String> DEFAULT_COLOR = ConfiguredValue.of(String.class, "&7");

    @HeaderComment("Maximum length of the suffix (excluding color symbols)")
    public static final ConfigValue<Integer> MAX_LENGTH = ConfiguredValue.of(Integer.class, 8);

}
