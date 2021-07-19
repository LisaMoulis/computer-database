package service.displayer;

import java.util.HashMap;
import model.*;

public interface Displayer {

	public String crud();
	
	public String listCompanies();
	
	public String listComputers();
	
	public String detailsComputer(String args);
	
	public String nextComputers();
	
	public String previousComputers();

	public String quit();

	public String listComputers(int index);
}
