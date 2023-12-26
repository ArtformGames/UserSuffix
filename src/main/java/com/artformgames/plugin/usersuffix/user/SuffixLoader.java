package com.artformgames.plugin.usersuffix.user;

import cc.carm.lib.easysql.api.SQLQuery;
import cc.carm.lib.easysql.api.SQLTable;
import com.artformgames.core.ArtCore;
import com.artformgames.core.user.User;
import com.artformgames.core.user.UserKey;
import com.artformgames.core.user.handler.UserHandlerLoader;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SuffixLoader extends UserHandlerLoader<SuffixAccount> {

    protected SQLTable table = SQLTable.of("user_suffix", builder -> {
        builder.addColumn("user", "INT UNSIGNED NOT NULL PRIMARY KEY");
        builder.addColumn("suffix", "VARCHAR(16)");
        builder.addColumn("color", "CHAR(1)");
    });

    public SuffixLoader(@NotNull Plugin plugin) {
        super(plugin, SuffixAccount.class);
        try {
            table.create(ArtCore.getSQLManager());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull SuffixAccount emptyData(@NotNull User user) {
        return new SuffixAccount(user, null, null);
    }

    @Override
    public @Nullable SuffixAccount loadData(User user) throws Exception {
        try (SQLQuery query = table.createQuery()
                .addCondition("user", user.getID())
                .setLimit(1).build().execute()) {
            ResultSet rs = query.getResultSet();
            if (rs.next()) {
                String suffix = rs.getString("suffix");
                String color = rs.getString("color");
                return new SuffixAccount(user, suffix, color == null ? null : ChatColor.getByChar(color.charAt(0)));
            }
        }
        return null;
    }

    @Override
    public void saveData(UserKey user, SuffixAccount handler) throws Exception {
        if (handler.getContent() == null || handler.getContent().isEmpty()) {
            table.createDelete().addCondition("user", user.id()).build().execute();
        } else {
            table.createInsert()
                    .setColumnNames("user", "suffix", "color")
                    .setParams(
                            user.id(), handler.getContent(),
                            handler.getColor() == null ? null : handler.getColor().getChar()
                    ).execute();
        }
    }

}