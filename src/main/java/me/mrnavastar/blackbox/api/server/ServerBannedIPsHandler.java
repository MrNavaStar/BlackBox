package me.mrnavastar.blackbox.api.server;

import com.google.gson.JsonArray;

public interface ServerBannedIPsHandler {
    void saveBannedIPList(JsonArray array);
    JsonArray loadBannedIPList();
}
