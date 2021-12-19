package cn.lemongo97.wol;

import cn.lemongo97.wol.Response;
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
        ((Runnable) () -> {
            try {
                while (true){
                    Thread.sleep(10000);
                    Command command = new ScanCommand();
                    WebSocketServer.sendInfo("8dc6b6681d034402945fc5c44675424b", Response.success("8dc6b6681d034402945fc5c44675424b", "scan", command));
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).run();

    }

}
