package be.kdg.repaircafe.config;

import be.kdg.repaircafe.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configures security aspects
 * <p>
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/frontend-spring-security/
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/root-context-config/
 * https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/springs-integratie-met-webserver/
 *
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
}
