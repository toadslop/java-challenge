package jp.co.axa.apidemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * A class defining the security parameters for the Employee api.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /**
   * A container for variables loaded from a .env file. This file should contain two variables:
   * ADMIN_USER and ADMIN_PASS, which hold the username of the admin user and the password for the
   * admin user respectively.
   */
  private Dotenv dotenv = Dotenv.load();

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser(dotenv.get("ADMIN_USER"))
        .password("{noop}" + dotenv.get("ADMIN_PASS")).roles("ADMIN");
  }
}
