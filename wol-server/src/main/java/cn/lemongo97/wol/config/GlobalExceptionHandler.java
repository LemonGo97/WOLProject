package cn.lemongo97.wol.config;

import cn.lemongo97.wol.common.ResultCode;
import cn.lemongo97.wol.exception.WOLBaseException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

/**
 * @author LemonGo97
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public Result handleExpiredJwtException(ExpiredJwtException e){
        log.error("token 过期了", e);
        return Result.failure(ResultCode.USER_TOKEN_EXPIRED, e.getStackTrace());
    }

    @ExceptionHandler(DataAccessException.class)
    public Result handleDataAccessException(DataAccessException e){
        log.error("数据库服务异常", e);
        return Result.failure(ResultCode.DATABASE_EXCEPTION, Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler(WOLBaseException.class)
    public Result handleTiBaseException(WOLBaseException e) {
        log.error(e.getMsg(),e);
        return Result.failure(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Throwable.class)
    public Result handleBasicException(Throwable t) {
        log.error(ResultCode.UNKNOWN_EXCEPTION.message(),t);
        return Result.failure(ResultCode.UNKNOWN_EXCEPTION, t.getMessage());
    }
}