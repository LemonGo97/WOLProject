package cn.lemongo97.wol.config;

import cn.lemongo97.wol.common.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author LemonGo97
 */
@Data
@NoArgsConstructor
public class Result implements Serializable {
    private Integer code;
    private String message;
    private Object data;
    private Object errors;

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public static Result success() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code());
        result.setData(data);
        return result;
    }

    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setErrors(null);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object errors) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        result.setErrors(errors);
        return result;
    }

    public static Result failure(ResultCode resultCode, String msg, Object errors) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(msg);
        result.setErrors(errors);
        return result;
    }

    public Object getErrors() {
        return this.errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
