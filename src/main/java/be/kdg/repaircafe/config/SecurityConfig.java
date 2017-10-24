package be.kdg.repaircafe.config;

import be.kdg.repaircafe.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Configures security aspects
 * <p>
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/frontend-spring-security/
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/root-context-config/
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/springs-integratie-met-webserver/
 * <p>
 * Also see: https://stackoverflow.com/questions/29721098/enableglobalmethodsecurity-vs-enablewebsecurity
 *
 * @author wouter
 */
@Configuration
@EnableGlobalMethodSecurity
public class SecurityConfig {

    // Constructor injection doesn't work here because of circular dependency
    // Field injection or setter injection to the rescue
    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userService).
                passwordEncoder(passwordEncoder());
    }

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatcher(request -> {
                final String url = request.getServletPath() + request.getPathInfo();
                return !(url.startsWith("/api/"));
            });

            http.authorizeRequests()
                    .antMatchers("/topic/**", "/register.do").permitAll()
                    .antMatchers("/client/**").access("hasRole('ROLE_CLIENT')")
                    .antMatchers("/repairer/**").access("hasRole('ROLE_REPAIRER')")
                    .anyRequest().authenticated()      // remaining URL's require authentication
                    .and()
                    .formLogin()
                    .loginPage("/login.do")
                    .failureUrl("/login-error")
                    .permitAll()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                    .csrf().disable();
        }
    }

    @Configuration
    @Order(2)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/logout").authenticated()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/api/login")
                    .successHandler(new SimpleUrlAuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                            clearAuthenticationAttributes(request);
                        }
                    })
                    .failureHandler(new SimpleUrlAuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                            super.onAuthenticationFailure(request, response, exception);
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getOutputStream().println("{ \"error\": \"" + exception.getMessage() + "\" }");
                        }
                    })
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler((request, response, authentication) -> response.setStatus(HttpServletResponse.SC_OK))
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
                    })
                    .and()
                    .csrf()
                    .disable();
        }
    }
}
