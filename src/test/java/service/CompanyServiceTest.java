package service;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.zaxxer.hikari.HikariDataSource;

import model.Company;
import persistence.DBConnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:mockContext.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class CompanyServiceTest {

	@Autowired
	private CompanyService companyService;
	
	@Test
	public void testGetCompanyByName()
	{		
		Company c = companyService.getCompany("testcompany");
		assertEquals("testcompany",c.getName());
		assertEquals(3,c.getId());
	}	
	
	@Test
	public void testGetComputerById()
	{		
		Company c = companyService.getCompany(3);
		assertEquals("testcompany",c.getName());
		assertEquals(3,c.getId());
	}
}
