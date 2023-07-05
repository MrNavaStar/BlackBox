package me.mrnavastar.blackbox.api.player;

import com.google.gson.JsonElement;
import net.minecraft.entity.player.PlayerEntity;

public interface PlayerAdvancementHandler {
    void saveAdvancementData(PlayerEntity player, JsonElement advancementData);
    JsonElement loadAdvancementData(PlayerEntity player);
}
