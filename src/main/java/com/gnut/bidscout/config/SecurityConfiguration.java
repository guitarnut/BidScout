package com.gnut.bidscout.config;

import com.gnut.bidscout.service.user.MongoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    MongoUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/health/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user/create/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/user/login/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/bid/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/vast/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/click/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/event/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/sync/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) {
        builder.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));
        configuration.setAllowedHeaders(Arrays.asList(
                "authorization", "content-type", "x-auth-token","access-control-allow-origin"
        ));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


/**
 * @Override protected void configure(HttpSecurity http) throws Exception {
 * <p>
 * //                .and()
 * //                .authorizeRequests().antMatchers("/bid/**").hasRole(Users.Role.ADMIN.getValue())
 * <p>
 * //.antMatchers("/**").access("hasRole('" + Users.Role.ADMIN.getValue() + "')")
 * //.anyRequest().authenticated()
 * http.authorizeRequests()
 * .antMatchers("/login**").permitAll()
 * .antMatchers("/healthcheck**").permitAll()
 * .antMatchers("/static/**").permitAll()
 * .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
 * .antMatchers("/**").access("hasRole('ROLE_USER')")
 * .and()
 * .formLogin().loginPage("/login").failureUrl("/login?error")
 * .usernameParameter("username").passwordParameter("password")
 * .and()
 * .logout().logoutSuccessUrl("/login?logout")
 * .and()
 * .exceptionHandling().accessDeniedPage("/403")
 * .and()
 * .csrf();
 * <p>
 * }
 * NOTE: This is a first match wins so you may need to play with the order. For example, I originally had /** first:
 * <p>
 * .antMatchers("/**").access("hasRole('ROLE_USER')")
 * .antMatchers("/login**").permitAll()
 * .antMatchers("/healthcheck**").permitAll()
 * Which caused the site to continually redirect all requests for /login back to /login. Likewise I had /admin/** last:
 * <p>
 * .antMatchers("/**").access("hasRole('ROLE_USER')")
 * .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
 * Which resulted in my unprivledged test user "guest" having access to the admin interface (yikes!)
 */
