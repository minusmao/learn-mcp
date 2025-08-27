package com.minus.test.mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试MCP客户端（传输方式：SSE网络服务）
 *
 * @author minus
 * @since 2025/8/19 17:27
 */
@SpringBootApplication
public class TestMcpClientSseApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMcpClientSseApplication.class, args);
    }

}
