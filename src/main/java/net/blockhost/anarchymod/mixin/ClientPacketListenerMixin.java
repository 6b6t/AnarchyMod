package net.blockhost.anarchymod.mixin;

import net.blockhost.anarchymod.Domains;
import net.blockhost.anarchymod.JoinPayload;
//? if <=1.13.2 {
/*import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.client.network.ServerInfo;
*///?} elif <1.14.4 {
/*import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.packet.GameJoinS2CPacket;
import net.minecraft.client.options.ServerEntry;
*///?} else {
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.logging.Logger;

//? if <1.14.4 {
/*@Mixin(ClientPlayNetworkHandler.class)
*///?} else {
@Mixin(ClientPacketListener.class)
// ?}
public class ClientPacketListenerMixin {

    private static final Logger LOGGER = Logger.getLogger("AnarchyMod-JoinNotifier");

    // ? if <=1.13.2 {
    /*
     * @Inject(method = "onGameJoin", at = @At("RETURN"))
     * private void afterLogin(GameJoinS2CPacket packet, CallbackInfo ci) {
     * MinecraftClient client = MinecraftClient.getInstance();
     * ServerInfo server = client.getCurrentServerEntry();
     * if (server == null || !Domains.contains(server.address)) {
     * return;
     * }
     *
     * try {
     * ClientPlayNetworkHandler listener = (ClientPlayNetworkHandler) (Object) this;
     * listener.getClientConnection().send(JoinPayload.createPacket());
     * } catch (RuntimeException error) {
     * // Analytics must never be able to break an otherwise successful connection.
     * LOGGER.warning("Failed to send join notification to " + server.address + ": "
     * + error.getMessage());
     * }
     * }
     */// ?} elif <1.14.4 {
    /*
     * @Inject(method = "onGameJoin", at = @At("RETURN"))
     * private void afterLogin(GameJoinS2CPacket packet, CallbackInfo ci) {
     * MinecraftClient client = MinecraftClient.getInstance();
     * ServerEntry server = client.getCurrentServerEntry();
     * if (server == null || !Domains.contains(server.address)) {
     * return;
     * }
     *
     * try {
     * ClientPlayNetworkHandler listener = (ClientPlayNetworkHandler) (Object) this;
     * listener.getClientConnection().send(JoinPayload.createPacket());
     * } catch (RuntimeException error) {
     * // Analytics must never be able to break an otherwise successful connection.
     * LOGGER.warning("Failed to send join notification to " + server.address + ": "
     * + error.getMessage());
     * }
     * }
     */// ?} else {
    @Inject(method = "handleLogin", at = @At("RETURN"))
    private void afterLogin(ClientboundLoginPacket packet, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        ServerData server = client.getCurrentServer();
        if (server == null || !Domains.contains(server.ip)) {
            return;
        }

        try {
            ClientPacketListener listener = (ClientPacketListener) (Object) this;
            listener.getConnection().send(JoinPayload.createPacket());
        } catch (RuntimeException error) {
            // Analytics must never be able to break an otherwise successful connection.
            LOGGER.warning("Failed to send join notification to " + server.ip + ": " + error.getMessage());
        }
    }
    // ?}
}
