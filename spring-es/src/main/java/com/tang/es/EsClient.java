package com.tang.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class EsClient {

    private static final Logger logger = LoggerFactory.getLogger(EsClient.class);

    private static final String username = "elastic";

    private static final String password = "_Td8m1AZJm-wlw_=fO74";

    private static final String hostName = "localhost";

    private static final Integer port = 9200;

    private static final String scheme = "https";

    public static void main(String[] args) throws IOException {

        EsClient esClient = new EsClient();
        RestHighLevelClient restHighLevelClient = esClient.restClient();


        AcknowledgedResponse user = restHighLevelClient.indices().delete(new DeleteIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println(user.isAcknowledged());
        CreateIndexResponse response = restHighLevelClient.indices().create(new CreateIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
        restHighLevelClient.close();
    }

    public RestHighLevelClient restClient() {
        logger.info("Elasticsearch init start ......");
        RestHighLevelClient restClient = null;
        try {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));

            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLIOSessionStrategy sessionStrategy = new SSLIOSessionStrategy(sslContext, NoopHostnameVerifier.INSTANCE);
            restClient = new RestHighLevelClient(
                    RestClient.builder(
                                    new HttpHost(hostName, port, scheme))
                            .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                    httpClientBuilder.disableAuthCaching();
                                    httpClientBuilder.setSSLStrategy(sessionStrategy);
                                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                                    return httpClientBuilder;
                                }
                            }));
        } catch (Exception e) {
            logger.error("elasticsearch TransportClient create error!!", e);
        }
        return restClient;
    }
}
