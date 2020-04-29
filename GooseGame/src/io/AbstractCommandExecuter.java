package io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class implementing the Template Method pattern to identify the executer for a specific user command
 * @author antonellaarchetti
 *
 */
public abstract class AbstractCommandExecuter implements CommandExecuter {

	protected Pattern pattern = null;
	protected String errorMessage = null;
	
	protected AbstractCommandExecuter(Pattern pattern, String errorMessage) {
		this.pattern = pattern;
		this.errorMessage = errorMessage;
	}
	@Override
	public Boolean apply(String command) {
		boolean handled = false;
		Matcher matcher = pattern.matcher(command);
		if (matcher.matches()) {
			handled=true;
			performAction(command, matcher);
		} else {
			throw new InvalidCommandException(command, errorMessage);
		}
		return handled;
	}
	
	protected abstract void performAction(String command, Matcher matcher);

}
