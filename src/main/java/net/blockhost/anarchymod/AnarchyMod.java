package net.blockhost.anarchymod;

//? if <1.20.5 {
/*import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
*///?} else {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//?}
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.multiplayer.ServerData;

import java.util.logging.Logger;

public class AnarchyMod implements ClientModInitializer {

    private static final Logger LOGGER = Logger.getLogger("AnarchyMod");

    @Override
    public void onInitializeClient() {
        Domains.initialize();

        //? if >=26.1 {
        PayloadTypeRegistry.serverboundPlay().register(JoinPayload.TYPE, JoinPayload.CODEC);
        //?} else if >=1.20.5 {
        /*PayloadTypeRegistry.playC2S().register(JoinPayload.TYPE, JoinPayload.CODEC);
        *///?}
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            ServerData server = client.getCurrentServer();
            if (server == null || !Domains.contains(server.ip)) {
                return;
            }

            try {
                //? if <1.20.5 {
                /*ClientPlayNetworking.send(JoinPayload.ID, PacketByteBufs.empty());
                *///?} else {
                sender.sendPacket(new JoinPayload());
                //?}
            } catch (RuntimeException error) {
                // Analytics must never be able to break an otherwise successful connection.
                LOGGER.warning("Failed to send join notification to " + server.ip + ": " + error.getMessage());
            }
        });
    }
}
