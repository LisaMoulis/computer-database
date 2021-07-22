package service;

import model.Computer;
import model.exceptions.*;

public class Validator {

	public static void validate(Computer computer) throws RuntimeException
	{
		if (computer.getName() == null || computer.getName().equals(""))
		{
			throw new EmptyNameException();
		}
		
		if (computer.getDiscontinued() != null)
		{
			if (computer.getIntroduced() == null)
			{
				throw new EmptyIntroducedException();
			}
			else if (computer.getIntroduced().isAfter(computer.getDiscontinued()))
			{
				throw new IncorrectDateOrderException();
			}
		}
	}
}
