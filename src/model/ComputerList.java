package model;

import java.util.*;

/**
 * Class ComputerLists
 * Keeper of the computers list
 */
public class ComputerList {

	private HashMap<Integer,Computer> computers;
	
	public ComputerList(HashMap<Integer,Computer> computers,HashMap<Integer,String> companies)
	{
		this.computers = computers;
	}
	
	public Computer getComputer(int id)
	{
		return computers.get(id);
	}
	
}
