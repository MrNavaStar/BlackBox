package me.mrnavastar.blackbox.util;

import com.mojang.datafixers.DataFixer;
import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.level.storage.LevelStorage;
import org.jetbrains.annotations.Nullable;

public class CustomWorldSaveHandler extends WorldSaveHandler {

    public CustomWorldSaveHandler(LevelStorage.Session session, DataFixer dataFixer) {
        super(session, dataFixer);
    }

    @Override
    public void savePlayerData(PlayerEntity player) {
        NbtCompound playerData = player.writeNbt(new NbtCompound());
        BlackBox.playerDataHandler.savePlayerData(player, playerData);
    }

    @Nullable
    @Override
    public NbtCompound loadPlayerData(PlayerEntity player) {
        NbtCompound playerData = BlackBox.playerDataHandler.loadPlayerData(player);

        if (playerData != null) {
            int i = NbtHelper.getDataVersion(playerData, -1);
            player.readNbt(DataFixTypes.PLAYER.update(this.dataFixer, playerData, i));
        }

        return playerData;
    }

    @Override
    public String[] getSavedPlayerIds() {
        return BlackBox.playerDataHandler.getSavedPlayerUUIDS();
    }
}
