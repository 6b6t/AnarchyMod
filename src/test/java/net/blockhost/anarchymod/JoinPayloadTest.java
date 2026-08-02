package net.blockhost.anarchymod;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
//? if <1.20.2 {
/*import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
*///?} else {
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
//?}
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JoinPayloadTest {

    @Test
    void encodesAnEmptyJoinPayload() {
        FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());

        try {
            ServerboundCustomPayloadPacket packet = JoinPayload.createPacket();
            //? if <1.20.5 {
            /*packet.write(buffer);
            *///?} else {
            ServerboundCustomPayloadPacket.STREAM_CODEC.encode(buffer, packet);
            //?}

            //? if >=1.21.11 {
            assertEquals(JoinPayload.ID, buffer.readIdentifier());
            //?} else {
            /*assertEquals(JoinPayload.ID, buffer.readResourceLocation());
            *///?}
            assertFalse(buffer.isReadable());
        } finally {
            buffer.release();
        }
    }
}
