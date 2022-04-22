package ro.unibuc.fmi.javabookstoreproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Profile("test")
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
public class TestWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final UserSecurityConfig userSecurityConfig;

    @Autowired
    public TestWebSecurityConfigurerAdapter(UserSecurityConfig userSecurityConfig) {
        this.userSecurityConfig = userSecurityConfig;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        userSecurityConfig.getUsers()
                .stream()
                .map(user -> User.withUsername(user.getName())
                        .password(encoder.encode(user.getPass()))
                        .roles(user.getRoles()).build()
                )
                .forEach(manager::createUser);

        return manager;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().fullyAuthenticated();
        http.httpBasic();
        http.csrf().disable();

    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/actuator/*");
    }

}