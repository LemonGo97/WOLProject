package cn.lemongo97.wol.model.command;


public class ScanCommand extends Command{
    private String ipRange;

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }
}
