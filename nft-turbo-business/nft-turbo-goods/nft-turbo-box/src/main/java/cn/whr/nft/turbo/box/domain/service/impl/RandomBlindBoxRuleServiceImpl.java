package cn.whr.nft.turbo.box.domain.service.impl;

import cn.whr.nft.turbo.api.box.constant.BlindBoxItemStateEnum;
import cn.whr.nft.turbo.box.domain.request.BlindBoxBindMatchRequest;
import cn.whr.nft.turbo.box.domain.service.BlindBoxItemService;
import cn.whr.nft.turbo.box.domain.service.BlindBoxRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 随机盲盒规则实现
 *
 * @author whr
 */
@Service("randomBlindBoxRuleService")
@Slf4j
public class RandomBlindBoxRuleServiceImpl implements BlindBoxRuleService {

    @Autowired
    private BlindBoxItemService blindBoxItemService;

    @Override
    public Long match(BlindBoxBindMatchRequest request) {
        return blindBoxItemService.queryRandomByBoxIdAndState(request.getBlindBoxId(), BlindBoxItemStateEnum.INIT.name());
    }
}
