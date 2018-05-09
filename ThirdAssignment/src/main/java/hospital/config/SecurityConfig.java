package hospital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ShaPasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder();
    }
    @Autowired
    private DataSource dataSource;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery("select username,role from users where username = ?")
                .rolePrefix("ROLE_")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                    .authorizeRequests()
                    .antMatchers("/js/**","/").permitAll()
                    .antMatchers("/logout","/admin").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/logout","/secretary","/crudConsultations","/updatePatients").access("hasRole('ROLE_SECRETARY')")
                    .antMatchers("/logout","/doctor").access("hasRole('ROLE_DOCTOR')")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(loginSuccessHandler)
                .and()
                    .logout()
                    .permitAll();
    }
}
