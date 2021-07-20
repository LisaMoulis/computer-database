package service;

import java.time.LocalDate;

import junit.framework.*;
import model.Computer;

public class ComputerBuilderTest extends TestCase {

	public void TestCreateEmpty() throws  Exception
	{
		ComputerBuilder builder = new ComputerBuilder();
		assertEquals(null,builder.build());
	}
	
	public void TestCreateMinimum() throws Exception
	{
		ComputerBuilder builder = new ComputerBuilder();
		builder.setName("test");
		Computer testc = new Computer("test");
		assertEquals(testc,builder.build());
	}
	
	public void TestCreateFull() throws Exception
	{
		ComputerBuilder builder = new ComputerBuilder();
		builder.setId(1).setName("test").setIntroduced(LocalDate.of(2020, 1, 1)).setDiscontinued(LocalDate.of(2020,2,2)).setCompany("One company");
		Computer testc = new Computer(1,"test",LocalDate.of(2020, 1, 1),LocalDate.of(2020, 2, 2),"One company");
		assertEquals(testc,builder.build());
	}
	
}
