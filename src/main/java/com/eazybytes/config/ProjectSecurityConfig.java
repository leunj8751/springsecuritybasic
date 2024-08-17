package com.eazybytes.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests((requests) -> requests
            .requestMatchers("/myAcount","/myBalance","/myCards","/myLoans").authenticated()
            .requestMatchers("/notices","/contact","/register").permitAll()
        );
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    /*
    @Bean
     public InMemoryUserDetailsManager userDetailsService() {

         // use withDefaultPasswordEncoder
//         UserDetails admin = User.withDefaultPasswordEncoder()
//                 .username("admin")
//                 .password("1234")
//                 .authorities("admin")
//                 .build();
//         UserDetails user = User.withDefaultPasswordEncoder()
//                 .username("user")
//                 .password("1234")
//                 .authorities("read")
//                 .build();
//         return new InMemoryUserDetailsManager(admin,user);

        //use NoOpPasswordEncoder Bean
         UserDetails admin = User.withUsername("admin")
                 .password("1234")
                 .authorities("admin")
                 .build();
         UserDetails user = User.withUsername("user")
                 .password("1234")
                 .authorities("read")
                 .build();
         return new InMemoryUserDetailsManager(admin,user);

    }
    */

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
