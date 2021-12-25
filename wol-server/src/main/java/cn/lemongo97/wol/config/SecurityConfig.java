package cn.lemongo97.wol.config;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.MD5;
import cn.lemongo97.wol.common.ResultCode;
import cn.lemongo97.wol.security.filter.JwtAuthenticationTokenFilter;
import cn.lemongo97.wol.security.filter.LoginFilter;
import cn.lemongo97.wol.service.UserService;
import cn.lemongo97.wol.utils.ResponseUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String REDIS_TOKEN_PREFIX = "string:wol-token:";
    private final static String HEADER_TOKEN_NAME = "X-Token";
    private final static String TOKEN_SECRET_KEY = "sang@123";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**", "/ws/*");
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            StringBuffer stringBuffer = new StringBuffer();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                stringBuffer.append(authority.getAuthority()).append(",");
            }
            String token = Jwts.builder()
                    .claim("authorities", stringBuffer)
                    .setSubject(authentication.getName())
                    .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET_KEY)
                    .compact();
            redisTemplate.opsForValue().setIfAbsent(REDIS_TOKEN_PREFIX + MD5.create().digestHex(token), token, 60, TimeUnit.MINUTES);
            ResponseUtils.writeAndFlush(response, Result.success(MapUtil
                    .builder()
                    .put("user", authentication.getPrincipal())
                    .put("token", token)
                    .build()));
        });
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            Result failure;
            if (exception instanceof LockedException) {
                failure = Result.failure(ResultCode.USER_ACCOUNT_LOCKED, exception.getStackTrace());
            } else if (exception instanceof CredentialsExpiredException) {
                failure = Result.failure(ResultCode.USER_PASSWD_EXPIRED, exception.getStackTrace());
            } else if (exception instanceof AccountExpiredException) {
                failure = Result.failure(ResultCode.USER_ACCOUNT_EXPIRED, exception.getStackTrace());
            } else if (exception instanceof DisabledException) {
                failure = Result.failure(ResultCode.USER_ACCOUNT_FORBIDDEN, exception.getStackTrace());
            } else if (exception instanceof BadCredentialsException) {
                failure = Result.failure(ResultCode.USER_LOGIN_ERROR, exception.getStackTrace());
            } else {
                failure = Result.failure(ResultCode.USER_ACCOUNT_UNKNOWN_EXCEPTION, exception.getStackTrace());
            }
            ResponseUtils.writeAndFlush(response, failure);
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler((request, response, authentication) -> {
                    String token = request.getHeader(HEADER_TOKEN_NAME);
                    redisTemplate.delete(REDIS_TOKEN_PREFIX + MD5.create().digestHex(token));
                })
                .logoutSuccessHandler((request, response, authentication) -> ResponseUtils.writeAndFlush(response, Result.success("注销成功！")))
                .and()
                .addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new JwtAuthenticationTokenFilter(redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }
}