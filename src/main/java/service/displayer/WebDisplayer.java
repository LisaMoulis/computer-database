package service.displayer;

import model.Computer;
import model.ComputerList;

public class WebDisplayer implements Displayer {

	private static WebDisplayer instance;
	
	private WebDisplayer()
	{}
	
	public static WebDisplayer getInstance()
	{
		if (instance == null)
		{
			instance = new WebDisplayer();
		}
		return instance;
	}
	
	@Override
	public String crud() {
		return "<script>\n"
				+ "  document.location.href=\"http://localhost/training-java/computers"
				+ "</script>";
	}

	@Override
	public String listCompanies() {
		StringBuilder str = new StringBuilder("<thead>"
        + "<tr>"
        + "<!-- Variable declarations for passing labels as parameters -->"
        + "<!-- Table header for Computer Name -->"

        + "<th class=\"editMode\" style=\"width: 60px; height: 22px;\">"
        + "   <input type=\"checkbox\" id=\"selectall\" /> "
        + "   <span style=\"vertical-align: top;\">"
        + "        -  <a href=\"#\" id=\"deleteSelected\" onclick=\"$.fn.deleteSelected();\">"
        + "               <i class=\"fa fa-trash-o fa-lg\"></i>"
        + "           </a>"
        + "   </span>"
        + "</th>"
        + "<th>Computer name</th>"
        + "<th>Introduced date</th>"
        + "<!-- Table header for Discontinued Date -->"
        + "<th>Discontinued date</th>"
        + "<!-- Table header for Company -->"
        + "<th>Company</th>"
        + "</tr>"
        + "</thead>"
        + "<!-- Browse attribute computers -->\n"
        + "                <tbody id=\"results\">");
		
		for (Computer computer : ComputerList.getInstance().getPage())
		{
			str.append("<tr>"
			+ "		<td class=\"editMode\">"
            + "	<input type=\"checkbox\" name=\"cb\" class=\"cb\" value=\"0\">"
	        + "</td>"
	        + "<td>"
	        + "    <a href=\"editComputer.html\" onclick=\"\">"+ computer.getName()+"</a>"
	        + "</td>"
	        + "<td>"+ (computer.getIntroduced() == null ?"": computer.getIntroduced()) +"</td>"
	        + "<td>"+ (computer.getDiscontinued() == null ?"": computer.getDiscontinued()) + "</td>"
	        + "<td>" + (computer.getCompany() == null ?"": computer.getCompany()) + "</td></tr>");
		}
		
		str.append("</tbody>");
		
		return str.toString();
	}

	@Override
	public String listComputers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String nextComputers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String previousComputers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String quit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String detailsComputer(String args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listComputers(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
