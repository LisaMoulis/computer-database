package ui;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import model.*;

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
		org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
		System.out.println("\n" + config.getProperties()+ "\n");
	    return config.addAnnotatedClass(Company.class).addAnnotatedClass(Computer.class).buildSessionFactory();
	}
	/*INFO: Initializing Spring root WebApplicationContext
17:51:39.374 [localhost-startStop-1] INFO org.springframework.web.context.ContextLoader - Root WebApplicationContext: initialization started
17:51:39.465 [localhost-startStop-1] DEBUG org.springframework.web.context.support.XmlWebApplicationContext - Refreshing Root WebApplicationContext
17:51:39.672 [localhost-startStop-1] DEBUG org.springframework.beans.factory.xml.XmlBeanDefinitionReader - Loaded 6 bean definitions from class path resource [applicationContext.xml]
17:51:39.729 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'
17:51:39.914 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Ignored because not a concrete top-level class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/command/Command.class]
17:51:39.921 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/command/CommandHandler.class]
17:51:39.942 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/service/CompanyService.class]
17:51:39.943 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/service/ComputerService.class]
17:51:39.943 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/service/PageService.class]
17:51:39.957 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/persistence/CompanyRequestHandler.class]
17:51:39.958 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/persistence/ComputerRequestHandler.class]
17:51:39.959 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/persistence/DBConnection.class]
17:51:39.964 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/mapper/ComputerDAOMapper.class]
17:51:39.964 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/mapper/ComputerDTOMapper.class]
17:51:39.965 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/mapper/PageListMapper.class]
17:51:39.979 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/ui/servlets/AddComputer.class]
17:51:39.981 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/ui/servlets/Computers.class]
17:51:39.982 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/ui/servlets/Delete.class]
17:51:39.983 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/ui/servlets/Edit.class]
17:51:39.984 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/ui/servlets/ErrorPages.class]
17:51:39.985 [localhost-startStop-1] DEBUG org.springframework.context.annotation.ClassPathBeanDefinitionScanner - Identified candidate component class: file [/home/excilys/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/training-java/WEB-INF/classes/ui/servlets/Home.class]
17:51:40.508 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerProcessor'
17:51:40.515 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.event.internalEventListenerFactory'
17:51:40.522 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'
17:51:40.524 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'org.springframework.context.annotation.internalCommonAnnotationProcessor'
17:51:40.532 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'messageSource'
17:51:40.536 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'ui.ContextConfig#0'
17:51:40.610 [localhost-startStop-1] DEBUG org.springframework.ui.context.support.UiApplicationContextUtils - Unable to locate ThemeSource with name 'themeSource': using default [org.springframework.ui.context.support.ResourceBundleThemeSource@7c238179]
17:51:40.619 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'commandHandler'
17:51:40.647 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'computerService'
17:51:40.652 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'computerRequestHandler'
17:51:40.661 [localhost-startStop-1] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'sessionFactory'*/
	
}
//Inversion de dépendance :  la classe est la mère, le nom est la classe fille
