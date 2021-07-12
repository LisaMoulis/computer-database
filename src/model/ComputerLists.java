package model;

import java.util.*;

/**
 * Class ComputerLists
 * Keeper of the computers and companies lists
 */
public class ComputerLists {

	private HashMap<Integer,Computer> computers;
	private final HashMap<Integer,String> companies;
	
	public ComputerLists(HashMap<Integer,Computer> computers,HashMap<Integer,String> companies)
	{
		this.computers = computers;
		this.companies = companies;
	}
	
	
	
}
