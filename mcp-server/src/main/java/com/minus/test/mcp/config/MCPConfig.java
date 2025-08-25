package com.minus.test.mcp.config;

import com.minus.test.mcp.server.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP配置<br>
 * 参考文档：<a href="https://mcp-docs.cn/quickstart/server">MCP中文文档-面向服务器开发者</a><br>
 *
 * @author minus
 * @since 2025/5/15 09:46
 */
@Configuration
public class MCPConfig {

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return  MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }

}
