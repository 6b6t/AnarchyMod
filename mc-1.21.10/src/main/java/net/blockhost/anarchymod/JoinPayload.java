package net.blockhost.anarchymod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class JoinPayload implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("anarchymod", "join");

    public static final StreamCodec<FriendlyByteBuf, JoinPayload> CODEC =
            StreamCodec.of(
                    (payload, buf) -> {},
                    buf -> new JoinPayload()
            );

    public static final Type<JoinPayload> TYPE =
            new Type<>(ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
