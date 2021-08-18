package ui;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import model.*;

@Configuration

@ComponentScan(basePackages = { "command","service","persistence","mapper","ui.servlets","training-java-persistence.src/main/java.persistence",
		"training-java-persistence.persistence" ,"training-java.training-java-persistence.persistence","training-java.training-java-persistence.src/main/java.persistence" })
@EnableWebMvc
@EnableWebSecurity
public class ContextConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public HikariDataSource dataSource()
	{
		HikariConfig config = new HikariConfig("/datasource.properties");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return new HikariDataSource(config);
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/static/views/");
	    bean.setSuffix(".jsp");
	    return bean;
	}
	
	/*@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.US);
	    return slr;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(localeChangeInterceptor());
	}*/
	@Bean
	public PasswordEncoder encoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	/*@Bean
	public UserDetailsService users() {
	    // The builder will ensure the passwords are encoded before saving in memory
	    UserBuilder builder = User.builder();
	    UserDetails user = builder
	        .username("user")
	        .password("password")
	        .roles("USER")
	        .build();
	    UserDetails admin = builder
	        .username("admin")
	        .password("password")
	        .roles("USER", "ADMIN")
	        .build();
	    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource());
	    users.createUser(user);
	    users.createUser(admin);
	    return users;
	}*/
	
	
	
	@Bean
    public UserDetailsService users() throws Exception {
        return super.userDetailsServiceBean();
    }
	
	@Bean
	public ResourceBundleMessageSource messageSource() {

		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
	    source.setBasenames("/messages");
	    source.setUseCodeAsDefaultMessage(true);
	       //source.setDefaultEncoding("UTF-8");

	    return source;
	}
	
	@Bean
	public SessionFactory sessionFactory() 
	{	
	    return new org.hibernate.cfg.Configuration().addAnnotatedClass(Company.class).addAnnotatedClass(Computer.class).buildSessionFactory();
	}
	@Bean
	public DigestAuthenticationEntryPoint entryPoint() {
	    DigestAuthenticationEntryPoint result = new DigestAuthenticationEntryPoint();
	    result.setRealmName("Please authenticate");
	    result.setKey("a key");
	    return result;
	}

	@Bean
	public DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
	    DigestAuthenticationFilter result = new DigestAuthenticationFilter();
	    result.setUserDetailsService(users());
	    result.setAuthenticationEntryPoint(entryPoint());
	    //result.setPasswordAlreadyEncoded(true);
	    return result;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
        .withUser("user")
            .password(encoder().encode("digestsecret"))
            .roles("USER")
    .and()
        .withUser("TestAdmin")
            .password(encoder().encode("adminsecret"))
            .roles("ADMIN");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {

	    http
	      .csrf().disable()
	      .authorizeRequests()
	      .antMatchers("/edit*").hasRole("USER")
	      .antMatchers("/add*").hasRole("USER")
	      .antMatchers("/login*").permitAll()
	      .antMatchers("/computers*").permitAll()
	      .anyRequest().authenticated()
	      .and()
	      //.formLogin()
	      //.loginPage("/login")
	      //.defaultSuccessUrl("/computers", true)
	      //.and()
	      .logout()
	      //.logoutUrl("/logout")
	      .deleteCookies("JSESSIONID")
	      .and().addFilter(digestAuthenticationFilter()).exceptionHandling()
			.authenticationEntryPoint(entryPoint())
	      //.logoutSuccessHandler(logoutSuccessHandler());
	      ;
	}
	
	
}
//Inversion de dépendance :  la classe est la mère, le nom est la classe fille
