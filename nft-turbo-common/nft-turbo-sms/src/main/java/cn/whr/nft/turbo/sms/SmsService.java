package cn.whr.nft.turbo.sms;

import cn.whr.nft.turbo.sms.response.SmsSendResponse;

/**
 * 短信服务
 *
 * @author whr
 */
public interface SmsService {
    /**
     * 发送短信
     *
     * @param phoneNumber
     * @param code
     * @return
     */
    public SmsSendResponse sendMsg(String phoneNumber, String code);
}
