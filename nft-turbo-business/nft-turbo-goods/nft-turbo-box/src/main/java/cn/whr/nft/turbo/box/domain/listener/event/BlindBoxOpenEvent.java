package cn.whr.nft.turbo.box.domain.listener.event;

import cn.whr.nft.turbo.box.domain.entity.BlindBoxItem;
import org.springframework.context.ApplicationEvent;

/**
 * 创建HeldCollection事件
 *
 * @author whr
 */
public class BlindBoxOpenEvent extends ApplicationEvent {

    public BlindBoxOpenEvent(BlindBoxItem blindBoxItem) {
        super(blindBoxItem);
    }
}
