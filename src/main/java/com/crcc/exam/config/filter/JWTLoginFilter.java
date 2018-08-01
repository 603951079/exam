package com.crcc.exam.config.filter;

import com.crcc.exam.domain.dto.BaseResult;
import com.crcc.exam.domain.po.SysUser;
import com.crcc.exam.util.JWTTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        log.debug("username=" + username);
        log.debug("password=" + password);

        // 返回一个验证令牌
        return this.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
    }

    /**
     * JWTLoginFilter 验证通过后会回调该方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth) throws IOException {
        log.debug("JWTLoginFilter.attemptAuthentication 验证之后，进入successfulAuthentication！");
        SysUser userDetails = (SysUser) auth.getPrincipal();
        String token = JWTTokenUtil.getToken(userDetails.getId(), userDetails.getZhName(), userDetails.getUsername(), userDetails.getPhone(),
                auth.getAuthorities().stream().map(s -> ((GrantedAuthority) s).getAuthority()).collect(Collectors.toList()));
        res.setContentType("text/plain;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_OK);
        BaseResult result = new BaseResult();
        result.setData(token);
        result.setSuccess(true);
        result.setStatus(200);
        result.setMessage("success!");
        res.getWriter().write(new ObjectMapper().writeValueAsString(result));

    }

    /**
     * JWTLoginFilter 验证失败后会回调该方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        log.debug("JWTLoginFilter.attemptAuthentication 验证失败之后，unsuccessfulAuthentication！");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        BaseResult result = new BaseResult();
        result.setData(null);
        result.setSuccess(false);
        result.setStatus(500);
        result.setMessage(failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }
}
