package cn.lemongo97.wol.security.filter;

import cn.hutool.crypto.digest.MD5;
import cn.lemongo97.wol.common.ResultCode;
import cn.lemongo97.wol.config.Result;
import cn.lemongo97.wol.exception.TokenExpiredException;
import cn.lemongo97.wol.exception.TokenNotFoundException;
import cn.lemongo97.wol.utils.ResponseUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    private final static String REDIS_TOKEN_PREFIX = "string:wol-token:";
    private final static String HEADER_TOKEN_NAME = "X-Token";
    private final static String TOKEN_SECRET_KEY = "sang@123";

    private final RedisTemplate<String, String> redisTemplate;

    public JwtAuthenticationTokenFilter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String jwtToken = request.getHeader(HEADER_TOKEN_NAME);
            if (StringUtils.isBlank(jwtToken)){
                throw new TokenNotFoundException();
            }
            if (StringUtils.isBlank(redisTemplate.opsForValue().get(REDIS_TOKEN_PREFIX + MD5.create().digestHex(jwtToken)))) {
                throw new TokenExpiredException();
            }
            Claims claims = Jwts.parser().setSigningKey(TOKEN_SECRET_KEY).parseClaimsJws(jwtToken.replace("Bearer", "")).getBody();
            String username = claims.getSubject();
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(request, servletResponse);
        } catch (Exception e) {
            if ((e instanceof ExpiredJwtException) || (e instanceof TokenExpiredException)) {
                log.error(ResultCode.USER_TOKEN_EXPIRED.message(), e);
                ResponseUtils.writeAndFlush(servletResponse, Result.failure(ResultCode.USER_TOKEN_EXPIRED));
            } else {
                throw e;
            }
        }
    }
}
