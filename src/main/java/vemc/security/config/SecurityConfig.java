package vemc.security.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import vemc.security.error.CustomOAuth2AccessDeniedHandler;
import vemc.security.error.CustomOAuth2AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

@Configuration
public class SecurityConfig {

  @Value("${app.secret-key}")
  private String tokenSecret;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    http
            .cors(Customizer.withDefaults()) //Will use the CorsConfigurationSource bean declared in CorsConfig.java
            .csrf(csrf -> csrf.disable())  //We can disable csrf, since we are using token based authentication, not cookie based
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .oauth2ResourceServer((oauth2ResourceServer) ->
                    oauth2ResourceServer
                            .jwt((jwt) -> jwt.decoder(jwtDecoder())
                                    .jwtAuthenticationConverter(authenticationConverter())
                            )
                            .authenticationEntryPoint(new CustomOAuth2AuthenticationEntryPoint())
                            .accessDeniedHandler(new CustomOAuth2AccessDeniedHandler()));

    http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/auth/login")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/user-with-role")).anonymous() //Clients can create a user for themself

            //Allow index.html for anonymous users
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/index.html")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/")).permitAll()

            // Allow all user cinema endpoints
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/cinemas")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/cinemas/*")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/cinemas/*/movies")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/movies/*")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/movies")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/screenings")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/screenings/*/cancel")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/reservations")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservations")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservations/*")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/reservations/*/tickets")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/reservations/*/tickets")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PUT, "/reservations/*/tickets")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.DELETE, "/reservations/*/tickets/*")).anonymous()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.PATCH, "/reservations/*/complete")).anonymous()


            // Allow all user endpoints
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/screenings/*/cancel")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/screenings/*")).permitAll()

            //Allow for swagger-ui
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/swagger-ui/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/swagger-resources/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/v3/api-docs/**")).permitAll()

            //Required for error responses
            .requestMatchers(mvcMatcherBuilder.pattern("/error")).permitAll()


            //This is for demo purposes only, and should be removed for a real system
            //.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/test/user-only")).hasAuthority("USER")
            //.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.GET, "/api/test/admin-only")).hasAuthority("ADMIN")

            //Use this to completely disable security (Will not work if endpoints has been marked with @PreAuthorize)
            //.requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll());
            .anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationConverter authenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }

  public SecretKey secretKey() {
    return new SecretKeySpec(tokenSecret.getBytes(), "HmacSHA256");
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withSecretKey(secretKey()).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey()));
  }

}
