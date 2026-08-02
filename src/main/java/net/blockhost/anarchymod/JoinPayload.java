package net.blockhost.anarchymod;

//? if <1.20.2 {
/*import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
*///?} else {
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//? } else {
/*import net.minecraft.resources.ResourceLocation;
*///?}

public final class JoinPayload {

    //? if >=1.21.11 {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("anarchymod", "join");
    //? } elif >=1.21.7 {
    /*public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("anarchymod", "join");
    *///?} else {
    /*public static final ResourceLocation ID = ResourceLocation.tryBuild("anarchymod", "join");
    *///?}

    private JoinPayload() {
    }

    public static ServerboundCustomPayloadPacket createPacket() {
        //? if <1.20.2 {
        /*return new ServerboundCustomPayloadPacket(ID, new FriendlyByteBuf(Unpooled.EMPTY_BUFFER));
        *///?} else {
        return new ServerboundCustomPayloadPacket(new DiscardedPayload(ID));
        //?}
    }
}
