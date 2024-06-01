package rs.ac.uns.ftn.Bookify.config;

import jakarta.servlet.DispatcherType;
import org.keycloak.adapters.authorization.integration.jakarta.ServletPolicyEnforcerFilter;
import org.keycloak.adapters.authorization.spi.ConfigurationResolver;
import org.keycloak.adapters.authorization.spi.HttpRequest;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.Bookify.config.filters.JWTAuthenticationFilter;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.service.CustomUserDetailService;

import java.io.IOException;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwkSetUri;

    @Bean
    public WebMvcConfigurer CORSConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("https://localhost:4200").allowedMethods("GET", "PUT", "POST", "DELETE").allowCredentials(true);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.securityContext((securityContext) -> securityContext.securityContextRepository(new RequestAttributeSecurityContextRepository()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> CORSConfigurer());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint));
        http.authorizeHttpRequests(request ->{
            request.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/accommodations/image/{imageId}","/api/v1/accommodations/search", "/api/v1/accommodations/details/{accommodationId}",
                            "/api/v1/accommodations/top-accommodations", "/api/v1/accommodations/top-locations", "/api/v1/users/image/{imageId}", "/api/v1/users/forgot-password/{email}",
                            "/api/v1/accommodations/images/{accommodationId}", "/api/v1/reviews/accommodation/{accommodationId}", "/api/v1/reviews/owner/{ownerId}", "/api/v1/users/user/{userId}",
                            "/api/v1/reviews/owner/{ownerId}", "/api/v1/reviews/owner/{ownerId}/rating", "/api/v1/reviews/accommodation/{accommodationId}/rating").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/accommodations/filter", "/sendMessageRest").permitAll()
                    .anyRequest().authenticated()
            ;
        });
        http.oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()));
        http.addFilterAfter(createPolicyEnforcerFilter(), BearerTokenAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.POST,"/socket/**", "/ws/**")
                .requestMatchers(HttpMethod.GET, "/", "/webjars/*", "/*.html", "favicon.ico",
                        "/*/*.html", "/*/*.css", "/*/*.js", "/socket/**", "/ws/**");

    }

    private ServletPolicyEnforcerFilter createPolicyEnforcerFilter() {
        PolicyEnforcerConfig config;

        try {
            config = JsonSerialization.readValue(getClass().getResourceAsStream("/policy-enforcer.json"), PolicyEnforcerConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ServletPolicyEnforcerFilter(new ConfigurationResolver() {
            @Override
            public PolicyEnforcerConfig resolve(HttpRequest request) {
                return config;
            }
        });
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(this.jwkSetUri).build();
    }
}
