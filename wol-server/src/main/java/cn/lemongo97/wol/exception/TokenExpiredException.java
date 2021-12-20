package cn.lemongo97.wol.exception;

import cn.lemongo97.wol.common.ResultCode;

/**
 * @author lemongo97
 */
public class TokenExpiredException extends WOLBaseException {

    public TokenExpiredException() {
        super(ResultCode.USER_TOKEN_EXPIRED);
    }
}
