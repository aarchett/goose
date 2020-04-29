package io;

@SuppressWarnings("serial")
public class InvalidCommandException extends RuntimeException {

	@Override
	public String toString() {
		return "Invalid command [command=" + command + "]: " + getMessage();
	}

	private String command;
	
	
	public String getCommand() {
		return command;
	}


	public InvalidCommandException(String command, String message) {
		super(message);
		this.command = command;
	}
}
