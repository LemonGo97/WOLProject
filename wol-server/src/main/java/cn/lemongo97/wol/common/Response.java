package cn.lemongo97.wol.common;

public class Response<T> {

    private Integer code;

    private String clientId;

    private T body;

    public static <T> Response<T> success(String clientId){
        return new Response<>(200, clientId);
    }

    public static <T> Response<T> success(String clientId, T body){
        return new Response<>(200, clientId, body);
    }

    public static <T> Response<T> failed(String clientId){
        return new Response<>(400, clientId);
    }

    public static <T> Response<T> failed(String clientId, T body){
        return new Response<>(400, clientId, body);
    }

    private Response(Integer code, String clientId, T body) {
        this.code = code;
        this.clientId = clientId;
        this.body = body;
    }

    private Response(Integer code, String clientId) {
        this.code = code;
        this.clientId = clientId;
    }

    public Response() {}

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
}
