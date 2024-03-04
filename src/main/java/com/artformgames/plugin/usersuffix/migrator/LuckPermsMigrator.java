package com.artformgames.plugin.usersuffix.migrator;

import cc.carm.lib.easysql.api.SQLQuery;
import com.artformgames.core.ArtCore;
import com.artformgames.core.user.UserKey;
import com.artformgames.plugin.usersuffix.user.SuffixLoader;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuckPermsMigrator {

    public record CachedRecord(UserKey user,
                               String content, String formatColor) {
    }

    // REGEX For  suffix.<weight>.<content>
    public static final Pattern PATTERN = Pattern.compile("^suffix\\.\\d+\\.(.*)$");

    // REGEX for [START-COLOR]<content>[END-COLOR] or [START-COLOR]<content> or <content>[END-COLOR] or <content>
    public static final Pattern CONTENT = Pattern.compile(
            "^(.*?)((?:&[0-9a-fk-or]|&\\(#[0-9a-fA-F]{6}\\))?)$"
    );

    public static int importData(String sourceTable, String usersTable, boolean purge) {
        Set<Integer> indexList = new HashSet<>();
        Set<CachedRecord> data = new HashSet<>();
        try (SQLQuery query = ArtCore.getSQLManager().createQuery().inTable(sourceTable)
                .selectColumns("id", "uuid", "permission")
                .build().execute()) {
            ResultSet rs = query.getResultSet();
            while (rs.next()) {
                String perm = rs.getString("permission");
                Matcher matcher = PATTERN.matcher(perm);
                if (!matcher.matches()) continue;

                UUID uuid = UUID.fromString(rs.getString("uuid"));
                String contentString = matcher.group(1);

                Matcher contentMatcher = CONTENT.matcher(contentString);
                if (!contentMatcher.matches()) continue;

                String content = contentMatcher.group(1);
                String formatColor = contentMatcher.group(2);

                if (content == null && formatColor == null) continue;

                String username = getUsername(usersTable, uuid);
                if (username == null) continue;

                UserKey key = importKey(uuid, username);
                if (key == null) continue;

                indexList.add(rs.getInt("id"));
                data.add(new CachedRecord(key, content, formatColor));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

        for (CachedRecord cache : data) { // Insert data into the new table
            SuffixLoader.TABLE.createReplace()
                    .setColumnNames("user", "content", "color")
                    .setParams(cache.user().id(), cache.content(), cache.content())
                    .execute(null);
        }

        if (purge) {
            for (Integer id : indexList) {
                ArtCore.getSQLManager().createDelete(sourceTable)
                        .addCondition("id", id)
                        .setLimit(1).build().execute(null);
            }
        }

        return data.size();
    }

    public static String getUsername(String playersTable, UUID uuid) {
        try (SQLQuery query = ArtCore.getSQLManager().createQuery().inTable(playersTable)
                .selectColumns("uuid", "username")
                .addCondition("uuid", uuid.toString())
                .setLimit(1).build().execute()) {
            ResultSet rs = query.getResultSet();
            if (rs.next()) {
                return rs.getString("username");
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static UserKey importKey(UUID uuid, String username) {
        try {
            return ArtCore.getUserManager().upsertKey(uuid, username);
        } catch (Exception e) {
            return null;
        }
    }


}
