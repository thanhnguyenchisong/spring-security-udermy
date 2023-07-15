package org.ncst.mine.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    /**
     * Config the security
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
             requests.requestMatchers("/account/*", "/loan/*", "/card/*", "/balance/*").authenticated() //authentication
                     .requestMatchers("/notice/*", "/contact/*").permitAll() //permit
                     .anyRequest().denyAll(); // deny others urls
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /**
     * Initializing 2 users
     * @return
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin").authorities("admin").build();
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("user").authorities("read").build();
        return new InMemoryUserDetailsManager(admin, user);
    }
}
