package com.wangmang.study.configuration.auth.jwt;

import com.wangmang.study.configuration.auth.UserDetailsServiceImpl;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
//@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    //request, response filter 처리
   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
                //요청에서 token만을 파싱
                String jwt = parseJwt(request);
                //유효한 토큰인지 검증
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    //토큰을 통해 username을 얻음
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);
                    //얻은 username으로 사용자 정보 얻음
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //security 컨텍스트에 사용자 인증정보 저장
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            } catch (Exception e) {
                log.error("Cannot set user authentication: {}", e);
            }

            filterChain.doFilter(request, response);

        }


    //http request 헤더에서 jwt토큰 파싱
    private String parseJwt(HttpServletRequest request){
        String headerToken = request.getHeader("Authorization");

        //인증헤더 유무체크 & 토큰타입 체크
        if(StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer")){

            //토큰부분만을 파싱
           return headerToken.substring(7, headerToken.length());
        }
        return null;
    }
}