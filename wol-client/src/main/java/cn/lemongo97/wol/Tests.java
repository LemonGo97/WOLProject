package cn.lemongo97.wol;

import java.util.Map;

public class Tests {


    public static void main(String[] args) {
        Map<String, String> arpTable = ArpHandler.scan();
        sendMagicPacket("192.168.31.97", "04421A1FC012");
    }

    private static void sendMagicPacket(String ip, String mac) {
        MagicPacketSender.send(ip, mac, 9);
    }


}
