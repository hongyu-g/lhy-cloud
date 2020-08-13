package com.hong.bookservice.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author liang
 * @description
 * @date 2020/6/12 18:39
 */
@Configuration
@Slf4j
public class RestClientConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    private static final String HTTP_SCHEME = "http";


    @Bean
    public RestClientBuilder restClientBuilder() {
        return RestClient.builder(makeHttpHost());
    }

    @Bean
    public RestClient restClient(){
        return RestClient.builder(new HttpHost(host, port, HTTP_SCHEME)).build();
    }

    private HttpHost makeHttpHost() {
        return new HttpHost(host, port, HTTP_SCHEME);
    }

    @Bean("restHighLevelClient")
    public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder){
        return new RestHighLevelClient(restClientBuilder);
    }
}
