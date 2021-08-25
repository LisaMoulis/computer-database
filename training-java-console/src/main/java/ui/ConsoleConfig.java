package ui;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import dto.CompanyDTO;
import dto.ComputerDTO;

@Configuration

@ComponentScan(basePackages = { "command","service","persistence","mapper","ui.servlets","training-java-persistence.src/main/java.persistence",
		"training-java-persistence.persistence" ,"training-java.training-java-persistence.persistence","training-java.training-java-persistence.src/main/java.persistence" })

public class ConsoleConfig {
	
	@Bean
	public HikariDataSource dataSource()
	{
		HikariConfig config = new HikariConfig("/datasource.properties");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return new HikariDataSource(config);
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
	    return new org.hibernate.cfg.Configuration().addAnnotatedClass(CompanyDTO.class).addAnnotatedClass(ComputerDTO.class).buildSessionFactory();
	}
	
	
}
//Inversion de dépendance :  la classe est la mère, le nom est la classe fille
