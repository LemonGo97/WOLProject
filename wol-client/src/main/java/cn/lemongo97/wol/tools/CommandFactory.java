package cn.lemongo97.wol.tools;

import cn.lemongo97.wol.Response;
import cn.lemongo97.wol.model.command.*;
import cn.lemongo97.wol.utils.GsonUtil;
import com.google.gson.reflect.TypeToken;

public class CommandFactory {

    public static Command getInstance(String json, String command) {
        switch (command) {
            case "scan":
                return GsonUtil.fromJson(json, new TypeToken<Response<ScanCommand>>() {}).getBody();
            case "wake":
                return GsonUtil.fromJson(json, new TypeToken<Response<WakeSingleCommand>>() {}).getBody();
            case "wake-batch":
                return GsonUtil.fromJson(json, new TypeToken<Response<WakeBatchCommand>>() {}).getBody();
            default:
                return new UnknownCommand();
        }
    }
}
