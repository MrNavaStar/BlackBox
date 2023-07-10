package me.mrnavastar.blackbox.mixin;

import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.ServerStatHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Shadow @Final private MinecraftServer server;

    @Shadow @Final private Map<UUID, ServerStatHandler> statisticsMap;

    @Inject(method = "savePlayerData", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private void saveStatData(ServerPlayerEntity player, CallbackInfo ci) {
        if (BlackBox.playerStatHandler != null) BlackBox.playerStatHandler.saveStatData(player, statisticsMap.get(player.getUuid()).asString());
    }

    @Redirect(method = "savePlayerData", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private Object disableRegularStatDataSave(Map instance, Object uuid) {
        if (BlackBox.playerStatHandler != null) return null;
        return instance.get(uuid);
    }

    @Inject(method = "createStatHandler", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void loadStatData(PlayerEntity player, CallbackInfoReturnable<ServerStatHandler> cir, UUID uuid, ServerStatHandler serverStatHandler, File file, File file2) {
        if (BlackBox.playerStatHandler != null) serverStatHandler.parse(server.getDataFixer(), BlackBox.playerStatHandler.loadStatData(player));
    }
}
