package cn.lemongo97.wol.tools;

import cn.lemongo97.wol.common.Response;
import cn.lemongo97.wol.model.command.Command;
import cn.lemongo97.wol.model.command.ScanCommand;
import cn.lemongo97.wol.model.command.UnknownCommand;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CommandFactory {

    private static final Gson gson = new Gson();

    public static Command getInstance(String json, String command) {
        switch (command) {
            case "scan":
                Response<ScanCommand> response = gson.fromJson(json, new TypeToken<Response<ScanCommand>>() {
                }.getType());
                return response.getBody();
            default:
                return new UnknownCommand();
        }
    }
}
