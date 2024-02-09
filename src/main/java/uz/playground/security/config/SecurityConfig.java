package uz.playground.security.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.security.CustomAuthenticationEntryPoint;
import uz.playground.security.security.JwtProvider;
import uz.playground.security.security.filter.JwtAuthenticationFilter;
import uz.playground.security.service.CustomUserDetailService;
import javax.servlet.Filter;
import javax.swing.text.html.FormSubmitEvent;
import java.util.List;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final ResponseHelper responseHelper;
    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, JwtProvider jwtProvider, ResponseHelper responseHelper) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jwtProvider = jwtProvider;
        this.responseHelper = responseHelper;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .authenticationProvider(authenticationProvider())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/images/**", "/js/**", "/webjars/**")
                .permitAll()
                .antMatchers("/taksibor/auth0/**", "/taksibor/comment/**",
                        "/taksibor/post/**", "/taksibor/region/**", "/taksibor/district/**")
                .permitAll()
                .antMatchers( "/v1-docs/**")
                .permitAll()
                .antMatchers(String.valueOf(FormSubmitEvent.MethodType.POST), "/taksibor/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    public Filter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider, (CustomUserDetailService) userDetailsService(), responseHelper);
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailService();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder()  {
        return new BCryptPasswordEncoder(5);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public CorsConfigurationSource configuration(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}