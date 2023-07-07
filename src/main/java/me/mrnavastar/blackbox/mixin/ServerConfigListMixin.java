package me.mrnavastar.blackbox.mixin;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.ServerConfigList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

@Mixin(ServerConfigList.class)
public class ServerConfigListMixin {

    @Shadow @Final private File file;

    private final boolean bannedPlayersRegistered = file.equals(ServerConfigHandler.BANNED_PLAYERS_FILE) && BlackBox.serverBannedPlayersHandler != null;
    private final boolean bannedIpsRegistered = file.equals(ServerConfigHandler.BANNED_IPS_FILE) && BlackBox.serverBannedIPsHandler != null;
    private final boolean operatorsRegistered = file.equals(ServerConfigHandler.OPERATORS_FILE) && BlackBox.serverOperatorsHandler != null;
    private final boolean whitelistRegistered = file.equals(ServerConfigHandler.WHITE_LIST_FILE) && BlackBox.serverWhitelistHandler != null;

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Lcom/google/common/io/Files;newWriter(Ljava/io/File;Ljava/nio/charset/Charset;)Ljava/io/BufferedWriter;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true, remap = false)
    private void save(CallbackInfo ci, JsonArray jsonArray) {
        if (bannedPlayersRegistered) BlackBox.serverBannedPlayersHandler.saveBannedPlayersList(jsonArray);
        else if (bannedIpsRegistered) BlackBox.serverBannedIPsHandler.saveBannedIPList(jsonArray);
        else if (operatorsRegistered) BlackBox.serverOperatorsHandler.saveOperatorList(jsonArray);
        else if (whitelistRegistered) BlackBox.serverWhitelistHandler.saveWhitelist(jsonArray);
        ci.cancel();
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Ljava/io/File;exists()Z"))
    private boolean disableFileCheck(File instance) {
        if (bannedPlayersRegistered || bannedIpsRegistered || operatorsRegistered || whitelistRegistered) return true;
        return instance.exists();
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lcom/google/common/io/Files;newReader(Ljava/io/File;Ljava/nio/charset/Charset;)Ljava/io/BufferedReader;", remap = false))
    private BufferedReader disableReader(File file, Charset cs) throws FileNotFoundException {
        if (bannedPlayersRegistered || bannedIpsRegistered || operatorsRegistered || whitelistRegistered) return (BufferedReader) Reader.nullReader();
        return Files.newReader(file, cs);
    }

    @Redirect(method = "load", at = @At(value = "INVOKE", target = "Lcom/google/gson/Gson;fromJson(Ljava/io/Reader;Ljava/lang/Class;)Ljava/lang/Object;", remap = false))
    private <T> T load(Gson gson, Reader reader, Class<T> classOfT) {
        if (bannedPlayersRegistered) return (T) BlackBox.serverBannedPlayersHandler.loadBannedPlayersList();
        else if (bannedIpsRegistered) return (T) BlackBox.serverBannedIPsHandler.loadBannedIPList();
        else if (operatorsRegistered) return (T) BlackBox.serverOperatorsHandler.loadOperatorList();
        else if (whitelistRegistered) return (T) BlackBox.serverWhitelistHandler.loadWhitelist();
        return gson.fromJson(reader, classOfT);
    }
}