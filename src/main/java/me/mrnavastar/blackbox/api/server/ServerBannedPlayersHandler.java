package me.mrnavastar.blackbox.api.server;

import com.google.gson.JsonArray;

public interface ServerBannedPlayersHandler {
    void saveBannedPlayersList(JsonArray bannedPlayers);
    JsonArray loadBannedPlayersList();
}
