package ca.dheri.AQHI.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/").permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated())
                .headers((headers) -> headers
                        .defaultsDisabled()
                        .cacheControl(withDefaults())
                        .frameOptions((f) -> f.disable())
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers(toH2Console()))
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionConcurrency((sessionConcurrency) -> sessionConcurrency.maximumSessions(1).expiredUrl("/login?expired")))
                .logout((l -> l.logoutSuccessUrl("/").permitAll()));

        return http.build();
    }

}