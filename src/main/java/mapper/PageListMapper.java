package mapper;

import dto.PageListDTO;
import model.ComputerList;

public class PageListMapper {

	public static ComputerList mapToComputerList(PageListDTO dto)
	{
		ComputerList list = new ComputerList(dto.getPage(),dto.getSize());
		
		return list;
	}
}
