package com.artformgames.plugin.usersuffix.user;

import cc.carm.lib.easyplugin.utils.ColorParser;
import com.artformgames.core.user.User;
import com.artformgames.core.user.handler.AbstractUserHandler;
import com.artformgames.core.user.handler.UserHandler;
import com.artformgames.plugin.usersuffix.conf.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SuffixAccount extends AbstractUserHandler implements UserHandler {

    // Allow colors and hex codes(starting with #)
    public static final Pattern ALLOWED_CODES = Pattern.compile("(&[0-9a-fk-or]|#[0-9a-fA-F]{6})");
    public static final NumberFormat FORMAT = NumberFormat.getInstance();

    static {
        FORMAT.setMaximumFractionDigits(2);
    }

    public static boolean validColor(String code) {
        return ALLOWED_CODES.matcher(code).matches();
    }

    protected @Nullable String content;
    protected @Nullable String color;

    protected long lastChange = -1;

    public SuffixAccount(User user, @Nullable String content, @Nullable String color) {
        super(user);
        this.content = content;
        this.color = color;
    }

    protected Optional<Player> getPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(getUser().getUserUUID()));
    }

    public @Nullable String getColor() {
        return color;
    }

    public @Nullable String getContent() {
        return content;
    }

    public void setColor(@Nullable String color) {
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
        String colorContent = Optional.ofNullable(this.color).orElse(PluginConfig.DEFAULT_COLOR.getNotNull());

        if (colorContent.startsWith("#")) {
            String v = colorContent.substring(1);
            return Arrays.stream(v.split("")).map(s -> '§' + s)
                    .collect(Collectors.joining("", '§' + "x", ""));
        } else {
            return '§' + colorContent;
        }
    }

    public CompletableFuture<Void> setSuffix(@Nullable String content, @Nullable String color) {
        this.lastChange = System.currentTimeMillis();
        this.content = content;
        this.color = color;
        return SuffixLoader.TABLE.createReplace()
                .setColumnNames("user", "content", "color")
                .setParams(user.getID(), content, color)
                .executeFuture();
    }

    public boolean isCoolingDown() {
        return lastChange > 0 && System.currentTimeMillis() - lastChange < PluginConfig.COOLDOWN.getNotNull();
    }

    public String getCoolDownSeconds() {
        return FORMAT.format((lastChange + PluginConfig.COOLDOWN.getNotNull() - System.currentTimeMillis()) / 1000);
    }

}
