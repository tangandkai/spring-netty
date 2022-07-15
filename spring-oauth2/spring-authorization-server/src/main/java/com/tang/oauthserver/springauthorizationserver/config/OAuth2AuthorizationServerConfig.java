package com.tang.oauthserver.springauthorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer //声明开启 OAuth 授权服务器的功能。
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;

    @Bean
    public TokenStore jdbcTokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 用户认证 Manager
     */
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 设置/oauth/check_token 端点，通过认证后可访问
     * 这里的认证，指的是使用 client-id + client-secret 进行的客户端认证，不要和用户认证混淆
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager);
        endpoints.authenticationManager(authenticationManager).tokenStore(jdbcTokenStore());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory() // <4.1>
//                .withClient("clientapp").secret("112233") // <4.2> Client 账号、密码。
//                .authorizedGrantTypes("authorization_code","password") // <4.2> 密码模式,在原来的基础上添加授权码模式
//                .redirectUris("http://127.0.0.1:8082/callback") //配置回调地址
//                .redirectUris("http://www.baidu.com")
//                .scopes("read_userinfo", "read_contacts") // <4.2> 可授权的 Scope
////                .and().withClient() // <4.3> 可以继续配置新的 Client
//        ;
        clients.withClientDetails(jdbcClientDetailsService());
    }

    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }
}
