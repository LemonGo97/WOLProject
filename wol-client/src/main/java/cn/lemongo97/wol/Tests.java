package cn.lemongo97.wol;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RuntimeUtil;

import java.net.NetworkInterface;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests {

    final static Pattern pattern = Pattern.compile(".*?((?:(?:2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(?:2[0-4]\\d|25[0-5]|[01]?\\d\\d?)).*?(\\w{2}(?::\\w{2}){5}).*");

    public static void main(String[] args) {
        List<String> lines = RuntimeUtil.execForLines("arp -a");
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()){
                Console.log("---------------------------------");
                Console.log("IP: ", matcher.group(1));
                Console.log("MAC: ", matcher.group(2));
                Console.log("---------------------------------");
            }
        }
    }
}
