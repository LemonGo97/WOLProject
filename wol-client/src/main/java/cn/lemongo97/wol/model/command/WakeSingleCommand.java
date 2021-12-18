package cn.lemongo97.wol.model.command;

import cn.lemongo97.wol.tools.MagicPacketSender;

public class WakeSingleCommand extends WakeCommand{
    private String ip;
    private String mac;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    protected void wake() {
        MagicPacketSender.send(ip, mac, DEFAULT_PORT);
    }
}
