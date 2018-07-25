package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.GBT26875Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class GBT26875RequestDecoderTest {
    // Little-endian
    byte[] outData = {
            (byte)64,
            (byte)64,
            (byte)0xaa, // seq no
            (byte)0xbb,
            (byte)0x01, // version
            (byte)0x01,
            (byte)0xf1, // time tab
            (byte)0xf2,
            (byte)0xf3,
            (byte)0xf4,
            (byte)0xf5,
            (byte)0xf6,
            (byte)0xc1, // source addr
            (byte)0xc2,
            (byte)0xc3,
            (byte)0xc4,
            (byte)0xc5,
            (byte)0xc6,
            (byte)0xd1, // dest addr
            (byte)0xd2,
            (byte)0xd3,
            (byte)0xd4,
            (byte)0xd5,
            (byte)0xd6,
            (byte)5,       // data len
            (byte)0,
            (byte)0x01,    // cmd
            (byte)0xf1,    // data
            (byte)0xf2,
            (byte)0xf3,
            (byte)0xf4,
            (byte)0xf5,
            (byte)0xee, // crc, dummy value
            (byte)35,
            (byte)35
    } ;

    @Test
    public void GBT26875Test()
    {
        // given
        EmbeddedChannel channel = new EmbeddedChannel( new MessageEncoder(), new GBT26875RequestMessageDecoder() );
        // when
        channel.writeOutbound( outData );
        channel.writeInbound( channel.readOutbound() );
        // then
        GBT26875Message requesMessage = (GBT26875Message) channel.readInbound();
        assertNotNull(requesMessage);
        assertEquals( requesMessage.getSeqNo(), 0xaabb );
    }

    static class MessageEncoder extends MessageToMessageEncoder<byte[]>
    {
        @Override
        protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out ) throws Exception
        {
            ByteBuf buf = ctx.alloc().buffer();
            buf.writeBytes(msg) ;
            out.add( buf );
        }
    }
}
