package mapper;

import dto.ComputerDTO;
import junit.framework.TestCase;

public class ComputerDTOMapperTest extends TestCase {

	public void testToComputer()
	{
		ComputerDTO dto = new ComputerDTO(1,"test","2021-01-01","2021-02-02","testcompany",3);
	}
	
	public void testToDTO()
	{
		
	}
}
