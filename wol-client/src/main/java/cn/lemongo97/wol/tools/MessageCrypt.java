package cn.lemongo97.wol.tools;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageCrypt implements InitializingBean {
    @Value("${wol.clientId}")
    private String clientId;
    @Value("${wol.clientKey}")
    private String clientKey;

    private AES aes;

    public String encode(String message){
        return aes.encryptBase64(message);
    }

    public String decode(String message){
        return aes.decryptStr(message);
    }

    @Override
    public void afterPropertiesSet() {
        byte[] key = Base64.decode(clientKey);
        byte[] iv = new byte[16];

        byte[] clientIdBytes = clientId.getBytes();
        for (int i = 0; i < clientIdBytes.length; i+=2) {
            iv[i/2] = (byte) (clientIdBytes[i] & clientIdBytes[i+1]);
        }
        this.aes = new AES(Mode.CBC, Padding.PKCS5Padding, key, iv);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
