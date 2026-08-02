package net.blockhost.anarchymod;

//? if <=1.12.2 {
/*import io.netty.buffer.Unpooled;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;
*///?} elif <=1.13.2 {
/*import io.netty.buffer.Unpooled;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;
*///?} elif <1.14.4 {
/*import io.netty.buffer.Unpooled;
import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
import net.minecraft.util.PacketByteBuf;
*///?} elif <1.20.2 {
/*import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
*///?} else {
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//? } elif >=1.14.4 {
/*import net.minecraft.resources.ResourceLocation;
*///?} else {
/*import net.minecraft.util.Identifier;
*///?}

public final class JoinPayload {

    // ? if >=1.21.11 {
    public static final Identifier ID = Identifier.fromNamespaceAndPath("anarchymod", "join");
    // ? } elif >=1.21.7 {
    /*
     * public static final ResourceLocation ID =
     * ResourceLocation.fromNamespaceAndPath("anarchymod", "join");
     */// ?} elif >=1.19.4 {
    /*
     * public static final ResourceLocation ID =
     * ResourceLocation.tryBuild("anarchymod", "join");
     */// ?} elif >=1.14.4 {
    /*
     * public static final ResourceLocation ID = new ResourceLocation("anarchymod",
     * "join");
     */// ?} else {
    /*
     * public static final Identifier ID = new Identifier("anarchymod", "join");
     */// ?}

    private JoinPayload() {
    }

    // ? if <=1.12.2 {
    /*
     * public static CustomPayloadC2SPacket createPacket() {
     * return new CustomPayloadC2SPacket(ID.toString(), new
     * PacketByteBuf(Unpooled.EMPTY_BUFFER));
     * }
     */// ?} elif <1.14.4 {
    /*
     * public static CustomPayloadC2SPacket createPacket() {
     * return new CustomPayloadC2SPacket(ID, new
     * PacketByteBuf(Unpooled.EMPTY_BUFFER));
     * }
     */// ?} elif <1.20.2 {
    /*
     * public static ServerboundCustomPayloadPacket createPacket() {
     * return new ServerboundCustomPayloadPacket(ID, new
     * FriendlyByteBuf(Unpooled.EMPTY_BUFFER));
     * }
     */// ?} else {
    public static ServerboundCustomPayloadPacket createPacket() {
        return new ServerboundCustomPayloadPacket(new DiscardedPayload(ID));
    }
    // ?}
}
