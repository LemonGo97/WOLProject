package cn.lemongo97.wol.model.command;

import cn.lemongo97.wol.tools.ArpScanner;

public class ScanCommand extends Command{
    private String ipRange;

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }

    @Override
    public Object doCommand() {
        return ArpScanner.scan();
    }
}
