package cn.lemongo97.wol.model.command;


public abstract class Command {
    protected String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}
