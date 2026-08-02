package net.blockhost.anarchymod.mixin;

import net.blockhost.anarchymod.Domains;
import net.blockhost.anarchymod.JoinPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.logging.Logger;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    private static final Logger LOGGER = Logger.getLogger("AnarchyMod-JoinNotifier");

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
}
