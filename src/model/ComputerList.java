package model;

import java.util.*;
import java.util.Map.Entry;

import persistence.ComputerRequestHandler;

/**
 * Class ComputerLists
 * Keeper of the computers list
 */
public class ComputerList {

	private HashMap<Integer,Computer> computers;
	private static ComputerList instance;
	private int index = 0;
	private final int sizePage = 50;
	
	private ComputerList()
	{
		computers = ComputerRequestHandler.getAllComputers();
	}
	
	public static ComputerList getInstance()
	{
		if (instance == null)
		{
			instance = new ComputerList();
		}
		return instance;
	}
	
	public Computer getComputer(int id)
	{
		return computers.get(id);
	}
	
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
		int index = 1;
		while (computers.containsKey(index))
		{
			index++;
		}
		c.setId(index);
		computers.put(index, c);
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
	
	private String getPage()
	{
		StringBuilder str = new StringBuilder("	Page ").append(index+1).append("\nComputerList [computers=");
		for (int i = index*sizePage+1; i < computers.size() && i < (index+1)*sizePage+1; i++)
		{
			if (computers.get(i) != null)
			{
				str.append(computers.get(i).toString());
			}
			
		}
		str.append("]");
		return str.toString();
	}
	
	
	public String beginPage()
	{
		index = 0;
		return getPage();
	}
	
	public String nextPage()
	{
		if ((index+1)*sizePage < computers.size())
		{
			index++;
		}
		return getPage();
	}
	
	public String previousPage()
	{
		if (index > 0)
		{
			index--;
		}
		return getPage();
	}

	@Override
	public String toString() {
		return "ComputerList [computers=" + computers + "]";
	}
	
}
