package cn.whr.nft.turbo.inventory.domain.service;

import cn.whr.nft.turbo.api.inventory.request.InventoryRequest;
import cn.whr.nft.turbo.inventory.domain.response.InventoryResponse;
import org.springframework.stereotype.Service;

/**
 * 库存服务
 *
 * @author whr
 */
@Service
public interface InventoryService {

    /**
     * 初始化藏品库存
     *
     * @param request
     * @return
     */
    public InventoryResponse init(InventoryRequest request);

    /**
     * 扣减藏品库存
     *
     * @param request
     * @return
     */
    public InventoryResponse decrease(InventoryRequest request);

    /**
     * 增加藏品库存
     *
     * @param request
     * @return
     */
    public InventoryResponse increase(InventoryRequest request);

    /**
     * 失效藏品库存
     *
     * @param request
     * @return
     */
    public void invalid(InventoryRequest request);

    /**
     * 获取藏品库存
     *
     * @param request
     * @return
     */
    public Integer getInventory(InventoryRequest request);

    /**
     * 获取藏品库存扣减日志
     *
     * @param request
     * @return
     */
    public String getInventoryDecreaseLog(InventoryRequest request);
}
