package propensi.a04.wois.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity



public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/guest").permitAll()
                .antMatchers("/katalog/add", "/katalog/update/{id}", "/katalog/delete/{id}",
                        "/review/delete-admin/{idReview}", "/log","/user", "/user/**").hasAuthority("ROLE_Super Admin")
                .antMatchers("/grafik", "/reservasi/update/{idReservasi}" ).hasAuthority("ROLE_PIC Organizer")
                .antMatchers("/reservasi/reservasi-for-vendor", "/reservasi/reservasi-for-vendor/{idReservasi}").hasAuthority("ROLE_Vendor")
                .antMatchers("/aboutus","/contactus", "/review/add",
                        "/review/update/{idReview}", "/review/delete/{idReview}" ).hasAuthority("ROLE_Customer")

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll();
        return http.build();
    }
    // @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
    //     auth.inMemoryAuthentication()
    //             .passwordEncoder(encoder())
    //             .withUser("ayu")
    //             .password(encoder().encode("apapA"))
    //             .roles("admin");
    // }


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
