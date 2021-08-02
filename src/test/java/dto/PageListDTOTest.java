package dto;

import org.mockito.Spy;

import junit.framework.TestCase;

public class PageListDTOTest extends TestCase {

	@Spy
	private PageListDTO page = new PageListDTO();
	
	public void testDefault()
	{
		page = new PageListDTO();
		assertEquals(1,page.getPage());
		assertEquals(10,page.getSize());
		assertEquals("",page.getSearch());
		assertEquals("computer.name",page.getOrder());
	}
	
	public void testPageDownLimit()
	{
		page.setPage(0);;
		assertEquals(1,page.getPage());
	}
	
	public void testPageUpperLimit()
	{
		page.setPage(page.getNbPages()+1);
		assertEquals(page.getNbPages(),page.getPage());
	}
	
	public void testSearch()
	{
		page.setSearch("something");
		assertEquals("something",page.getSearch());
	}
	
	public void testSize()
	{
		page.setSize(20);
		assertEquals(20,page.getSize());
	}
	
	public void testOrder()
	{
		page.setOrder("something else");
		assertEquals("something else",page.getOrder());
	}
	
	public void testPage()
	{
		page.setPage(1);
		assertEquals(1,page.getPage());
	}
}
