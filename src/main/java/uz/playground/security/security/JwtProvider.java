package uz.playground.security.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uz.playground.security.helper.ResponseHelper;
import java.security.Key;
import java.util.Date;
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final ResponseHelper responseHelper;
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    public JwtProvider(ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }
    public String generateToken(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .claim("userRole", userPrincipal.getAuthorities())
                .claim("username", userPrincipal.getPhoneNumber())
                .claim("email", userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }
    public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean validateToken(String authToken){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            responseHelper.prepareResponse("Invalid JWT token.", "error");
            logger.error("Invalid JWT token.");
        } catch (ExpiredJwtException ex) {
            responseHelper.prepareResponse("Expired JWT token.", "error");
            logger.error("Expired JWT token.");
        } catch (UnsupportedJwtException ex) {
            responseHelper.prepareResponse("Unsupported JWT token.", "error");
            logger.error("Unsupported JWT token.");
        } catch (IllegalArgumentException ex) {
            responseHelper.prepareResponse("JWT claims string is empty.", "error");
            logger.error("Unsupported JWT token.");
        }
        return false;
    }
}