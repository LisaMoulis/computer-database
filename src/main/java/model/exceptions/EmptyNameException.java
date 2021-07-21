package model.exceptions;

public class EmptyNameException extends RuntimeException {

	public EmptyNameException()
	{
		super("The name cannot be empty!");
	}

}
