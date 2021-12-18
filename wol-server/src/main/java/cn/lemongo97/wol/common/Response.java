package cn.lemongo97.wol.common;

public class Response<T> {

    private Integer code;

    private String clientId;

    private String command;

    private T body;

    public static <T> Response<T> success(String clientId) {
        return new Response<>(200, clientId, null);
    }

    public static <T> Response<T> success(String clientId, String command) {
        return new Response<>(200, clientId, command);
    }

    public static <T> Response<T> success(String clientId, String command, T body) {
        return new Response<>(200, clientId, command, body);
    }

    public static <T> Response<T> failed(String clientId) {
        return new Response<>(400, clientId, null);
    }

    public static <T> Response<T> failed(String clientId, T body) {
        return new Response<>(400, clientId, null, body);
    }

    private Response(Integer code, String clientId, String command, T body) {
        this.code = code;
        this.clientId = clientId;
        this.command = command;
        this.body = body;
    }

    private Response(Integer code, String clientId, String command) {
        this.code = code;
        this.clientId = clientId;
        this.command = command;
    }

    public Response() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
