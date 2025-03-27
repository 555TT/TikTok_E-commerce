package cn.whr.nft.turbo.api.notice.service;


import cn.whr.nft.turbo.api.notice.response.NoticeResponse;

/**
 * @author whr
 */
public interface NoticeFacadeService {
    /**
     * 生成并发送短信验证码
     *
     * @param telephone
     * @return
     */
    public NoticeResponse generateAndSendSmsCaptcha(String telephone);
}
