package ui;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration

@ComponentScan(basePackages = { "command","service","persistence","mapper","ui.servlets" })
public class ContextConfig extends WebMvcConfigurationSupport {
	
	@Bean
	public HikariDataSource dataSource()
	{
		HikariConfig config = new HikariConfig("/datasource.properties");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return new HikariDataSource(config);
	}
	
}
//Inversion de dépendance :  la classe est la mère, le nom est la classe fille
