package cn.whr.nft.turbo.user.infrastructure.exception;

import cn.whr.nft.turbo.base.exception.BizException;
import cn.whr.nft.turbo.base.exception.ErrorCode;

/**
 * 用户异常
 *
 * @author whr
 */
public class UserException extends BizException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public UserException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public UserException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public UserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

}
