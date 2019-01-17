package com.liuzj.oauth2server.config.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 此 Filter 是实现了 javax.servlet 的 Filter,
 * 所以在 WebSecurityConfig 中是不能直接 AddFilter 的，
 * 而是需要通过 addFilterBefore\addFilterAfter\addFilterAt
 * 将此 Filter 加到拦截链中
 *
 * @see "https://docs.spring.io/spring-security/site/docs/3.2.0.RELEASE/apidocs/org/springframework/security/config/annotation/web/HttpSecurityBuilder.html#addFilter%28javax.servlet.Filter%29"
 * @author liuzj
 * @date 2019-01-17
 */
@Component
public class MyAuthoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 处理过滤规则
        System.out.println("自定义 Filter" + servletRequest.getParameter("username"));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
