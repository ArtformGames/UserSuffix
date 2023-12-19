package com.artformgames.plugin.usersuffix.conf;

import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessageList;
import com.artformgames.core.conf.MessagesRoot;
import net.md_5.bungee.api.chat.BaseComponent;

public class PluginMessages extends MessagesRoot {

    public static final ConfiguredMessageList<BaseComponent[]> CLEARED = list()
            .defaults("&f The existing suffix has been cleared for you. To set up, enter &e/suffix <content> &f.")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> TOO_LONG = list()
            .defaults("&fThe suffix you want to set is too long, no longer than &e% (length) &f characters (excluding colors).")
            .params("length")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> TOO_SHORT = list()
            .defaults("&fYour suffix contains at least one character (excluding colors).")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> SUCCESS = list()
            .defaults("&fSuccessfully modified your suffix to &r%(suffix) &f.")
            .params("suffix")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> FAILED = list()
            .defaults("&fYou can't use this suffix because it may contain a violating word, please try again after changing something else.")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> NO_SPACE = list()
            .defaults("&fSuffix should not contain spaces.")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> CONTAIN_COLOR_CODE = list()
            .defaults("&fYou can't include color codes directly in your suffix!")
            .build();

    public static final ConfiguredMessageList<BaseComponent[]> INVALID_COLOR_CODE = list()
            .defaults("&fYour color code is invalid!")
            .build();

}
