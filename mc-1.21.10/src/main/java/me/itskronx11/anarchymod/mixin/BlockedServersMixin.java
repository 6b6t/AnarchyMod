package me.itskronx11.anarchymod.mixin;

import com.mojang.patchy.BlockedServers;
import me.itskronx11.anarchymod.Domains;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockedServers.class)
public class BlockedServersMixin {

    @Inject(method = "isBlockedServerHostName", at = @At("TAIL"), cancellable = true, remap = false)
    public void isBlockedServerHostName(String server, CallbackInfoReturnable<Boolean> cir) {
        boolean contains = Domains.contains(server);
        if (contains) {
            cir.setReturnValue(false);
        }
    }

}
