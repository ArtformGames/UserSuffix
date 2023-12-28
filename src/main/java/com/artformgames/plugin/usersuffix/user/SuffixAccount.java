package com.artformgames.plugin.usersuffix.user;

import cc.carm.lib.easyplugin.utils.ColorParser;
import com.artformgames.core.user.User;
import com.artformgames.core.user.handler.AbstractUserHandler;
import com.artformgames.core.user.handler.UserHandler;
import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.regex.Pattern;

public class SuffixAccount extends AbstractUserHandler implements UserHandler {
    public static final Pattern ALLOWED_CODES = Pattern.compile("[\\da-f]+");

    public static boolean validColor(String code) {
        return ALLOWED_CODES.matcher(code).matches();
    }


    protected @Nullable String content;
    protected @Nullable ChatColor color;

    public SuffixAccount(User user, @Nullable String content, @Nullable ChatColor color) {
        super(user);
        this.content = content;
        this.color = color;
    }

    protected Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(getUser().getUserUUID()));
    }

    public @Nullable ChatColor getColor() {
        return color;
    }

    public @Nullable String getContent() {
        return content;
    }

    public void setColor(@Nullable ChatColor color) {
        this.color = color;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    public @Nullable String getSuffix() {
        if (this.content == null) return null;
        return ColorParser.parse(PluginConfig.FORMAT.getNotNull()
                .replace("%(color)", getColorCode())
                .replace("%(suffix)", this.content));
    }

    public @NotNull String getColorCode() {
        return this.color != null ? "&" + this.color.getChar() : "&" + PluginConfig.DEFAULT_COLOR.getNotNull().getChar();
    }

    public void setSuffix(@Nullable String content, @Nullable ChatColor color) {
        this.content = content;
        this.color = color;
    }

}
