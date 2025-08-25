package com.minus.test.mcp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 高德地图配置参数
 *
 * @author minus
 * @since 2025/8/18 13:50
 */
@Data
@Component
@ConfigurationProperties(prefix = "amap-api")
public class AMapProperties {

    private String key;

    private String url;

}
