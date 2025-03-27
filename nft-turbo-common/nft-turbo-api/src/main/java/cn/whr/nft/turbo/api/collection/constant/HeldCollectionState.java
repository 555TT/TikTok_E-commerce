package cn.whr.nft.turbo.api.collection.constant;

/**
 * @author wswyb001
 */
public enum HeldCollectionState {
    /**
     * 初始化
     */
    INIT,

    /**
     * 生效
     */
    ACTIVED,

    /**
     * 失效
     */
    INACTIVED,

    /**
     * 销毁中
     */
    DESTROYING,

    /**
     * 已销毁
     */
    DESTROYED;
}
