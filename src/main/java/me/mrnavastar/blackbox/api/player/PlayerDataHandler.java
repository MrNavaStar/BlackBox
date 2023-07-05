package me.mrnavastar.blackbox.api.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public interface PlayerDataHandler {
    void savePlayerData(PlayerEntity player, NbtCompound playerData);
    NbtCompound loadPlayerData(PlayerEntity player);

    String[] getSavedPlayerUUIDS();
}