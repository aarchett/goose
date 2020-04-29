package io;

import java.util.Arrays;
import java.util.Optional;
import java.util.PrimitiveIterator.OfInt;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import goose.Board;
import goose.GooseGame;
import goose.Player;
import goose.space.BridgeSpace;
import goose.space.GooseSpace;
import goose.space.Space;
import goose.space.WinnerSpace;


public class MovePlayerExecuter extends AbstractCommandExecuter{

	private MovePlayerExecuter() {
		super(movePlayerGenericPattern, "player move invalid");
	}

	private static class ExecuterLazyHolder {
		static final MovePlayerExecuter commandHandler = new MovePlayerExecuter();
	}

	public static MovePlayerExecuter getInstance() {
		return ExecuterLazyHolder.commandHandler;
	}

	private static final Pattern movePlayerWithDicesPattern = Pattern.compile("move (\\w+) (1|2|3|4|5|6), *(1|2|3|4|5|6)", 0);
	private static final Pattern movePlayerGenericPattern = Pattern.compile("move (\\w+).*", 0);
	private static final Pattern movePlayerNoDicesPattern = Pattern.compile("move (\\w+) *", 0);

	@Override
	protected void performAction(String command, Matcher matcher) {
		Matcher moveWithDicesMatcher = movePlayerWithDicesPattern.matcher(command);
		Matcher moveNoDicesMatcher = movePlayerNoDicesPattern.matcher(command);
		int dice1 = 0, dice2 = 0, i = 0;
		String playerName = null;
		if (moveWithDicesMatcher.matches()) {
			playerName = moveWithDicesMatcher.group(++i).trim();
			dice1 = Integer.parseInt(moveWithDicesMatcher.group(++i));
			dice2 = Integer.parseInt(moveWithDicesMatcher.group(++i));
		} else if (moveNoDicesMatcher.matches()){
			playerName = moveNoDicesMatcher.group(++i).trim();
			OfInt dices = new Random().ints(2, 1, 7).iterator();
			dice1 = dices.next();
			dice2 = dices.next();
		} else {
			throw new InvalidCommandException(command, "invalid command");
		}
		move(playerName, dice1, dice2, command);
	}

	private void move(String playerName, int dice1, int dice2, String command) {
		GooseGame gooseGame = GooseGame.getInstance();
		Player player = gooseGame.getPlayer(playerName);
		if (player != null) {
			Board instance = Board.getInstance();
			Space startingCell = instance.getCurrentPlayerPosition(player);
			Space[] destinationCells = instance.move(player, dice1, dice2);
			String output = getOutput(dice1, dice2, player, startingCell, destinationCells);
			System.out.println(output);
		} else {
			throw new InvalidCommandException(command, "player is not playing");
		}
	}

	private String getOutput(int dice1, int dice2, Player player, Space startingCell, Space[] destinationCells) {
		StringBuffer output = new StringBuffer();
		int spacesLen = destinationCells.length;
		if (spacesLen == 0 ) {
			output.append(player).append(" rolls ").append(dice1).append(", ").append(dice2).append(". However ").append(player).append(" cannot moves from ")
			.append(startingCell).append(". ").append(player).append(" remains at ").append(startingCell);
		} else {
			Space destinationCell = destinationCells[spacesLen-1];
			if (spacesLen > 1) {
				Optional<Space> winnerCell = Arrays.stream(destinationCells).filter(cell->(cell instanceof WinnerSpace)).findAny();
				Optional<Space> bridgeCell = Arrays.stream(destinationCells).filter(cell->(cell instanceof BridgeSpace)).findAny();
				Optional<Space> gooseCell = Arrays.stream(destinationCells).filter(cell->(cell instanceof GooseSpace)).findAny();
				if (winnerCell.isPresent()) {
					// "Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61"
					output.append(player + " rolls " + dice1 + ", " + dice2 + ". " + player + " moves from " 
							+ startingCell + " to " + winnerCell.get() + ". " + player + " bounces! " + player + " returns to " + destinationCell);
				} else if (bridgeCell.isPresent()){
					int i = 0;
					// Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12
					output.append(player + " rolls " + dice1 + ", " + dice2 + ". " + player + " moves from " 
							+ startingCell + " to " + destinationCells[i++] + ". " + player + " jumps to " + destinationCells[i++]);
				} else if (gooseCell.isPresent()) {
					int i = 0;
					// Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22
					output.append(player).append(" rolls ").append(dice1).append(", ").append(dice2).append(". ").append(player).append(" moves from ")
						.append(startingCell).append(" to ").append(destinationCells[i++]);
					while (i<spacesLen) {
						output.append(". ").append(player).append(" moves again and goes to ").append(destinationCells[i++]);
					}
				}		
			} else if (spacesLen == 1) {
				// Pippo rolls 4, 2. Pippo moves from Start to 6
				output.append(player + " rolls " + dice1 + ", " + dice2 + ". " + player + " moves from " 
				+ startingCell + " to " + destinationCell + ((destinationCell instanceof WinnerSpace) ? ". " + player + " Wins!!" : ""));
				// Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!
			}
		}
		return output.toString();
	}
}