package com.minus.test.mcp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 测试MCP服务（传输方式：Stdio本地进程）
 *
 * @author minus
 * @since 2025/5/15 09:33
 */
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties
public class TestMcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMcpServerApplication.class, args);
    }

}
