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

    @HeaderComment("Suffix")
    public static final ConfigValue<Integer> WEIGHT = ConfiguredValue.of(Integer.class, 1);

    @HeaderComment({"后缀格式，其中“%s”将被替换为后缀内容。"})
    public static final ConfigValue<String> FORMAT = ConfiguredValue.of(String.class, " %(color)[%(suffix)%(color)]");

    @HeaderComment("后缀格式部分的默认颜色。")
    public static final ConfigValue<String> DEFAULT_COLOR = ConfiguredValue.of(String.class, "&7");

    @HeaderComment("后缀名的最大长度(不包括颜色符号)")
    public static final ConfigValue<Integer> MAX_LENGTH = ConfiguredValue.of(Integer.class, 8);

}
