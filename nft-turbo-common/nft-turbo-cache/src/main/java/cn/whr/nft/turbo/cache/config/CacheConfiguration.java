package cn.whr.nft.turbo.cache.config;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 *
 * @author whr
 */
@Configuration
@EnableMethodCache(basePackages = "cn.whr.nft.turbo")
public class CacheConfiguration {
}
