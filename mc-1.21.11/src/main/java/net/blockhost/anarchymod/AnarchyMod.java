package net.blockhost.anarchymod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class AnarchyMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PayloadTypeRegistry.playC2S().register(JoinPayload.TYPE, JoinPayload.CODEC);
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            if (client.getCurrentServer() != null && Domains.contains(client.getCurrentServer().ip))
                sender.sendPacket(new JoinPayload());
        });
    }


}
