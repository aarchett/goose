package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Class containing the main() of the program. 
 * Starts and quits the Goose game and makes a tier 1 analisys of the input command given by the user.
 * To do:
 * use Spring DI to loose coupling of the classes
 * use resource bundle for i18n of output messages and errors
 * add proper comments for classes and public methods
 * add a GUI to make the game more attractive
 * can I prepare something similar for my primary school students?
 *   
 * @author antonellaarchetti
 *
 */
public class CommandHandler {
	private CommandHandler() {
	}
	
	private static class CommandLazyHolder {
		static final CommandHandler commandHandler = new CommandHandler();
	}
	
	public static CommandHandler getInstance() {
		return CommandLazyHolder.commandHandler;
	}
	
	private static final Pattern addPlayerBasicPattern = Pattern.compile("add player.*", 0);
	private static final Pattern endPattern = Pattern.compile("quit", 0);
	private static final Pattern movePattern = Pattern.compile("move .*", 0);
	private static Map<Pattern, CommandExecuter> patternToExecuter;
	static {
		patternToExecuter = new HashMap<Pattern, CommandExecuter>();
		patternToExecuter.put(addPlayerBasicPattern, AddPlayerExecuter.getInstance());
		patternToExecuter.put(endPattern, QuitGameExecuter.getInstance());
		patternToExecuter.put(movePattern, MovePlayerExecuter.getInstance());
	}
	
	public static void main(String[] args) {
		CommandHandler handler = CommandHandler.getInstance();
		handler.startGame();
	}
	
	private void endGame() {
		System.exit(0);
	}
	
	void startGame() {
		String inputLine = null;
		try (InputStreamReader ir = new InputStreamReader(System.in);
				BufferedReader in = new BufferedReader(ir)) {
			inputLine = in.readLine();
			while (inputLine != null) {
				try {
					final String input = inputLine;
					Optional<Entry<Pattern, CommandExecuter>> executerEntry = patternToExecuter.entrySet().stream().filter(entry->entry.getKey().matcher(input).matches()).findFirst();
					if (executerEntry.isPresent()) {
						executerEntry.get().getValue().apply(input);
					} else {
						throw new InvalidCommandException(inputLine, "command not recognized");
					}			
				} catch (InvalidCommandException e) {
					System.out.println(e);
				}
				inputLine = in.readLine();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static class QuitGameExecuter implements CommandExecuter {

		private QuitGameExecuter() {
		}
		
		private static class ExecuterLazyHolder {
			static final QuitGameExecuter commandHandler = new QuitGameExecuter();
		}
		
		public static QuitGameExecuter getInstance() {
			return ExecuterLazyHolder.commandHandler;
		}
		
		@Override
		public Boolean apply(String command) {
			CommandHandler.getInstance().endGame();
			return false;	
		}
	}

}
