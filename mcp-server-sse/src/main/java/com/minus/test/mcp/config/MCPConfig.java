package com.minus.test.mcp.config;

import com.minus.test.mcp.server.WeatherService;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MCP配置<br>
 * 参考文档：<a href="https://mcp-docs.cn/quickstart/server">MCP中文文档-面向服务器开发者</a><br>
 * <br>
 * MCP 五大概念：<br>
 * - Tools：服务端提供的可调用方法（RPC 工具函数），客户端像调用 API 一样用它们<br>
 * - Resources：服务端暴露的资源入口（文件、数据库、配置等），客户端可以 list/read<br>
 * - Prompts：服务端预定义的提示模板（Prompt Preset），客户端调用 get 后交给 LLM 使用<br>
 * - Sampling：服务端请求客户端去调用 LLM 生成内容（反向调用 AI），服务端只发起，不做推理<br>
 * - Roots：客户端暴露的根目录权限，服务端只能在这些目录下访问文件，保证安全沙箱<br>
 *
 * @author minus
 * @since 2025/5/15 09:46
 */
@Configuration
public class MCPConfig {

    /**
     * Tools示例
     * - tools/list：工具列表<br>
     * - tools/call：工具调用<br>
     */
    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }

    /**
     * Resources示例<br>
     * - resources/list：资源列表<br>
     * - resources/read：资源读取<br>
     */
    @Bean
    public List<McpServerFeatures.AsyncResourceSpecification> timeResources() {
        McpSchema.Resource resource = new McpSchema.Resource(
                "time://now",
                "timeNow",
                "当前时间",
                "text/plain",
                null
        );
        return List.of(new McpServerFeatures.AsyncResourceSpecification(
                resource,
                (exchange, request) -> {
                    // 获取当前时间
                    LocalDateTime now = LocalDateTime.now();
                    // 生成结果
                    McpSchema.ReadResourceResult result = new McpSchema.ReadResourceResult(
                            List.of(new McpSchema.TextResourceContents(resource.uri(), resource.mimeType(), now.toString()))
                    );
                    return Mono.just(result);
                }
        ));
    }

    /**
     * Prompts示例<br>
     * - prompts/list：模板列表<br>
     * - prompts/get：模板获取<br>
     */
    @Bean
    public List<McpServerFeatures.AsyncPromptSpecification> greetingPrompts() {
        McpSchema.Prompt prompt = new McpSchema.Prompt(
                "greeting",
                "生成打招呼文案",
                List.of(new McpSchema.PromptArgument("username", "用户名", true))
        );
        return List.of(new McpServerFeatures.AsyncPromptSpecification(
                prompt,
                (exchange, request) -> {
                    // 获取用户名
                    String username = request.arguments().get("username").toString();
                    // 生成结果
                    McpSchema.GetPromptResult result = new McpSchema.GetPromptResult(
                            "生成打招呼文案",
                            List.of(new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent("你好，" + username + "！很高兴见到你^_^")))
                    );
                    return Mono.just(result);
                }
        ));
    }

}
