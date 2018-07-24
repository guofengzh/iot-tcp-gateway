package com.mtoliv.iot.server.codec;

import com.mtoliv.iot.server.message.GBT26875RequesMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GBT26875RequestDecoderTest {
    byte[] outData = {
            (byte)64,
            (byte)64,
            (byte)0xaa, // seq no
            (byte)0xbb,
            (byte)0x01, // version
            (byte)0x01,
            (byte)0xff, // time tab
            (byte)0xff,
            (byte)0xff,
            (byte)0xcc, // source addr
            (byte)0xcc,
            (byte)0xcc,
            (byte)0xdd, // dest addr
            (byte)0xdd,
            (byte)0xdd,
            (byte)5,       // data len
            (byte)0x01,    // cmd
            (byte)0xf1,    // data
            (byte)0xf2,
            (byte)0xf3,
            (byte)0xf4,
            (byte)0xf5,
            (byte)0xee, // crc, dummy value
            (byte)(byte)35,
            (byte)(byte)35
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
        GBT26875RequesMessage requesMessage = (GBT26875RequesMessage) channel.readInbound();
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
