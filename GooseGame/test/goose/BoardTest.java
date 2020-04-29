package goose;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class BoardTest {
	
	private static final Player tomas = new Player("tomas");

	@BeforeEach
	public void init() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(tomas);
		GooseGame.getInstance().setPlayers(players);
		Board.getInstance().setPlayersAtStartingPosition(players);
	}
	
	@Test
	public void testMove() {
		Board instance = Board.getInstance();
		instance.move(tomas, 1, 3);
		assertEquals(4,instance.getCurrentPlayerPosition(tomas).getValue());
		instance.move(tomas, 3, 3);
		assertEquals(10,instance.getCurrentPlayerPosition(tomas).getValue());
	}

	@Test
	public void testSetPlayerAtStartingPosition() {
		Board instance = Board.getInstance();
		instance.setPlayerAtStartingPosition(tomas);
		assertEquals(0,instance.getCurrentPlayerPosition(tomas).getValue());
	}


}
