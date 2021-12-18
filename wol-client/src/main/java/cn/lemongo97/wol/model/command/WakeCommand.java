package cn.lemongo97.wol.model.command;

public abstract class WakeCommand extends Command {

    protected static final int DEFAULT_PORT = 9;

    @Override
    protected final Object doCommand() {
        this.wake();
        return "success";
    }

    abstract protected void wake();
}
