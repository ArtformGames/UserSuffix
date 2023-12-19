package com.artformgames.plugin.usersuffix;

import cc.carm.lib.easyplugin.EasyPlugin;
import cc.carm.lib.mineconfiguration.bukkit.MineConfiguration;
import com.artformgames.core.ArtCore;
import com.artformgames.core.utils.GHUpdateChecker;
import com.artformgames.plugin.usersuffix.command.UserSuffixCommands;
import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import com.artformgames.plugin.usersuffix.conf.PluginMessages;
import com.artformgames.plugin.usersuffix.user.SuffixAccount;
import dev.rollczi.litecommands.LiteCommands;
import org.bukkit.command.CommandSender;

public class Main extends EasyPlugin {

    private static Main instance;

    public Main() {
        Main.instance = this;
    }

    protected MineConfiguration configuration;
    protected LiteCommands<CommandSender> commands;

    @Override
    @SuppressWarnings("UnstableApiUsage")
    protected boolean initialize() {

        log("Loading plugin configurations...");
        configuration = new MineConfiguration(this);
        configuration.initializeConfig(PluginConfig.class);
        configuration.initializeMessage(PluginMessages.class);


        log("Register handlers...");
        ArtCore.getUserManager().registerHandler(this, SuffixAccount.class, SuffixAccount::new);

        log("Register commands...");
        this.commands = ArtCore.createCommand().commands(UserSuffixCommands.class).build();
        this.commands.register();

        if (PluginConfig.CHECK_UPDATE.getNotNull()) {
            log("Start to check the plugin versions...");
            getScheduler().runAsync(GHUpdateChecker.runner(this));
        } else {
            log("Version checker is disabled, skipped.");
        }

        return true;
    }

    @Override
    public boolean isDebugging() {
        return PluginConfig.DEBUG.getNotNull();
    }

    public static void info(String... messages) {
        getInstance().log(messages);
    }

    public static void severe(String... messages) {
        getInstance().error(messages);
    }

    public static void debugging(String... messages) {
        getInstance().debug(messages);
    }

    public static Main getInstance() {
        return instance;
    }

}
