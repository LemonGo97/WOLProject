package cn.lemongo97.wol.model.command;

import static cn.lemongo97.wol.ws.WebSocketClient.WebSocketClientHandler;

public abstract class Command {
    protected String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public final void execute(WebSocketClientHandler webSocketClientHandler) {
        webSocketClientHandler.send(doCommand());
    }

    abstract protected Object doCommand();

}
