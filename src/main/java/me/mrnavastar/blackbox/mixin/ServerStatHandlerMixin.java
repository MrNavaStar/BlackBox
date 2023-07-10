package me.mrnavastar.blackbox.mixin;

import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.stat.ServerStatHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(ServerStatHandler.class)
public abstract class ServerStatHandlerMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/io/File;isFile()Z"))
    private boolean disableFileCheck(File file) {
        if (BlackBox.playerStatHandler != null) return false;
        return file.isFile();
    }
}