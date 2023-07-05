package me.mrnavastar.blackbox.api.server;

import com.google.gson.JsonArray;

public interface ServerOperatorsHandler {
    void saveOperatorList(JsonArray operatorList);
    JsonArray loadOperatorList();
}
