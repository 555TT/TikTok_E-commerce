package cn.whr.nft.turbo.box.exception;

import cn.whr.nft.turbo.base.exception.BizException;
import cn.whr.nft.turbo.base.exception.ErrorCode;

/**
 * 盲盒异常
 *
 * @author whr
 */
public class BlindBoxException extends BizException {

    public BlindBoxException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BlindBoxException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public BlindBoxException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public BlindBoxException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public BlindBoxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

}
