package com.syscawfit.syscawfit.securityweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UsuarioPrincipalDetailsService userPrincipalDetailsService;

    public WebSecurityConfig(UsuarioPrincipalDetailsService userPrincipalDetailsService) {
        this.userPrincipalDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/profile/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/management/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/api/public/users").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/signin")
                .loginPage("/login").permitAll()
                .usernameParameter("txtUsername")
                .passwordParameter("txtPassword")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and();
//                .rememberMe().tokenValiditySeconds(2592000).key("mySecret!").rememberMeParameter("checkRememberMe");
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * Um exemplo de uma simples autenticação de usuário
// * Intercepta as requisicoes
// */
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    // para autenticar um usuario precisamos de uma service
//    // que vai responder se as credenciais do usuario sao validas
//    //@Autowired
//    private com.syscawfit.syscawfit.dao.UsuarioRepository userServ;
//
//    // precisamos tambem dizer como sera realizado a autenticacao sobrescrevendo o
//    // metodo configure
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // nesse ponto a UsuarioService é chamada para consultar o database
//        //alterar aqui
//        auth.userDetailsService(userDetailsService());
//    }
//
//    /* setamos o bcrypt como metodo de criptografia para codificar o password */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // painel admin do H2 - remover quando em producao
//    @Override
//    public void configure(WebSecurity web) {
//        // libera acesso ao padrao de url:
//        web.ignoring().antMatchers("/h2admin/**");
//    }
//
//    // libera acesso a todos os recursos (para desenvolvimento e pular a tela de login)
//// @Override
//// protected void configure(HttpSecurity http) throws Exception {
////  http.authorizeRequests().anyRequest().permitAll();
//// }
//
//    /**
//     * CONFIGURACAO do esquema de autenticao baseado em sessao HttpSecurity eh uma
//     * classe builder altamente customizavel com metodos que sao executados em
//     * sequencia Cada metodo adiciona um filtro que eh aplicado para cada request
//     * HTTP
//     */
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/login") // deixando a tela de login publica
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().formLogin()
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID");
//        httpSecurity.headers().frameOptions().disable();
//    }
//
//}
