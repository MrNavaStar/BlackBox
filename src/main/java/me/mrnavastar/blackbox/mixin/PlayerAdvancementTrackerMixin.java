package me.mrnavastar.blackbox.mixin;

import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Map;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {

    @Shadow private ServerPlayerEntity owner;

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/PathUtil;createDirectories(Ljava/nio/file/Path;)V", remap = false), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void savePlayerAdvancementData(CallbackInfo ci, Map map, JsonElement jsonElement) {
        if (BlackBox.playerAdvancementHandler == null) return;
        BlackBox.playerAdvancementHandler.saveAdvancementData(owner, jsonElement);
        ci.cancel();
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ljava/nio/file/Files;isRegularFile(Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z"))
    private boolean disableFileCheck(Path provider, LinkOption[] linkOptions) {
        if (BlackBox.playerAdvancementHandler != null) return true;
        return Files.isRegularFile(provider, linkOptions);
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ljava/nio/file/Files;newBufferedReader(Ljava/nio/file/Path;Ljava/nio/charset/Charset;)Ljava/io/BufferedReader;"))
    private BufferedReader disableJsonReader(Path path, Charset cs) throws IOException {
        if (BlackBox.playerAdvancementHandler != null) return new BufferedReader(Reader.nullReader());
        return Files.newBufferedReader(path, cs);
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lcom/google/gson/internal/Streams;parse(Lcom/google/gson/stream/JsonReader;)Lcom/google/gson/JsonElement;", remap = false))
    private JsonElement loadPlayerAdvancementData(JsonReader jsonReader) {
        if (BlackBox.playerAdvancementHandler != null) return BlackBox.playerAdvancementHandler.loadAdvancementData(owner);
        return Streams.parse(jsonReader);
    }
}