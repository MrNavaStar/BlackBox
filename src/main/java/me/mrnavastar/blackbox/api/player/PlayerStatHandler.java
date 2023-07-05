package me.mrnavastar.blackbox.api.player;

import net.minecraft.entity.player.PlayerEntity;

public interface PlayerStatHandler {
    void saveStatData(PlayerEntity player, String statData);
    String loadStatData(PlayerEntity player);
}
