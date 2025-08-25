package com.minus.test.mcp.client;

import com.minus.test.mcp.properties.AMapProperties;
import feign.RequestInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;

/**
 * 高德地图客户端配置
 *
 * @author minus
 * @since 2025/8/18 13:47
 */
public class AMapFeignConfig {

    @Resource
    private AMapProperties aMapProperties;

    /**
     * 设置请求拦截器
     *
     * @return 请求拦截器
     */
    @Bean
    public RequestInterceptor aMapRequestInterceptor() {
        return template -> {
            // 设置请求服务权限标识
            template.query("key", aMapProperties.getKey());
        };
    }

}
