package com.artformgames.plugin.usersuffix.user;

import cc.carm.lib.easysql.api.SQLQuery;
import cc.carm.lib.easysql.api.SQLTable;
import com.artformgames.core.ArtCore;
import com.artformgames.core.user.User;
import com.artformgames.core.user.UserKey;
import com.artformgames.core.user.handler.UserHandlerLoader;
import com.artformgames.plugin.usersuffix.Main;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class SuffixLoader extends UserHandlerLoader<SuffixAccount> {

    public static final SQLTable TABLE = SQLTable.of("user_suffix", builder -> {
        builder.addColumn("user", "INT UNSIGNED NOT NULL PRIMARY KEY");
        builder.addColumn("content", "VARCHAR(255)");
        builder.addColumn("color", "VARCHAR(7)");
    });

    public SuffixLoader(@NotNull Plugin plugin) {
        super(plugin, SuffixAccount.class);
        try {
            TABLE.create(ArtCore.getSQLManager());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull CompletableFuture<SuffixAccount> load(@NotNull User user) {
        return super.load(user).thenApply(acc -> {
            Main.debugging("Loaded suffix data for user: " + user.getUsername() + " - " + acc.color + " " + acc.content + " (at " + acc.lastChange + ")");
            return acc;
        });
    }

    @Override
    public @NotNull SuffixAccount emptyData(@NotNull User user) {
        return new SuffixAccount(user, null, null);
    }

    @Override
    public @Nullable SuffixAccount loadData(User user) throws Exception {
        try (SQLQuery query = TABLE.createQuery()
                .addCondition("user", user.getID())
                .setLimit(1).build().execute()) {
            ResultSet rs = query.getResultSet();
            if (rs.next()) {
                String content = rs.getString("content");
                String color = rs.getString("color");
                if (color != null && !SuffixAccount.validColor(color)) {
                    color = null;
                }
                return new SuffixAccount(user, content, color);
            }
        }
        return null;
    }

    @Override
    public void saveData(UserKey user, SuffixAccount handler) throws Exception {
        // Do nothing
    }

}
