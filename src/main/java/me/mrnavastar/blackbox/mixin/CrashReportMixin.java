package me.mrnavastar.blackbox.mixin;

import me.mrnavastar.blackbox.BlackBox;
import net.minecraft.util.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(CrashReport.class)
public abstract class CrashReportMixin {

    @Shadow public abstract String asString();

    @Inject(method = "writeToFile", at = @At("HEAD"), cancellable = true)
    private void save(File file, CallbackInfoReturnable<Boolean> cir) {
        if (BlackBox.crashReportHandler == null) return;
        BlackBox.crashReportHandler.saveCrashReport(asString());
        cir.setReturnValue(true);
    }
}