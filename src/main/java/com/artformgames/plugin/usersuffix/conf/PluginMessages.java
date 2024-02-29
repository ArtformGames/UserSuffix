package com.artformgames.plugin.usersuffix.conf;

import cc.carm.lib.mineconfiguration.bukkit.value.ConfiguredMessageList;
import com.artformgames.core.conf.Messages;
import net.md_5.bungee.api.chat.BaseComponent;

public interface PluginMessages extends Messages {

    ConfiguredMessageList<BaseComponent[]> CLEARED = Messages.list()
            .defaults("&fThe existing suffix has been cleared for you. To set up again, enter &e/suffix content <content> &f.")
            .build();

    ConfiguredMessageList<BaseComponent[]> NO_PERMISSION = Messages.list()
            .defaults("&c&lSorry! &fBut you dont have enough permission to set a suffix!")
            .build();

    ConfiguredMessageList<BaseComponent[]> TOO_LONG = Messages.list()
            .defaults("&fThe suffix you want to set is too long, no longer than &e% (length) &f characters (excluding colors).")
            .params("length")
            .build();

    ConfiguredMessageList<BaseComponent[]> TOO_SHORT = Messages.list()
            .defaults("&fYour suffix contains at least one character (excluding colors).")
            .build();

    ConfiguredMessageList<BaseComponent[]> COOLING = Messages.list()
            .defaults("&c&lHold on! &fYou can't change your suffix so fast, please wait &e%(time)&f seconds for next change.")
            .params("time")
            .build();


    ConfiguredMessageList<BaseComponent[]> SUCCESS = Messages.list()
            .defaults("&fSuccessfully modified your suffix to &r%(suffix) &f.")
            .params("suffix")
            .build();

    ConfiguredMessageList<BaseComponent[]> FAILED = Messages.list()
            .defaults("&fYou can't use this suffix because it may contain a violating word, please try again after changing something else.")
            .build();

    ConfiguredMessageList<BaseComponent[]> NO_SPACE = Messages.list()
            .defaults("&fSuffix should not contain spaces.")
            .build();

    ConfiguredMessageList<BaseComponent[]> INVALID_COLOR_CODE = Messages.list()
            .defaults(
                    "&fYour input format color is invalid!",
                    "&fPlease use &e&l#xxxxxx &for RGB color code or &e&l&0-9a-fk-or &ffor color code."
            ).build();


}
