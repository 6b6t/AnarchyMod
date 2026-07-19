package net.blockhost.anarchymod.mixin;

import net.blockhost.anarchymod.Domains;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerList.class)
public class ServerListMixin {

    @Unique
    private static final String DEFAULT_SERVER_ADDRESS = "6b6t.org";

    @Shadow
    @Final
    private List<ServerData> serverList;

    @Inject(method = "load", at = @At("RETURN"))
    public void afterLoad(CallbackInfo ci) {
        if (serverList.stream().noneMatch(data -> Domains.matches(data.ip, "*." + DEFAULT_SERVER_ADDRESS))) {
            //? if <1.20.2 {
            /*serverList.add(0, new ServerData("6b6t", DEFAULT_SERVER_ADDRESS, false));
            *///? } elif <1.20.5 {
            /*serverList.add(0, new ServerData("6b6t", DEFAULT_SERVER_ADDRESS, ServerData.Type.OTHER));
            *///?} else {
            serverList.addFirst(new ServerData("6b6t", DEFAULT_SERVER_ADDRESS, ServerData.Type.OTHER));
            //?}
        }
    }
}
