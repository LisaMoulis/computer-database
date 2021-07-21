package model.exceptions;

public class EmptyIntroducedException extends RuntimeException {

	public EmptyIntroducedException()
	{
		super("Enter an introduced date if you enter a discontinued one!");
	}

}
