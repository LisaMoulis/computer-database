package model;

import java.util.*;
import java.util.Map.Entry;

import persistence.ComputerRequestHandler;

/**
 * Class ComputerLists :
 * Keeper of the computers list
 * @author Lisa
 */
public class ComputerList {

	private HashMap<Integer,Computer> computers;
	private static ComputerList instance;
	private int index = 0;
	private final int sizePage = 10;
	
	private ComputerList()
	{
		computers = ComputerRequestHandler.getAllComputers();
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
		return computers.get(id);
	}
	
	/**
	 * @param name Name of a computer
	 * @return The computer found
	 */
	public Computer getComputer(String name)
	{
		for (Entry<Integer, Computer> c : computers.entrySet())
		{
			if (c.getValue().getName().equals(name))
			{
				return c.getValue();
			}
		}
		return null;
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
	public ArrayList<Computer> getPage()
	{
		ArrayList<Computer> toDisplay = new ArrayList<Computer>();
		//Create the list of computers to display
		//StringBuilder str = new StringBuilder("	Page ").append(index+1).append("\nComputerList [computers=");
		for (int i = index*sizePage+1; i < computers.size() && i < (index+1)*sizePage+1; i++)
		{
			if (computers.get(i) != null)
			{
				toDisplay.add(computers.get(i));
				//str.append(computers.get(i).toString());
			}
			
		}
		//str.append("]");
		//return str.toString();
		return toDisplay;
	}
	
	public int getIndex()
	{
		return this.index;
	}
	
	public ArrayList<Computer> getPage(int idx)
	{
		ArrayList<Computer> toDisplay = new ArrayList<Computer>();
		//Create the list of computers to display
		//StringBuilder str = new StringBuilder("	Page ").append(idx+1).append("\nComputerList [computers=");
		for (int i = idx*sizePage+1; i < computers.size() && i < (idx+1)*sizePage+1; i++)
		{
			if (computers.get(i) != null)
			{
				toDisplay.add(computers.get(i));
				//str.append(computers.get(i).toString());
			}
			
		}
		//str.append("]");
		//return str.toString();
		return toDisplay;
	}
	
	/**
	 * @return The first page of the computers list
	 */
	public void beginPage()
	{
		index = 0;
		computers = ComputerRequestHandler.getAllComputers();
	}
	
	/**
	 * @return The next page of the computers list
	 */
	public void nextPage()
	{
		//Verify the page isn't the last one before returning the next one
		if ((index+1)*sizePage < computers.size())
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
		if (index > 0)
		{
			index--;
		}
	}

	@Override
	public String toString() {
		return "ComputerList [computers=" + computers + "]";
	}
	
}
