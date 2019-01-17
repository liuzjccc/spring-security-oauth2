package com.liuzj.oauth2server.config;

import com.liuzj.oauth2server.config.filter.MyAuthoFilter;
import com.liuzj.oauth2server.config.filter.MySecurityFilter;
import com.liuzj.oauth2server.config.handler.AuthoFaillExceptionHandler;
import com.liuzj.oauth2server.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 安全配置
 *
 * @author liuzj
 * @date  2019-01-15
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * userDetailsService 获取token的时候对用户进行一些自定义过滤，并将保存用户信息（用户名，密码，角色等）
     */
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthoFaillExceptionHandler authoFaillExceptionHandler;

    @Autowired
    private MyAuthoFilter myAuthoFilter;

    /**
     * 使用MD5对client_secreat进行加密，可以使用默认的加密方式也可以自定义，这里使用MD5加密方式
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return MD5Util.encodeMD5(String.valueOf(charSequence));
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(MD5Util.encodeMD5(String.valueOf(charSequence)));
            }
        };
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置用户签名服务 主要是user-details 机制，
     *
     * @param auth 签名管理器构造器，用于构建用户具体权限控制
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * Adds a Filter that must be an instance of or extend one of the Filters provided within the Security framework. The method ensures that the ordering of the Filters is automatically taken care of. The ordering of the Filters is:
     *
     *     ChannelProcessingFilter
     *     ConcurrentSessionFilter
     *     SecurityContextPersistenceFilter
     *     LogoutFilter
     *     X509AuthenticationFilter
     *     AbstractPreAuthenticatedProcessingFilter
     *     CasAuthenticationFilter
     *     UsernamePasswordAuthenticationFilter
     *     ConcurrentSessionFilter
     *     OpenIDAuthenticationFilter
     *     DefaultLoginPageGeneratingFilter
     *     ConcurrentSessionFilter
     *     DigestAuthenticationFilter
     *     BasicAuthenticationFilter
     *     RequestCacheAwareFilter
     *     SecurityContextHolderAwareRequestFilter
     *     JaasApiIntegrationFilter
     *     RememberMeAuthenticationFilter
     *     AnonymousAuthenticationFilter
     *     SessionManagementFilter
     *     ExceptionTranslationFilter
     *     FilterSecurityInterceptor
     *     SwitchUserFilter
     * @see "https://docs.spring.io/spring-security/site/docs/3.2.0.RELEASE/apidocs/org/springframework/security/config/annotation/web/HttpSecurityBuilder.html#addFilter%28javax.servlet.Filter%29"
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .requestMatchers().antMatchers("/oauth/**","/login/**","/logout/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .formLogin()
                .permitAll() //新增login form支持用户登录及授权
                .and()
//                .exceptionHandling().accessDeniedHandler(authoFaillExceptionHandler)
        .addFilterBefore(myAuthoFilter, BasicAuthenticationFilter.class);
    }
}
