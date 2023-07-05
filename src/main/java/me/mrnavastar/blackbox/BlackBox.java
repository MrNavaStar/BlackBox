package me.mrnavastar.blackbox;

import me.mrnavastar.blackbox.api.player.PlayerAdvancementHandler;
import me.mrnavastar.blackbox.api.player.PlayerDataHandler;
import me.mrnavastar.blackbox.api.player.PlayerStatHandler;
import me.mrnavastar.blackbox.api.server.*;
import me.mrnavastar.blackbox.api.universal.CrashReportHandler;
import net.fabricmc.api.ModInitializer;

public class BlackBox implements ModInitializer {

    public static PlayerDataHandler playerDataHandler = null;
    public static PlayerAdvancementHandler playerAdvancementHandler = null;
    public static PlayerStatHandler playerStatHandler = null;

    public static ServerBannedPlayersHandler serverBannedPlayersHandler = null;
    public static ServerBannedIPsHandler serverBannedIPsHandler = null;
    public static ServerOperatorsHandler serverOperatorsHandler = null;
    public static ServerWhitelistHandler serverWhitelistHandler = null;
    public static ServerUserCacheHandler serverUserCacheHandler = null;

    public static CrashReportHandler crashReportHandler = null;

    @Override
    public void onInitialize() {

    }

    public static void registerHandler(PlayerDataHandler handler) {
        playerDataHandler = handler;
    }

    public static void registerHandler(PlayerAdvancementHandler handler) {
        playerAdvancementHandler = handler;
    }

    public static void registerHandler(PlayerStatHandler handler) {
        playerStatHandler = handler;
    }

    public static void registerHandler(ServerBannedPlayersHandler handler) {
        serverBannedPlayersHandler = handler;
    }

    public static void registerHandler(ServerBannedIPsHandler handler) {
        serverBannedIPsHandler = handler;
    }

    public static void registerHandler(ServerOperatorsHandler handler) {
        serverOperatorsHandler = handler;
    }

    public static void registerHandler(ServerWhitelistHandler handler) {
        serverWhitelistHandler = handler;
    }

    public static void registerHandler(ServerUserCacheHandler handler) {
        serverUserCacheHandler = handler;
    }

    public static void registerHandler(CrashReportHandler handler) {
        crashReportHandler = handler;
    }
}