package cn.whr.nft.turbo.collection.exception;

import cn.whr.nft.turbo.base.exception.BizException;
import cn.whr.nft.turbo.base.exception.ErrorCode;

/**
 * 藏品异常
 *
 * @author whr
 */
public class CollectionException extends BizException {

    public CollectionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CollectionException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CollectionException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public CollectionException(Throwable cause, ErrorCode errorCode) {
        super(cause, errorCode);
    }

    public CollectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace, errorCode);
    }

}
