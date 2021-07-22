package model;

import java.time.LocalDate;

import junit.framework.TestCase;

public class ComputerTest extends TestCase{

	private Computer computer= new Computer("test");
	
	public ComputerTest(String name)
	{
		super(name);
	}
	
	public void testCreate()
	{
		Computer test = new Computer("test");
		assertNotSame(null,test.getName());
	}
	
	public void testId()
	{
		computer.setId(1);
		assertEquals(1,computer.getId());
	}
	
	public void testIntroduced()
	{
		computer.setIntroduced(LocalDate.of(2020, 1, 2));
		assertEquals(LocalDate.of(2020, 1, 2),computer.getIntroduced());
	}
	
	public void testInvalidDiscontinued()
	{
		computer = new Computer("test");
		computer.setDiscontinued(LocalDate.of(2020, 1, 2));
		assertEquals(null,computer.getDiscontinued());
	}
	
	public void testValidDiscontinued()
	{
		computer = new Computer("test");
		computer.setIntroduced(LocalDate.of(2020, 1, 2));
		computer.setDiscontinued(LocalDate.of(2020, 2, 2));
		assertEquals(LocalDate.of(2020, 2, 2),computer.getDiscontinued());
	}
}
