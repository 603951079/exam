package com.crcc.exam.config.filter;

import com.crcc.exam.domain.po.SysUser;
import com.crcc.exam.util.JWTTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader("Authorization");
        /**
         * 如果请求头中不含有Authorization信息，则请求直接过，到下一个过滤器中进行处理
         */
        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        /**
         * 验证token的合法性：不抛出异常、返回对象不为空则验证通过
         */
        Claims claims = JWTTokenUtil.checkToken(token);
        if (claims != null) {
            /**
             * 用户名
             */
            String username = claims.getSubject();
            /**
             * 获取用户中文名字
             */
            String zhName = claims.get("zhname", String.class);
            String id = claims.get("id", String.class);
            String phone = claims.get("phone", String.class);
            /**
             * 获取角色列表
             */
            List<String> authorities = claims.get("Authorities", ArrayList.class);
            for (int i = 0; i < authorities.size(); i++) {
                String role = authorities.get(i);
                log.debug("role=" + role);
            }
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(new SysUser(id,zhName, username,phone), null,
                            authorities.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }

        filterChain.doFilter(request, response);
    }
}
