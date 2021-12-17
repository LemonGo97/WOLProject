package cn.lemongo97.wol;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

/**
 * @author lemongo97
 */
public class Tests {

    public static void main(String[] args) {
        String mac = ArpHandler.scan().get("192.168.31.97");
        Console.log(mac);
        wsTest();
        sendMagicPacket("192.168.31.97", mac);
    }

    private static void wsTest() {
        String uuid = UUID.randomUUID().toString(true);
        byte[] bytes = uuid.getBytes();
        byte[] aaa = new byte[16];
        for (int i = 0; i < bytes.length; i+=2) {
            aaa[i/2] = (byte) (bytes[i] & bytes[i+1]);
        }
        String clientId = Base64.encode(aaa);
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        String clientKey = Base64.encode(key);
        String encrypt = new AES(Mode.CBC, Padding.PKCS5Padding,key,Base64.decode(clientId)).encryptBase64("123");
        String decrypt = new AES(Mode.CBC, Padding.PKCS5Padding,key,Base64.decode(clientId)).decryptStr(encrypt);
        Console.log("clientId: ", clientId);
        Console.log("clientKey: ", clientKey);
        Console.log("encrypt: ", encrypt);
        Console.log("decrypt: ", decrypt);
    }

    private static void sendMagicPacket(String ip, String mac) {
        MagicPacketSender.send(ip, mac, 9);
    }


}
