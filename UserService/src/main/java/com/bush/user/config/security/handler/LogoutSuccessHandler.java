package com.bush.user.config.security.handler;

import com.bush.user.config.security.SecurityConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    private final RedisTemplate<String, String> blackListTokenRedisTemplate;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String jwtToken = request.getHeader("Authorization").substring(7);
        blackListTokenRedisTemplate.opsForValue().set(SecurityConstants.BLACKLIST_KEY_PREFIX.getValue() + jwtToken, jwtToken);
        blackListTokenRedisTemplate.expire(SecurityConstants.BLACKLIST_KEY_PREFIX.getValue() + jwtToken,
                15, TimeUnit.MINUTES);

        SecurityContextHolder.clearContext();
    }
}
