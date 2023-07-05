package me.mrnavastar.blackbox.mixin;

import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.stat.ServerStatHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;

@Mixin(ServerStatHandler.class)
public abstract class ServerStatHandlerMixin {

    @Shadow protected abstract String asString();

    public String toString() {
        return asString();
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/io/File;isFile()Z"))
    private boolean disableFileCheck(File file) {
        if (BlackBox.playerStatHandler != null) return false;
        return file.isFile();
    }
}