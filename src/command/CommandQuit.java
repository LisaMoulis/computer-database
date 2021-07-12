package command;

/**
 * Class CommandQuit
 * Close the program
 */
public class CommandQuit extends Command {

	private final String name = "quit";

	@Override
	public void exec(String... args) {
		System.out.println("Bye!");
	}
}
