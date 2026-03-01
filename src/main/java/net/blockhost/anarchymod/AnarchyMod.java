package net.blockhost.anarchymod;

//? if <1.20.5 {
/*import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
*///?} else {
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
//?}
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class AnarchyMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //? if >=1.20.5 {
        PayloadTypeRegistry.playC2S().register(JoinPayload.TYPE, JoinPayload.CODEC);
        //?}
        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            if (client.getCurrentServer() != null && Domains.contains(client.getCurrentServer().ip))
                //? if <1.20.5 {
                /*ClientPlayNetworking.send(JoinPayload.ID, new FriendlyByteBuf(Unpooled.buffer()));
                *///?} else {
                sender.sendPacket(new JoinPayload());
                //?}
        });
    }
}
