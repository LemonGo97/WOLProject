package cn.lemongo97.wol.model.command;

import cn.lemongo97.wol.tools.MagicPacketSender;

import java.util.Map;

public class WakeBatchCommand extends WakeCommand {

    /**
     * k => IP Address
     * v => Mac Address
     */
    private Map<String,String> ipMacMapping;

    public Map<String, String> getIpMacMapping() {
        return ipMacMapping;
    }

    public void setIpMacMapping(Map<String, String> ipMacMapping) {
        this.ipMacMapping = ipMacMapping;
    }

    @Override
    protected void wake() {
        ipMacMapping.forEach((ip,mac) -> {
            MagicPacketSender.send(ip, mac, DEFAULT_PORT);
        });
    }
}
