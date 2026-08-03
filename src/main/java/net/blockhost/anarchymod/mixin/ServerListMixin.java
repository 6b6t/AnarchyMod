package net.blockhost.anarchymod.mixin;

import net.blockhost.anarchymod.Domains;
//? if <=1.13.2 {
/*import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
*///?} elif <1.14.4 {
/*import net.minecraft.client.options.ServerEntry;
import net.minecraft.client.options.ServerList;
*///?} else {
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
//?}
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

    //? if <=1.13.2 {
    /*@Shadow
    @Final
    private List<ServerInfo> servers;
    *///?} elif <1.14.4 {
    /*@Shadow
    @Final
    private List<ServerEntry> serverEntries;
    *///?} else {
    @Shadow
    @Final
    private List<ServerData> serverList;
    //?}

    //? if <=1.13.2 {
    /*@Inject(method = "loadFile", at = @At("RETURN"))
    public void afterLoad(CallbackInfo ci) {
        if (servers.stream().noneMatch(data -> Domains.matches(data.address, "*." + DEFAULT_SERVER_ADDRESS))) {
            servers.add(0, new ServerInfo("6b6t", DEFAULT_SERVER_ADDRESS, false));
        }
    }
    *///?} elif <1.14.4 {
    /*@Inject(method = "loadFile", at = @At("RETURN"))
    public void afterLoad(CallbackInfo ci) {
        if (serverEntries.stream().noneMatch(data -> Domains.matches(data.address, "*." + DEFAULT_SERVER_ADDRESS))) {
            serverEntries.add(0, new ServerEntry("6b6t", DEFAULT_SERVER_ADDRESS, false));
        }
    }
    *///?} else {
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
    //?}
}
