package com.Organizer.organizer.config;

import java.beans.Customizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.Organizer.organizer.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class securityConfig  {
    @Autowired
    private OAuthAuthenticationSuccessHandler Handler;

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
            // .failureForwardUrl("/login?error=true")
            // .successForwardUrl()
            .usernameParameter("email")
            .passwordParameter("password");
            // .failureHandler(new AuthenticationFailureHandler() {
        //     @Override
        //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        //         throw new UnsupportedOperationException("Not found") 
            
        //     }
        //     });
            
        });
    // protected void configure(HttpSecurity http) throws Exception {
    //     http
    //         .authorizeRequests()
    //             .antMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
    //             .anyRequest().authenticated()
    //         .and()
    //         .formLogin()
    //             .loginPage("/login")
    //             .defaultSuccessUrl("/home", true)
    //             .permitAll()
    //         .and()
    //         .logout()
    //             .permitAll();
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.logout(logutP->{
        logutP.logoutUrl("/logout");
        logutP.logoutSuccessUrl("/login?logout=true");
    });

    //auth configuration for google
    httpSecurity.oauth2Login(oauth->{
        oauth.loginPage("/login");
        oauth.successHandler(Handler);
    });
    return httpSecurity.build();

    }
}

