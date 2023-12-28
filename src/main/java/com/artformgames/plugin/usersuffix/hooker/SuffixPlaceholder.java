package com.artformgames.plugin.usersuffix.hooker;

import cc.carm.lib.easyplugin.papi.EasyPlaceholder;
import com.artformgames.core.ArtCore;
import com.artformgames.plugin.usersuffix.user.SuffixAccount;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import panda.std.function.TriFunction;

import java.util.Optional;

public class SuffixPlaceholder extends EasyPlaceholder {

    public SuffixPlaceholder(@NotNull JavaPlugin plugin, @NotNull String rootIdentifier) {
        super(plugin, rootIdentifier);
        handleSuffix("suffix", (player, account, args) -> account.getSuffix());
        handleSuffix("content", ((player, account, args) -> Optional.ofNullable(account.getContent()).orElse("")));
        handleSuffix("color", ((player, account, args) -> account.getColorCode()));
    }

    protected void handleSuffix(String id, TriFunction<Player, SuffixAccount, String[], String> handler) {
        handle(id, (player, args) -> {
            if (!(player instanceof Player onlinePlayer)) return "Offline";
            SuffixAccount account = ArtCore.getHandler(onlinePlayer, SuffixAccount.class);
            return handler.apply(onlinePlayer, account, args);
        });
    }

}

