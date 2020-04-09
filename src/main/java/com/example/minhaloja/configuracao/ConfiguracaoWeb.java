package com.example.minhaloja.configuracao;

import com.example.minhaloja.servico.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableWebSecurity
public class ConfiguracaoWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetails;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
       .antMatchers("/fazer_pedido", "/novo_pedido", "/pedidos")
       .authenticated().anyRequest().permitAll().and().formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("Maicao")
        //         .password("123").roles("ADMIN");
        auth.userDetailsService(userDetails).passwordEncoder(new BCryptPasswordEncoder());
    }

    // @Bean
    // @Override
    // public UserDetailsService userDetailsService() {
    //     UserDetails user = User.withDefaultPasswordEncoder().username("aluno").password("aluno").roles("USER").build();

    //     return new InMemoryUserDetailsManager(user);
    // }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }
}