package cn.lemongo97.wol.exception;


import cn.lemongo97.wol.common.ResultCode;

import java.io.Serializable;


/**
 * @author LemonGo97
 */
public class WOLBaseException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String msg;
    private final ResultCode code;

    public WOLBaseException() {
        super(ResultCode.UNKNOWN_EXCEPTION.message());
        this.code = ResultCode.UNKNOWN_EXCEPTION;
        this.msg = ResultCode.UNKNOWN_EXCEPTION.message();
    }

    public WOLBaseException(String msg) {
        super(msg);
        this.code = ResultCode.UNKNOWN_EXCEPTION;
        this.msg = msg;
    }

    public WOLBaseException(ResultCode resultCode) {
        super(resultCode.message());
        this.code = resultCode;
        this.msg = resultCode.message();
    }

    public WOLBaseException(ResultCode resultCode, String msg) {
        super(resultCode.message());
        this.code = resultCode;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public ResultCode getCode() {
        return code;
    }
}
