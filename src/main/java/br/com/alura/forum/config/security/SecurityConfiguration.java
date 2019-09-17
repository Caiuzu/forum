package br.com.alura.forum.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*todas configurações de segurança ficam aqui nessa classe*/

@EnableWebSecurity //habilita o modulo de segurança na aplicação
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /*temos que sobreescrever alguns metodos do webSecurity*/

    @Override /*metodo que configura autenticação*/
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

    @Override /*configuraçeõs de autorização (quyem pode acessar url, perfil de acesso)*/
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(HttpMethod.GET,"/topicos").permitAll()
        .antMatchers(HttpMethod.GET,"/topicos/*").permitAll();
    }

    @Override /*configuraçãod e recursos estáticos (css, javascript, img, etc)*/
    public void configure(WebSecurity web) throws Exception {
    // Se o front fosse integrado, seria configurado para nao ser bloqueado o front aqui, não ser interceptado na area de segurança
    }
}
