package com.artformgames.plugin.usersuffix.conf;

import cc.carm.lib.configuration.core.Configuration;
import cc.carm.lib.configuration.core.annotation.HeaderComment;
import cc.carm.lib.configuration.core.value.ConfigValue;
import cc.carm.lib.configuration.core.value.impl.ConfigValueMap;
import cc.carm.lib.configuration.core.value.type.ConfiguredMap;
import cc.carm.lib.configuration.core.value.type.ConfiguredValue;

public interface PluginConfig extends Configuration {

    ConfiguredValue<Boolean> DEBUG = ConfiguredValue.of(false);

    @HeaderComment({
            "Statistics Settings",
            "This option is used to help developers count plug-in versions and usage, and it will never affect performance and user experience.",
            "Of course, you can also choose to turn it off here for this plugin,",
            "or turn it off for all plugins in the configuration file under \"plugins/bStats\"."
    })
    ConfiguredValue<Boolean> METRICS = ConfiguredValue.of(Boolean.class, true);

    @HeaderComment({
            "Check update settings",
            "This option is used by the plug-in to determine whether to check for updates.",
            "If you do not want the plug-in to check for updates and prompt you, you can choose to close.",
            "Checking for updates is an asynchronous operation that will never affect performance and user experience."
    })
    ConfiguredValue<Boolean> CHECK_UPDATE = ConfiguredValue.of(true);

    @HeaderComment("Cooldown time for content change, in milliseconds.")
    ConfiguredValue<Long> COOLDOWN = ConfiguredValue.of(1000L);

    @HeaderComment({"Suffix format. The `%(suffix)` will be replaced with the suffix content."})
    ConfigValue<String> FORMAT = ConfiguredValue.of(" %(color)[%(suffix)%(color)]");

    @HeaderComment({"The default color for the suffix format section.", "Use #xxxxxx to mark as a RGB code."})
    ConfigValue<String> DEFAULT_COLOR = ConfiguredValue.of("#ffea00");

    @HeaderComment("Maximum length of the suffix of each permissions (excluding color symbols)")
    ConfiguredMap<Integer, String> MAX_LENGTH = ConfigValueMap.builderOf(Integer.class, String.class)
            .asLinkedMap().fromString().parseKey(Integer::parseInt).parseValue(Object::toString)
            .defaults(m -> {
                m.put(8, "usersuffix.use");
                m.put(12, "group.vip");
            }).build();

}
