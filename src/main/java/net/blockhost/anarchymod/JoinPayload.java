package net.blockhost.anarchymod;

//? if <1.20.5 {
/*import net.minecraft.resources.ResourceLocation;

public class JoinPayload {

    public static final ResourceLocation ID = new ResourceLocation("anarchymod", "join");
}
*///?} else {
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//? } else {
/*import net.minecraft.resources.ResourceLocation;
*///?}

public class JoinPayload implements CustomPacketPayload {

    //? if >=1.21.11 {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("anarchymod", "join");
    //? } elif >=1.21.7 {
    /*public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("anarchymod", "join");
    *///?} else {
    /*public static final ResourceLocation ID = ResourceLocation.tryBuild("anarchymod", "join");
    *///?}

    public static final StreamCodec<FriendlyByteBuf, JoinPayload> CODEC =
        StreamCodec.of(
            (payload, buf) -> {
            },
            buf -> new JoinPayload()
        );

    public static final Type<JoinPayload> TYPE =
        new Type<>(ID);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
//?}
