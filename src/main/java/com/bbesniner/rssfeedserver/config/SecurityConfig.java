package com.bbesniner.rssfeedserver.config;

import com.bbesniner.rssfeedserver.security.jwt.JwtConfigurer;
import com.bbesniner.rssfeedserver.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    // TODO : Access authorization should be set once API is mostly done
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/feeds/**").permitAll()
                    //.antMatchers(HttpMethod.GET, "/feeds/**").permitAll()
                    //.antMatchers(HttpMethod.DELETE, "/feeds/**").authenticated()
//                  .antMatchers(HttpMethod.GET, "/feeds/whatever/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .apply(new JwtConfigurer(this.jwtTokenProvider));
    }

}
