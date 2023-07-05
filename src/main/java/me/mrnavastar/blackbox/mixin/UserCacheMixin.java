package me.mrnavastar.blackbox.mixin;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.util.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.DateFormat;

@Mixin(UserCache.class)
public class UserCacheMixin {

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;toJson(Lcom/google/gson/JsonElement;)Ljava/lang/String;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void save(CallbackInfo ci, JsonArray jsonArray, DateFormat dateFormat) {
        if (BlackBox.serverUserCacheHandler == null) return;
        BlackBox.serverUserCacheHandler.saveUserCache(jsonArray);
        ci.cancel();
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lcom/google/common/io/Files;newReader(Ljava/io/File;Ljava/nio/charset/Charset;)Ljava/io/BufferedReader;"))
    private BufferedReader disableReader(File file, Charset cs) throws FileNotFoundException {
        if (BlackBox.serverUserCacheHandler != null) return (BufferedReader) Reader.nullReader();
        return Files.newReader(file, cs);
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;"))
    private <T> T load(Gson gson, Reader reader, Class<T> classOfT) {
        if (BlackBox.serverUserCacheHandler != null) return (T) BlackBox.serverUserCacheHandler.loadUserCache();
        return gson.fromJson(reader, classOfT);
    }
}