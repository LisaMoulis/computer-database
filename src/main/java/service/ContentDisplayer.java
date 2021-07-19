package service;

public class ContentDisplayer {

	private static ContentDisplayer instance;
	private static boolean isCLI = true;
	
	private ContentDisplayer()
	{}
	
	public static ContentDisplayer getInstance()
	{
		if (instance == null)
		{
			instance = new ContentDisplayer();
		}
		return instance;
	}
	
	public static void CLIMode()
	{
		isCLI = true;
	}
}
