package com.minus.test.mcp.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Feign配置
 *
 * @author maoshixiang
 * @since 2025/8/27 14:56
 */
@Configuration
public class FeignConfig {

    /**
     * 由于本项目使用的是spring-ai-starter-mcp-server-webflux方式，没有引入spring-boot-starter-web依赖<br>
     * 而Feign调用需要用到HttpMessageConverter转换器，所以在这里手动添加一个HttpMessageConverters转换器的bean<br>
     * <br>
     * 其他方法1：可以改用spring-ai-starter-mcp-server-webmvc方式，默认引入了spring-boot-starter-web依赖<br>
     * 其他方法2：webflux方式，用Feign（本质上是阻塞式的）就不太合适，可以改用WebClient来替代Feign<br>
     *
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }

}
