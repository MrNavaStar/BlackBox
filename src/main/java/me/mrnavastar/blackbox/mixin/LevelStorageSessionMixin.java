package me.mrnavastar.blackbox.mixin;

import me.mrnavastar.blackbox.BlackBox;
import me.mrnavastar.blackbox.util.CustomWorldSaveHandler;
import net.minecraft.datafixer.Schemas;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelStorage.Session.class)
public class LevelStorageSessionMixin {

    @Inject(method = "createSaveHandler", at = @At("TAIL"), cancellable = true)
    private void createSaveHandler(CallbackInfoReturnable<WorldSaveHandler> cir) {
        if (BlackBox.playerDataHandler != null) cir.setReturnValue(new CustomWorldSaveHandler(((LevelStorage.Session)(Object) this), Schemas.getFixer()));
    }
}