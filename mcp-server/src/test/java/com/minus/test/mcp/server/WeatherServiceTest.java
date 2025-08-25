package com.minus.test.mcp.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.minus.test.mcp.TestMcpServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 天气相关MCP服务测试
 *
 * @author minus
 * @since 2025/8/18 14:27
 */
@Slf4j
@SpringBootTest(classes = TestMcpServerApplication.class)
class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    void getDistrictInfoByKeywords() {
        JsonNode districtInfo = weatherService.getDistrictInfoByKeywords("北京");
        log.info("测试行政区划查询结果：{}", districtInfo);
    }

    @Test
    void getWeatherForecastByAreaCode() {
        JsonNode weatherInfo = weatherService.getWeatherForecastByAreaCode("110101", "all");
        log.info("测试天气查询结果：{}", weatherInfo);
    }

}