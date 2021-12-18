package cn.lemongo97.wol.tools;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.HexUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author lemongo97
 */
public class MagicPacketSender {
    private final static EventLoopGroup eventLoopGroup;
    private static Channel channel = null;

    static {
        eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(eventLoopGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new MessageToMessageEncoder<MagicPacket>() {
                        @Override
                        protected void encode(ChannelHandlerContext context, MagicPacket magicPacket, List<Object> out) {
                            ByteBuf buf = context.alloc().buffer(magicPacket.getMessage().length);
                            buf.writeBytes(magicPacket.getMessage());
                            out.add(new DatagramPacket(buf, magicPacket.getRemoteAddress()));
                        }
                    });
            channel = bootstrap.bind(0).sync().channel();
        } catch (InterruptedException e) {
            Console.log(e);
        }
    }

    public static void send(String ip, String mac, int port) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append("FF");
        }
        for (int i = 0; i < 16; i++) {
            builder.append(mac);
        }
        mac = builder.toString();
        byte[] magicBytes = HexUtil.decodeHex(mac);
        channel.writeAndFlush(new MagicPacket(magicBytes, new InetSocketAddress(ip, port)));
    }

    public static void shutdown() {
        eventLoopGroup.shutdownGracefully();
    }

    private static class MagicPacket {

        private byte[] message;
        private InetSocketAddress remoteAddress;

        public MagicPacket(byte[] message, InetSocketAddress remoteAddress) {
            this.message = message;
            this.remoteAddress = remoteAddress;
        }

        public byte[] getMessage() {
            return message;
        }

        public void setMessage(byte[] message) {
            this.message = message;
        }

        public InetSocketAddress getRemoteAddress() {
            return remoteAddress;
        }

        public void setRemoteAddress(InetSocketAddress remoteAddress) {
            this.remoteAddress = remoteAddress;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MagicPacket that = (MagicPacket) o;
            return Arrays.equals(message, that.message) && Objects.equals(remoteAddress, that.remoteAddress);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(remoteAddress);
            result = 31 * result + Arrays.hashCode(message);
            return result;
        }
    }
}
