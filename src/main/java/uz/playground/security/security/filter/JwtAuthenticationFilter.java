package uz.playground.security.security.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.security.JwtProvider;
import uz.playground.security.service.CustomUserDetailService;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService userDetailService;
    private final ResponseHelper responseHelper;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, CustomUserDetailService customUserDetailService, ResponseHelper responseHelper) {
        this.jwtProvider = jwtProvider;
        this.userDetailService = customUserDetailService;
        this.responseHelper = responseHelper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getServletPath());
        String jwt = request.getHeader("Authorization");
        if (StringUtils.hasText(jwt)) {
            try {
                jwt = jwt.substring(7);
                if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
                    Long userId = jwtProvider.getUserIdFromJWT(jwt);
                    UserDetails userDetails = userDetailService.loadUserById(userId);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception ex) {
                responseHelper.prepareResponse("Could not set user authentication in security context. " + ex.getMessage(), "error");
                logger.error("Could not set user authentication in security context", ex);
            }
        }
        filterChain.doFilter(request, response);
    }
}