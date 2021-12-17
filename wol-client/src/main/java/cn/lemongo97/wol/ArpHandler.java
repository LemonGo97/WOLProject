package cn.lemongo97.wol;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RuntimeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lemongo97
 */
public class ArpHandler {

    private final static Pattern IP_MAC_PATTERN = Pattern.compile(".*?((?:(?:2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(?:2[0-4]\\d|25[0-5]|[01]?\\d\\d?)).*?(\\w{2}(?:[:|-]\\w{2}){5}).*");

    public static Map<String, String> scan() {
        Map<String, String> arpTable = new HashMap<>();
        List<String> lines = RuntimeUtil.execForLines("arp -a");
        for (String line : lines) {
            Matcher matcher = IP_MAC_PATTERN.matcher(line);
            if (matcher.find()) {
                String ip = matcher.group(1);
                String mac = matcher.group(2);
                if (mac.contains("-")) {
                    mac = mac.replaceAll("-", "");
                } else if (mac.contains(":")) {
                    mac = mac.replaceAll(":", "");
                }
                arpTable.put(ip, mac);
                Console.log("---------------------------------");
                Console.log("IP: ", ip);
                Console.log("MAC: ", mac);
                Console.log("---------------------------------");
            }
        }
        return arpTable;
    }
}
