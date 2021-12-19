package cn.lemongo97.wol.model.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnknownCommand extends Command{
    @Override
    protected Object doCommand() {
        log.error("未知的命令，请检查客户端是否为最新版或上报社区 issue ！！！");
        return "not support command !!!";
    }
}
