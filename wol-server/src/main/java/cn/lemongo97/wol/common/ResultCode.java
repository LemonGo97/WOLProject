package cn.lemongo97.wol.common;

/**
 * @author LemonGo97
 */
public enum ResultCode {
    /* 成功状态码*/
    SUCCESS(200,"成功"),
    /* 参数错误：1001-1999 */
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_BIND_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),
    /* 用户错误：2001-2999 */
    USER_NOT_LOGGED_IN(2001,"用户未登录，访问的目标需要验证，请登录"),
    USER_LOGIN_ERROR(2002,"账户不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账户或已被禁用"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),
    USER_TOKEN_EXPIRED(2006, "Token 已过期"),
    USER_TOKEN_NOT_FOUND(2006, "Token 必须传递！"),
    USER_ACCOUNT_LOCKED(2007,"账户已被锁定"),
    USER_PASSWD_EXPIRED(2008,"密码过期，请联系管理员!"),
    USER_ACCOUNT_EXPIRED(2009,"账户过期，请联系管理员!"),
    USER_ACCOUNT_UNKNOWN_EXCEPTION(2010,"未知的用户登录错误!"),
    /* 其他异常：3001-3999 */
    UNKNOWN_EXCEPTION(3001,"出现了预期之外的异常"),
    PERMISSION_DENIED(4001,"用户没有权限"),
    DATABASE_EXCEPTION(5001,"数据库服务异常"),
    OBJECT_NOT_EXIST(6001,"对象不存在")
    ;

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }


}
