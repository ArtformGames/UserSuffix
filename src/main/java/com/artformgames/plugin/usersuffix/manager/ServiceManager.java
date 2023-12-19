package com.artformgames.plugin.usersuffix.manager;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.SuffixNode;
import net.luckperms.api.platform.PlayerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.SortedMap;

/**
 * 服务管理器，旨在于LuckPerms互联，调用其原始接口。
 */
public class ServiceManager {

    public static User getUser(Player player) {
        return getAPI().getUser(player);
    }

    public static LuckPerms getService() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            return provider.getProvider();
        } else {
            return LuckPermsProvider.get();
        }
    }

    public static PlayerAdapter<Player> getAPI() {
        return getService().getPlayerAdapter(Player.class);
    }


    /**
     * 通过LuckPermsAPI判断玩家是否有对应的权限
     *
     * @param user       用户
     * @param permission 权限
     * @return true / false
     */
    public static boolean hasPermission(User user, String permission) {
        return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
    }

    @Nullable
    public static String getSuffix(Player player) {
        return getAPI().getMetaData(player).getSuffix();
    }

    @NotNull
    public static SortedMap<Integer, String> getSuffixData(Player player) {
        return getAPI().getMetaData(player).getSuffixes();
    }

    public static void setSuffixData(Player player, @Nullable String content, int weight) {
        User user = getUser(player);
        clearSuffixData(player); // 清除掉旧的数据，LuckPerms不会去覆盖一个Meta，需要手动清除。
        if (content != null) user.data().add(SuffixNode.builder(content, weight).build());
        ServiceManager.getService().getUserManager().saveUser(user); // 保存数据
    }

    public static void clearSuffixData(Player player) {
        User user = getUser(player);
        user.data().clear(NodeType.SUFFIX.predicate());
    }

}
