package me.mrnavastar.blackbox.api.server;

import com.google.gson.JsonArray;

public interface ServerWhitelistHandler {
    void saveWhitelist(JsonArray whitelist);
    JsonArray loadWhitelist();
}
