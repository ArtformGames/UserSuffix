package com.artformgames.plugin.usersuffix.user;

import com.artformgames.core.user.User;
import com.artformgames.core.user.handler.AbstractUserHandler;
import com.artformgames.core.user.handler.UserHandler;
import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import com.artformgames.plugin.usersuffix.manager.ServiceManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SuffixAccount extends AbstractUserHandler implements UserHandler {
    public SuffixAccount(User user) {
        super(user);
    }

    protected Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(getUser().getUserUUID()));
    }

    public String getSuffix() {
        return getPlayer().map(ServiceManager::getSuffix).orElse(null);
    }

    public void setSuffix(@Nullable String suffix) {
        getPlayer().ifPresent(player -> ServiceManager.setSuffixData(
                player, suffix, PluginConfig.WEIGHT.getNotNull()
        ));
    }

}
