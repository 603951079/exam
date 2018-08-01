package com.crcc.exam.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
public class JWTTokenUtil {
    private static final String SECRET = "P@ssw02d"; // 私钥


    /**
     * 生成JWT
     */
    public static String getToken(String id, String zhName, String username, String phone, List<String> authorities) {
        String JWT = Jwts.builder()
                // 保存权限（角色）、用户中文名字
                .claim("Authorities", authorities)
                .claim("id", id)
                .claim("zhname", zhName)
                .claim("phone", phone)
                // 用户名写入标题
                .setSubject(username)
                // 有效期设置：在当前时间（毫秒数）+过期时间毫秒数
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        log.debug("JWT=" + JWT);
        return JWT;
    }

    /**
     * 验证JWT合法性
     */
    public static Claims checkToken(String token) {
        if (token != null) {
            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET)
                    // 去掉 Bearer 前缀
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();
            return claims;
        }
        return null;
    }
}
