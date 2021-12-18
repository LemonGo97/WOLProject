package cn.lemongo97.wol.model.command;

public class UnknownCommand extends Command{
    @Override
    protected Object doCommand() {
        return "success";
    }
}
