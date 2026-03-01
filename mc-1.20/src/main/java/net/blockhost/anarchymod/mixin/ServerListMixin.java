package net.blockhost.anarchymod.mixin;

import net.blockhost.anarchymod.Domains;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerList.class)
public class ServerListMixin {

    @Shadow
    @Final
    private List<ServerData> serverList;

    @Inject(method = "load", at = @At("RETURN"))
    public void afterLoad(CallbackInfo ci) {
        if (serverList.stream().noneMatch(data -> Domains.contains(data.ip))) {
            serverList.add(0, new ServerData("6b6t", "6b6t.org", false));
        }
    }

}
