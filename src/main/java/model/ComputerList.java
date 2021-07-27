package model;

import java.util.*;

import persistence.ComputerRequestHandler;

/**
 * Class ComputerLists :
 * Keeper of the computers list
 * @author Lisa
 */
public class ComputerList {

	private HashMap<Integer,Computer> computers;
	private static ComputerList instance;
	private int index = 1;
	private final int sizePage = 10;
	
	private ComputerList()
	{
	}
	
	/**
	 * @return instance	The unique instance of the class
	 */
	public static ComputerList getInstance()
	{
		//Create the instance if it's not existing
		if (instance == null)
		{
			instance = new ComputerList();
		}
		return instance;
	}
	
	/**
	 * @param id Identifier of a computer
	 * @return The computer found
	 */
	public Computer getComputer(int id)
	{
		return ComputerRequestHandler.getComputer(id);
	}
	
	/**
	 * @param name Name of a computer
	 * @return The computer found
	 */
	public Computer getComputer(String name)
	{
		return ComputerRequestHandler.getComputer(name);
	}
	
	public List<Computer> getComputers()
	{
		computers = ComputerRequestHandler.getAllComputers();
		return new ArrayList<Computer>(computers.values());
	}
	
	public void add(Computer c)
	{
		int cindex = 1;
		if (computers != null)
		{
			while (computers.containsKey(cindex))
			{
				cindex++;
			}
		}
		c.setId(cindex);
		computers.put(cindex, c);
	}
	
	public void remove(Computer c)
	{
		computers.remove(c.getId());
	}
	
	public void remove(int id)
	{
		computers.remove(id);
	}
	
	public void remove(String name)
	{
		computers.remove(getComputer(name).getId());
	}
	
	/**
	 * @return The current page of the computers list
	 */
	public List<Computer> getPage()
	{
		//Create the list of computers to display
		//StringBuilder str = new StringBuilder("	Page ").append(index+1).append("\nComputerList [computers=");
		return ComputerRequestHandler.getPage(sizePage,(index-1)*sizePage,"","");
	}
	
	public int getNbComputers()
	{
		return ComputerRequestHandler.getNbComputers("");
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public List<Computer> getPage(int idx,int size)
	{
		//Create the list of computers to display
		return ComputerRequestHandler.getPage(size,(idx-1)*size,"","");
	}
	
	/**
	 * @return The first page of the computers list
	 */
	public void beginPage()
	{
		index = 1;
	}
	
	/**
	 * @return The next page of the computers list
	 */
	public void nextPage()
	{
		//Verify the page isn't the last one before returning the next one
		if ((index)*sizePage < computers.size())
		{
			index++;
		}
	}
	
	/**
	 * @return The previous page of the computers list
	 */
	public void previousPage()
	{
		//Verify the page isn't the first one before returning the previous one
		if (index > 1)
		{
			index--;
		}
	}

	@Override
	public String toString() {
		return "ComputerList [computers=" + computers + "]";
	}
	
}
