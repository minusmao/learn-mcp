package com.minus.test.mcp.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 高德地图客户端<br>
 * 参考文档：<a href="https://lbs.amap.com/api/webservice/guide/api-advanced/weatherinfo">高德开放平台-天气查询</a>
 *
 * @author minus
 * @since 2025/8/18 13:37
 */
@FeignClient(name = "aMapFeign", url = "${amap-api.url}", configuration = AMapFeignConfig.class)
public interface AMapFeign {

    /**
     * 通过关键字（行政区划名称）查询行政区划信息（包含行政区划代码adcode）
     *
     * @param keywords 关键字（行政区划名称）
     * @return 行政区划信息
     */
    @GetMapping("/config/district")
    JsonNode getConfigDistrict(
            @RequestParam("keywords") String keywords
    );

    /**
     * 通过行政区划代码查询天气
     *
     * @param areaCode 行政区划代码（6位，如110000北京）
     * @param weatherType 返回天气类型（base-实况天气 all-预报天气）
     * @return 天气信息
     */
    @GetMapping("/weather/weatherInfo")
    JsonNode getWeatherWeatherInfo(
            @RequestParam("city") String areaCode,
            @RequestParam("extensions") String weatherType
    );

}
