package cn.lemongo97.wol.utils;

import cn.hutool.http.ContentType;
import cn.lemongo97.wol.config.Result;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author lemongo97
 */
public final class ResponseUtils {

    private final static String CONTENT_TYPE = ContentType.JSON.toString(StandardCharsets.UTF_8);

    private ResponseUtils() {
    }

    public static void writeAndFlush(ServletResponse response, Object message) throws IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.write(GsonUtil.toJson(message == null ? Result.success() : message));
        out.flush();
        out.close();
    }
}
