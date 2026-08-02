package net.blockhost.anarchymod;

import io.netty.buffer.Unpooled;
//? if <1.14.4 {
/*import net.minecraft.util.PacketByteBuf;
*///?} else {
import net.minecraft.network.FriendlyByteBuf;
//?}
//? if <=1.13.2 {
/*import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
*///?} elif <1.14.4 {
/*import net.minecraft.server.network.packet.CustomPayloadC2SPacket;
*///?} elif <1.20.2 {
/*import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
*///?} else {
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
//?}
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JoinPayloadTest {

    @Test
    void encodesAnEmptyJoinPayload() throws Exception {
        // ? if <1.14.4 {
        /*
         * PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
         */// ?} else {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
        // ?}

        try {
            // ? if <1.14.4 {
            /*
             * CustomPayloadC2SPacket packet = JoinPayload.createPacket();
             */// ?} else {
            ServerboundCustomPayloadPacket packet = JoinPayload.createPacket();
            // ?}
            // ? if <1.20.5 {
            /*
             * packet.write(buffer);
             */// ?} else {
            ServerboundCustomPayloadPacket.STREAM_CODEC.encode(buffer, packet);
            // ?}

            // ? if >=1.21.11 {
            assertEquals(JoinPayload.ID, buffer.readIdentifier());
            // ?} elif >=1.14.4 {
            /*
             * assertEquals(JoinPayload.ID, buffer.readResourceLocation());
             */// ?} elif >1.12.2 {
            /*
             * assertEquals(JoinPayload.ID, buffer.readIdentifier());
             */// ?} else {
            /*
             * assertEquals(JoinPayload.ID.toString(), buffer.readString(32767));
             */// ?}
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }
}
