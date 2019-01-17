package com.liuzj.oauth2server.config;

import com.liuzj.oauth2server.config.handler.AuthoFaillExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 这个类表明了此应用是OAuth2 的资源服务器,此处主要指定受资源服务器保护的资源链接
 * 默认情况下spring security oauth2的http配置会被WebSecurityConfigurerAdapter的配置覆盖
 *
 * @author liuzj
 * @date 2019-01-15
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private AuthoFaillExceptionHandler authoFaillExceptionHandler;

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
