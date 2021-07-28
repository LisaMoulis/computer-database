package service;

import java.time.LocalDate;

import builder.ComputerBuilder;
import junit.framework.*;
import model.Computer;

public class ComputerBuilderTest extends TestCase {

	public void testCreateEmpty() throws  Exception
	{
		ComputerBuilder builder = new ComputerBuilder();
		assertEquals(null,builder.build());
	}
	
	public void testCreateMinimum() throws Exception
	{
		ComputerBuilder builder = new ComputerBuilder();
		builder.setName("test");
		Computer testc = new Computer("test");
		assertEquals(testc,builder.build());
	}
	
	public void testCreateFull() throws Exception
	{
		ComputerBuilder builder = new ComputerBuilder();
		builder.setId(1).setName("test").setIntroduced(LocalDate.of(2020, 1, 1)).setDiscontinued(LocalDate.of(2020,2,2)).setCompany("One company");
		Computer testc = new Computer(1,"test",LocalDate.of(2020, 1, 1),LocalDate.of(2020, 2, 2),"One company");
		assertEquals(testc,builder.build());
	}
	
}
