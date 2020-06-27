package com.vijay.sfcp.obrs.config.security;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/

import com.vijay.sfcp.obrs.common.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private final String CLASS_NAME = this.getClass().getName();

    private AuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("daoAuthenticationProvider")
    public AuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean("httpSessionEventPublisher")
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeRequests()
                .antMatchers("/*").anonymous()
                .antMatchers("/*").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                /*.antMatchers("/resources/**","/resources/static/images/**","/resources/static/images/books/*","/resources/static/css/**","/resources/static/js/**").permitAll()
                .antMatchers("/webjars/**","uploads/**").permitAll()*/
        httpSecurity.authorizeRequests()
                .antMatchers("/","/console/**", "/index", "/contact", "/about", "/error/**").permitAll()
                .antMatchers("/login","/home", "/user/registration/**").permitAll()
                .antMatchers("/book/**").permitAll()
                .antMatchers("/book/byCategory/**").permitAll()
                .antMatchers("/book/bookDetail/**").permitAll()
                .antMatchers("/book/byAuthor/**").permitAll()
                .antMatchers("/book/searchPage/**","/book/search/**").permitAll()
                .antMatchers("/review/id/**").permitAll()
                .antMatchers("/file/getImage/**").permitAll()
                .antMatchers("/invalidSession","/sessionExpired").permitAll();

        httpSecurity.authorizeRequests()
                .antMatchers("/", "/index", "/contact", "/about", "/error/**").hasAnyAuthority("ROLE_USER", "ROLE_PUBLISHER", "ROLE_ADMIN")
                .antMatchers("/review/save/**").hasAnyAuthority("ROLE_USER", "ROLE_PUBLISHER", "ROLE_ADMIN")
                .antMatchers("/book/byPublisher/**","/book/save/**","/book/delete/**").hasAuthority("ROLE_PUBLISHER")
                .antMatchers("/category/**").hasAuthority("ROLE_PUBLISHER")
                .antMatchers("/author/**").hasAuthority("ROLE_PUBLISHER")
                .antMatchers("/file/uploadFile/**").hasAuthority("ROLE_PUBLISHER")
                .antMatchers("/user/byRole/**","/user/actDeact/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated();

        httpSecurity.sessionManagement()
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        httpSecurity.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/book/byCategory?category=all", true);

        httpSecurity.logout().deleteCookies("JSESSIONID");
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        httpSecurity.sessionManagement().invalidSessionUrl("/invalidSession").maximumSessions(2).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry()).expiredUrl("/sessionExpired");

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Bean("sessionRegistry")
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean("accessDeniedHandler")
    public AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerCustomImpl handler = new AccessDeniedHandlerCustomImpl();
        handler.setErrorPage("/accessDenied");
        return handler;
    }
}
