package com.security_03.filter;

import com.security_03.domain.LoginUser;
import com.security_03.uitls.JwtUtil;
import com.security_03.uitls.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

//对每一条请求进行过滤，过滤出token，再通过解析token来获得userid


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //注册调试
        String isRegister = request.getHeader("isRegister");
//        if (!StringUtils.hasText(isRegister))
//        {
//            filterChain.doFilter(request, response);
//        }
            if (!StringUtils.hasText(token)) {
                //如果不存在token，则直接放行, 后面会有springsecurity的异常过滤器进行过滤
                filterChain.doFilter(request, response);
                return;
            }
            // // 以下是对存在token的解析过程，并将解析结果authentication存入securityContext
            //解析token
            String userid;
            try {
                //通过token解析出实体
                Claims claims = JwtUtil.parseJWT(token);
                userid = claims.getSubject();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("token非法");
            }
            //从redis中获取用户信息
            String redisKey = "login:" + userid;
            LoginUser loginUser = redisCache.getCacheObject(redisKey);
            if (Objects.isNull(loginUser)) {
                throw new RuntimeException("用户未登录");
            }

            //将认证信息存入SecurityContextHolder，为该用户创建一个securityContext
            //TODO 获取权限信息封装到Authentication中
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //放行
            filterChain.doFilter(request, response);
    }
}