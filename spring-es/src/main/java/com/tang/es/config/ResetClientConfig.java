package com.tang.es.config;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Objects;

/**
 * es 客户端配置
 */
@Configuration
public class ResetClientConfig extends AbstractElasticsearchConfiguration {

    private static final Integer ADDRESS_LENGTH = 2;

    private static final Logger logger = LoggerFactory.getLogger(ResetClientConfig.class);

    private EsProperties esProperties;
    @Autowired
    public void setEsProperties(EsProperties esProperties) {
        this.esProperties = esProperties;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return highLevelClient();
//        return null;
    }



    @Bean(name = "restClientBuilder")
    public RestClientBuilder restClientBuilder() {
        RestClientBuilder restClientBuilder = null;
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(esProperties.getUserName(), esProperties.getPassword()));

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);

            HttpHost[] hosts = Arrays.stream(esProperties.getClusterNodes())
                    .map(this::makeHttpHost)
                    .filter(Objects::nonNull)
                    .toArray(HttpHost[]::new);
            restClientBuilder = RestClient.builder(hosts);
            // 设置一个监听器，每次节点出现故障时都会收到通知，以防需要采取措施，
            // 当启用故障嗅探时在内部使用。
            restClientBuilder.setFailureListener(new RestClient.FailureListener() {
                @Override
                public void onFailure(Node node) {
                    logger.error("es current node connection failure :{}", JSONObject.toJSONString(node));
                }
            });
            // 设置允许修改默认请求配置的回调
            //（例如请求超时，身份验证或org.apache.http.client.config.RequestConfig.Builder允许设置的任何内容）。
            restClientBuilder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                @Override
                public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                    return requestConfigBuilder
                            .setConnectionRequestTimeout(esProperties.getConnectionReqTimeOut())
                            .setSocketTimeout(esProperties.getSocketTimeOut())
                            .setConnectTimeout(esProperties.getConnectionTimeOut());
                }
            }).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                    httpClientBuilder.disableAuthCaching();
                    httpClientBuilder.setSSLStrategy(sessionStrategy);
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    return httpClientBuilder;
                }
            });
            return restClientBuilder;
        }catch ( Exception e){
            logger.error("ResetClientConfig.restClientBuilder error :{}",e.getMessage());
            return restClientBuilder;
        }
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient() {
        // 此处可以进行其它操作
        return new RestHighLevelClient(restClientBuilder());
    }

    /**
     * 根据配置创建HttpHost
     * @param s
     * @return
     */
    private HttpHost makeHttpHost(String s) {
        String[] address = s.split(":");
        if (address.length == ADDRESS_LENGTH) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, esProperties.getSchema());
        } else {
            return null;
        }
    }
}
