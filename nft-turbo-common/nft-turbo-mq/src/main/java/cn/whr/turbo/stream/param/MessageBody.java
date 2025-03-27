package cn.whr.turbo.stream.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 消息体
 *
 * @author whr
 */
@Data
@Accessors(chain = true)
public class MessageBody {
    /**
     * 幂等号
     */
    private String identifier;
    /**
     * 消息体
     */
    private String body;
}