package cn.whr.nft.turbo.goods;


import cn.whr.nft.turbo.api.chain.service.ChainFacadeService;
import cn.whr.nft.turbo.api.order.OrderFacadeService;
import cn.whr.nft.turbo.api.user.service.UserFacadeService;
import cn.whr.nft.turbo.collection.domain.service.CollectionService;
import cn.whr.nft.turbo.collection.domain.service.impl.HeldCollectionService;
import cn.whr.nft.turbo.collection.facade.CollectionReadFacadeServiceImpl;
import cn.whr.nft.turbo.limiter.SlidingWindowRateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {NfTurboGoodsApplication.class})
@ActiveProfiles("test")
public class GoodsBaseTest {

    @MockBean
    private RedissonClient redissonClient;


    @MockBean
    protected SlidingWindowRateLimiter slidingWindowRateLimiter;

    @MockBean
    private CollectionService collectionService;

    @MockBean
    protected ChainFacadeService chainFacadeService;

    @MockBean
    protected UserFacadeService userFacadeService;

    @MockBean
    protected CollectionReadFacadeServiceImpl collectionReadFacadeService;

    @MockBean
    protected OrderFacadeService orderFacadeService;

    @MockBean
    protected HeldCollectionService heldCollectionService;

    @Test
    public void test() {

    }
}
