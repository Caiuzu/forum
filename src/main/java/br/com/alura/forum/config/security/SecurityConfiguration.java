package br.com.alura.forum.config.security;

import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*todas configurações de segurança ficam aqui nessa classe*/

@EnableWebSecurity //habilita o modulo de segurança na aplicação
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    /*temos que sobreescrever alguns metodos do webSecurity*/

    @Autowired
    private AutenticacaoService AutenticacaoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    @Override /*metodo que configura autenticação*/
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(AutenticacaoService).passwordEncoder(new BCryptPasswordEncoder()); // encryt senha
    }

    @Override /*configuraçeõs de autorização (quyem pode acessar url, perfil de acesso), todas novas url devem ser adicionadas aqui*/
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers(HttpMethod.GET,"/topicos").permitAll()
        .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
        .antMatchers(HttpMethod.POST,"/auth").permitAll()
        .antMatchers(HttpMethod.GET,"/actuator/**").permitAll()
        .anyRequest().authenticated()
        .and().csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
        //.and().formLogin();//formulario padrao do spring
    }

    @Override /*configuração de recursos estáticos (css, javascript, img, etc)*/
    public void configure(WebSecurity web) throws Exception {
    // Se o front fosse integrado, seria configurado para nao ser bloqueado o front/documentaçao aqui, não ser interceptado na area de segurança
        web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }

    /*public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("senha123456")); // metodo para encripitar senha de teste
    }*/
}
