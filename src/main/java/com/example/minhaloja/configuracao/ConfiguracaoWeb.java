package com.example.minhaloja.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
public class ConfiguracaoWeb extends WebSecurityConfigurerAdapter{

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/fazer_pedido", "/novo_pedido", "/pedidos")
                .authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin(); 
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
             User.withDefaultPasswordEncoder()
                .username("aluno")
                .password("aluno")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/h2-console/**");
    }
}