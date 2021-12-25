package cn.lemongo97.wol.exception;

import cn.lemongo97.wol.common.ResultCode;

/**
 * @author lemongo97
 */
public class TokenNotFoundException extends WOLBaseException {

    public TokenNotFoundException() {
        super(ResultCode.USER_TOKEN_NOT_FOUND);
    }
}
