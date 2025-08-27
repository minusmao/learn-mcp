package com.minus.test.mcp.config;

import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Scanner;

/**
 * MCP配置<br>
 * 参考文档：<a href="https://mcp-docs.cn/quickstart/client">MCP中文文档-面向客户端开发者</a><br>
 * 参考文档：<a href="https://brave.com/zh/search/api">Brave搜索API</a><br>
 * 参考文档：<a href="https://blog.csdn.net/liudachu/article/details/147084461">SpringAI调用硅基流动免费模型</a>
 *
 * @author minus
 * @since 2025/8/20 11:51
 */
@Configuration
public class MCPConfig {

    @Bean
    public CommandLineRunner chatbot(ChatClient.Builder chatClientBuilder, List<McpSyncClient> mcpSyncClients) {

        return args -> {

            var chatClient = chatClientBuilder
                    .defaultSystem("你是一个天气助手，可以根据行政区划代码查询天气情况")
                    .defaultToolCallbacks(new SyncMcpToolCallbackProvider(mcpSyncClients))
                    .defaultAdvisors(MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build())
                    .build();

            // Start the chat loop
            System.out.println("\n###天气助手###\n");
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    String nextLine = scanner.nextLine();
                    if ("exit".equals(nextLine)) {
                        break;
                    }

                    System.out.print("\nUSER: " + nextLine);
                    // System.out.println("\nASSISTANT: " + chatClient.prompt().user(nextLine).call().content());
                    // 流式输出AI响应
                    System.out.print("\nASSISTANT: ");
                    chatClient.prompt().user(nextLine).stream().content()
                            .doOnComplete(System.out::println)
                            .subscribe(System.out::print);
                    System.out.println();
                }
            }

        };
    }

}
