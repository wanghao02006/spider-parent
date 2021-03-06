package com.leiyu.online.spiderimages.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:/httpclient.properties")
public class HttpClientConfig {

    @Value("${http.maxTotal}")
    private Integer httpMaxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private Integer httpDefaultMaxPerRoute;

    @Value("${http.connectTimeout}")
    private Integer httpConnectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private Integer httpConnectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private Integer httpSocketTimeout;

    @Value("${http.staleConnectionCheckEnabled}")
    private Boolean httpStaleConnectionCheckEnabled;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(httpMaxTotal);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpMaxTotal);
        // 每个主机的最大并发数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(httpDefaultMaxPerRoute);
        return poolingHttpClientConnectionManager;
    }

    // 定期关闭无效连接
    @Bean
    public IdleConnectionEvictor idleConnectionEvictor(PoolingHttpClientConnectionManager manager) {
        return new IdleConnectionEvictor(manager,5, TimeUnit.MINUTES);
    }

    // 定义Httpclient对
    @Bean
    @Scope("prototype")
    // 多例对象
    public CloseableHttpClient closeableHttpClient(PoolingHttpClientConnectionManager manager,RequestConfig config) {
        return HttpClients.custom().setDefaultRequestConfig(config).setConnectionManager(manager).build();
    }

    // 请求配置
    @Bean
    public RequestConfig requestConfig() {
        return RequestConfig.custom().setConnectTimeout(httpConnectTimeout) // 创建连接的最长时间
                .setConnectionRequestTimeout(httpConnectionRequestTimeout) // 从连接池中获取到连接的最长时间
                .setSocketTimeout(httpSocketTimeout) // 数据传输的最长时间
                .setStaleConnectionCheckEnabled(httpStaleConnectionCheckEnabled) // 提交请求前测试连接是否可用
                .build();
    }
}
