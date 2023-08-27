package com.tang.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.alibaba.fastjson2.JSONObject;
import com.tang.es.domain.User;
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
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
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

    private static final String hostName = "127.0.0.1";

    private static final Integer port = 9200;

    private static final String scheme = "https";

    public static void main(String[] args) throws IOException {

        EsClient esClient = new EsClient();
        RestHighLevelClient restHighLevelClient = esClient.restClient();
//        esClient.RestHighLevelClientOperate(restHighLevelClient);
        esClient.ElasticsearchClientOperate();
    }

    private void ElasticsearchClientOperate()  {
        RestClient restClient = getRestClientBuilder().build();
        RestClientTransport restClientTransport = null;
        ElasticsearchClient elasticsearchClient = null;
        try {
            restClientTransport = new RestClientTransport(restClient,new JacksonJsonpMapper());
            elasticsearchClient = new ElasticsearchClient(restClientTransport);
            CountResponse count = elasticsearchClient.count();
        }catch (Exception e){
            logger.error("ElasticsearchClientOperate error :{}",e.getMessage());
        }finally {
            if (null!=restClientTransport){
                try {
                    restClientTransport.close();
                }catch (Exception e){
                    logger.error("ElasticsearchClientOperate error :{}",e.getMessage());
                }
            }if (null!=elasticsearchClient){

            }
        }
    }

    private void RestHighLevelClientOperate(RestHighLevelClient restHighLevelClient) throws IOException {
        AcknowledgedResponse user = restHighLevelClient.indices().delete(new DeleteIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println(user.isAcknowledged());
        CreateIndexResponse response = restHighLevelClient.indices().create(new CreateIndexRequest("user"), RequestOptions.DEFAULT);
        System.out.println(response.isAcknowledged());
        // 添加用户数据
        User user1 = new User();
        user1.setName("jk");
        user1.setAge(28L);
        user1.setSex("男");
        String toJSONString = JSONObject.toJSONString(user1);
        System.out.println(toJSONString);
        IndexRequest indexRequest = new IndexRequest();
        try {
            indexRequest.index("user").id("1001").source(toJSONString, XContentType.JSON);
            IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index.getResult());
        }catch (Exception e){

        }

        GetResponse getResponse = restHighLevelClient.get(new GetRequest("user","1001"), RequestOptions.DEFAULT);
        System.out.println(getResponse.getIndex());
        System.out.println(getResponse.getSourceAsMap());
        System.out.println(getResponse.getVersion());
        System.out.println(getResponse.getFields());
        System.out.println(getResponse.getType());

        // 查询
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user");
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getNumReducePhases());
        SearchHits hits = searchResponse.getHits();
        for (SearchHit searchHit: hits){
            System.out.println(searchHit.getSourceAsString());
        }

        restHighLevelClient.close();
    }

    public RestHighLevelClient restClient() {
        RestHighLevelClient restClient = null;
        try {
            restClient = new RestHighLevelClient(getRestClientBuilder());
        } catch (Exception e) {
            logger.error("elasticsearch TransportClient create error!!", e);
        }
        return restClient;
    }

    private RestClientBuilder getRestClientBuilder(){
        logger.info("Elasticsearch init start ......");
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
            return RestClient.builder(
                            new HttpHost(hostName, port, scheme))
                    .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                        public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                            httpClientBuilder.disableAuthCaching();
                            httpClientBuilder.setSSLStrategy(sessionStrategy);
                            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                            return httpClientBuilder;
                        }
                    });
        } catch (Exception e) {
            logger.error("elasticsearch TransportClient create error!!", e);
        }
        return null;
    }
}
