package net.blockhost.anarchymod;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class AnarchyMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            if (client.getCurrentServer() != null && Domains.contains(client.getCurrentServer().ip))
                ClientPlayNetworking.send(JoinPayload.ID, new FriendlyByteBuf(Unpooled.buffer()));
        });
    }
}
