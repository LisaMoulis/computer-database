package command;

public interface Command {

	public static final String name = " ";
	public void exec(String... args);
	public String getName();
}
