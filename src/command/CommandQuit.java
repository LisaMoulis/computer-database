package command;

public class CommandQuit implements Command {

	private static final String name = "quit";

	@Override
	public void exec(String... args) {
		System.out.println("Bye!");
	}

	@Override
	public String getName() {
		return name;
	}
}
