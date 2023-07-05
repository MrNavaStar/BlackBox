package me.mrnavastar.blackbox.api.server;

import com.google.gson.JsonArray;

public interface ServerUserCacheHandler {
    void saveUserCache(JsonArray userCache);
    JsonArray loadUserCache();
}
