package com.artformgames.plugin.usersuffix.migrator;

import com.artformgames.core.user.UserKey;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record MigrateCache(UserKey user,
                           String content, String formatColor) {
}
