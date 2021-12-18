package cn.lemongo97.wol.model.command;

import static cn.lemongo97.wol.ws.WebSocketClient.WebSocketClientHandler;

public abstract class Command {

    public final void execute(WebSocketClientHandler webSocketClientHandler) {
        webSocketClientHandler.send(doCommand());
    }

    abstract protected Object doCommand();
}
