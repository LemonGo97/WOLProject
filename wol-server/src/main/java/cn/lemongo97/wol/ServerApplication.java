package cn.lemongo97.wol;

import cn.lemongo97.wol.model.command.Command;
import cn.lemongo97.wol.model.command.ScanCommand;
import cn.lemongo97.wol.ws.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author lemongo97
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
