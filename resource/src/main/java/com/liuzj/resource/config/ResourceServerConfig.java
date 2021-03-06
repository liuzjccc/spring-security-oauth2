package com.liuzj.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 资源服务器的配置，这个类表明了此应用是OAuth2 的资源服务器（访问资源都需要认证），此处主要指定了受资源服务器保护的资源链接
 *
 * @author liuzj
 * @date 2019-01-15
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Primary
    @Bean
    public RemoteTokenServices tokenServices() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8001/oauth/check_token");
        tokenService.setClientId("client");
        tokenService.setClientSecret("123456");
        return tokenService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//禁用了csrf（跨站请求伪造）功能
                .authorizeRequests()//限定签名成功的请求
                //必须认证过后才可以访问;注意：hasAnyRole 会默认加上ROLE_前缀，而hasAuthority不会加前缀
                .antMatchers("/decision/**","/govern/**").hasAnyRole("user")
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/test/**").authenticated()
                // 免验证请求
                .antMatchers("/oauth/**").permitAll();
    }
}
