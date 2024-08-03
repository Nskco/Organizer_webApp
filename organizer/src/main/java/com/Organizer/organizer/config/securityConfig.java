package com.Organizer.organizer.config;

import java.beans.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Organizer.organizer.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class securityConfig  {


    @Autowired
    private CustomUserDetailsService userDetailsService;

    // //in memory
    // @Bean
    // public UserDetailsService userDetailsService(){
    //     UserDetails user1=User.withUsername("admin123")
    //     .password("123")
    //     .roles("ADMIN","USER")
    //     .build();

    //     var inMermoryUserDetailsManager=new InMemoryUserDetailsManager(user1);
    //     return  inMermoryUserDetailsManager;
    // } 
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //user detail object
        authProvider.setUserDetailsService(userDetailsService);
        //object of password encoders is passed
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.authenticationProvider(authenticationProvider());
    // }

        /**
         * @param httpSecurity
         * @return
         * @throws Exception
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
            httpSecurity.authorizeHttpRequests(authorize->{
            // {authorize.requestMatchers("/home","/register").permitAll();
            authorize.requestMatchers("/user","/user/**").authenticated();
            authorize.anyRequest().permitAll();

        });
        //formpage Default login
        httpSecurity.formLogin(loginPage->{
            loginPage.loginPage("/login")
            .loginProcessingUrl("/loginAuth")
            .successForwardUrl("/user/dashboard")
           
            .usernameParameter("email")
            .passwordParameter("password");
           
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.logout(logutP->{
        logutP.logoutUrl("/logout");
        logutP.logoutSuccessUrl("/login?logout=true");
    });


    return httpSecurity.build();

    }
}

