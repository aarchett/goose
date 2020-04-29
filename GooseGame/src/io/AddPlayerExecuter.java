package io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import goose.GooseGame;


public class AddPlayerExecuter extends AbstractCommandExecuter{

	private AddPlayerExecuter() {
		super(addPlayerFullPattern, "player name invalid");
	}

	private static class ExecuterLazyHolder {
		static final AddPlayerExecuter commandHandler = new AddPlayerExecuter();
	}

	public static AddPlayerExecuter getInstance() {
		return ExecuterLazyHolder.commandHandler;
	}

	private static final Pattern addPlayerFullPattern = Pattern.compile("add player (\\w+)", 0);

	@Override
	protected void performAction(String command, Matcher matcher) {
		String playerName = matcher.group(1).trim();
		if (!"".equals(playerName)) {
			GooseGame gooseGame = GooseGame.getInstance();
			boolean added = gooseGame.addPlayer(playerName);
			System.out.println(added ? "players: " + gooseGame.getPlayers().stream().map(Object::toString).collect(Collectors.joining(", ")) : playerName + ": already existing player");
		} else {
			throw new InvalidCommandException(command, "player name invalid");
		}
	}
}