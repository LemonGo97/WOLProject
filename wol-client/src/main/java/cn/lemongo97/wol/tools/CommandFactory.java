package cn.lemongo97.wol.tools;

import cn.lemongo97.wol.Response;
import cn.lemongo97.wol.model.command.Command;
import cn.lemongo97.wol.model.command.ScanCommand;
import cn.lemongo97.wol.model.command.UnknownCommand;
import cn.lemongo97.wol.utils.GsonUtil;
import com.google.gson.reflect.TypeToken;

public class CommandFactory {

    public static Command getInstance(String json, String command) {
        switch (command) {
            case "scan":
                Response<ScanCommand> response = GsonUtil.fromJson(json, new TypeToken<Response<ScanCommand>>() {});
                return response.getBody();
            default:
                return new UnknownCommand();
        }
    }
}
