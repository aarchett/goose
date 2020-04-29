package goose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import goose.space.BridgeSpace;
import goose.space.GooseSpace;
import goose.space.Space;
import goose.space.WinnerSpace;

public class Board {

	private static final int PATH_LEN = 64;
	private static final int LAST_CELL = PATH_LEN-1;

	private Board() {
	}
	
	private static class BoardLazyHolder {
		static final Board board = new Board();
	}
	
	public static Board getInstance() {
		return BoardLazyHolder.board;
	}
	
	private static final List<Space> gamePath;
	private Map<Player, Space> playersPosition = new HashMap<Player, Space>();
	
	static {
		ArrayList<Space> path = new ArrayList<Space>(100);
		for(int i=0;i<PATH_LEN;i++) {
			switch (i) {
				case 0: 
					path.add(i, new Space(i, "Start"));
					break;
				case 6: 
					path.add(i, new BridgeSpace(i, 12));
					break;
				case 5:
				case 9:
				case 14:
				case 18:
				case 23:
				case 27:
					path.add(i, new GooseSpace(i));
					break;
				case LAST_CELL:
					path.add(i, new WinnerSpace(i));
					break;
				default:
					path.add(i, new Space(i));
			}
		}
		path.trimToSize();
		gamePath = Collections.unmodifiableList(path);
	}
	
	public Space[] move(Player player, int dice1, int dice2) {
		Space currentCell = playersPosition.get(player);
		ArrayList<Space> cells = new ArrayList<Space>();
		int nextLandingValue = -1;
		if (currentCell != null && !(currentCell instanceof WinnerSpace)) {
			int maybeLandingValue = currentCell.getValue() + dice1 + dice2;
			Space nextCell = gamePath.get(maybeLandingValue > LAST_CELL ? LAST_CELL: maybeLandingValue);
			cells.add(nextCell);
			nextLandingValue = nextCell.land(currentCell, dice1, dice2);
			while (nextLandingValue != maybeLandingValue) {
				maybeLandingValue = nextLandingValue;
				nextCell = gamePath.get(maybeLandingValue);
				cells.add(nextCell);
				nextLandingValue = nextCell.land(nextCell, dice1, dice2);
			}
			playersPosition.put(player, gamePath.get(nextLandingValue));
		} 
		return cells.toArray(new Space[0]);
	}
	
	public void setPlayerAtStartingPosition(Player player) {
		playersPosition.put(player, gamePath.get(0));
	}
	
	public void setPlayersAtStartingPosition(List<Player> players) {
		players.stream().forEach(this::setPlayerAtStartingPosition);
	}
	
	public void setPlayerAtPosition(Player player, int position) {
		playersPosition.put(player, gamePath.get(position));
	}
	
	public Space getCurrentPlayerPosition(Player player) {
		return playersPosition.get(player);
	}
}
