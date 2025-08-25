package com.minus.test.mcp.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.minus.test.mcp.client.AMapFeign;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 天气相关MCP服务<br>
 * 参考文档：<a href="https://mcp-docs.cn/quickstart/server">MCP中文文档-面向服务器开发者</a><br>
 *
 * @author minus
 * @since 2025/5/15 09:33
 */
@Service
public class WeatherService {

    @Autowired
    private AMapFeign aMapFeign;

    @Tool(description = "通过关键字（行政区划名称）查询行政区划信息（包含行政区划代码adcode）")
    public JsonNode getDistrictInfoByKeywords(
            @ToolParam(description = "关键字（行政区划名称）") String keywords
    ) {
        return aMapFeign.getConfigDistrict(keywords);
    }

    @Tool(description = "通过行政区划代码查询天气")
    public JsonNode getWeatherForecastByAreaCode(
            @ToolParam(description = "行政区划代码（6位，如110000北京）") String areaCode,
            @ToolParam(description = "返回天气类型（base-实况天气 all-预报天气）") String weatherType
    ) {
        return aMapFeign.getWeatherWeatherInfo(areaCode, weatherType);
    }

}
