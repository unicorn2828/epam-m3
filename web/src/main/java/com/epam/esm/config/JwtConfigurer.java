package com.epam.esm.config;

import com.epam.esm.filter.JwtTokenFilter;
import com.epam.esm.jwt.JwtTokenProvider;
import lombok.Data;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This is the JwtConfigurer class; it uses for JWT security configuration.
 *
 * @author Vitaly Kononov
 * @version 1.0
 */
@AllArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtConfigurer that = (JwtConfigurer) o;
        return Objects.equals(jwtTokenProvider, that.jwtTokenProvider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwtTokenProvider);
    }
}

