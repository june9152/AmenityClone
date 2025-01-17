package com.sparta.amenityclonecoding.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.amenityclonecoding.dto.SecurityExceptionDto;
import com.sparta.amenityclonecoding.entity.User;
import com.sparta.amenityclonecoding.repository.UserRepository;
import com.sparta.amenityclonecoding.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.sparta.amenityclonecoding.util.JwtUtil.ACCESS_KEY;
import static com.sparta.amenityclonecoding.util.JwtUtil.REFRESH_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //필터체인 : 필터가 체인형식으로 묶여있어 필터끼리 이동이 가능
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //헤더에 있는 엑세스, 리프레쉬 토큰 가져옴
        String access_token = jwtUtil.resolveToken(request, ACCESS_KEY);
        String refresh_token = jwtUtil.resolveToken(request, REFRESH_KEY);

        //엑세스 토큰 유무부터 검사
        if (access_token != null) {
            // 액세스 토큰 유효성 검사
            if (jwtUtil.validateToken(access_token)) {
                //엑세스토큰이 이상이 있으면 여기서 걸림(false가 정상)
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token));
            }
            // 액세스 토큰 만료 && 리프레시 토큰이 존재 -> 리프레시 토큰 검증(유효성, DB 존재 여부 확인
            else if (refresh_token != null && jwtUtil.refreshTokenValidation(refresh_token)) {
                // 리프레시 토큰으로 username, Member DB에서 username을 가진 member 가져오기
                String userEmail = jwtUtil.getUserInfoFromToken(refresh_token);
                User user = userRepository.findUserByUserEmail(userEmail).get();
                // 새로운 액세스 토큰 발급
                String newAccessToken = jwtUtil.createToken(userEmail, user.getRole(), "Access");
                // 헤더에 액세스 토큰 추가
                jwtUtil.setHeaderAccessToken(response, newAccessToken);
                // Security context에 인증 정보 넣기
                setAuthentication(userEmail);
            } else if (refresh_token == null) {
                jwtExceptionHandler(response, "AccessToken has Expired. Please send your RefreshToken together.", HttpStatus.BAD_REQUEST.value());
            }
            // (토큰 만료 && 리프레시 토큰 만료) || 리프레시 토큰이 DB와 비교했을 때 같지 않다면
            else {
                jwtExceptionHandler(response, "RefreshToken Expired", HttpStatus.BAD_REQUEST.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }


    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

