package io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import goose.Board;
import goose.GooseGame;
import goose.Player;

public class MovePlayerTest {

	private static final Player riccardo = new Player("riccardo");
	
	
	@BeforeEach
	public void init() {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(riccardo);
		GooseGame.getInstance().setPlayers(players);
		Board.getInstance().setPlayersAtStartingPosition(players);
	}
	
	@Test
	public void testMoveFromStartToBridge() {
		MovePlayerExecuter.getInstance().apply("move riccardo 4, 2");
		assertEquals(12,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveFromStartToGoose() {
		MovePlayerExecuter.getInstance().apply("move riccardo 4, 1");
		assertEquals(10,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveFrom10ToMultipleGoose() {
		Board.getInstance().setPlayerAtPosition(riccardo, 10);
		MovePlayerExecuter.getInstance().apply("move riccardo 2, 2");
		assertEquals(22,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveFromStartToNineToManyGooses() {
		MovePlayerExecuter.getInstance().apply("move riccardo 4,5");
		assertEquals(36,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveFromStartToSeven() {
		MovePlayerExecuter.getInstance().apply("move riccardo 4,3");
		assertEquals(7,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveFromStartToRandom() {
		MovePlayerExecuter.getInstance().apply("move riccardo");
		assertTrue(0<Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveInvalidDiceValue() {
		Assertions.assertThrows(InvalidCommandException.class, () -> MovePlayerExecuter.getInstance().apply("move riccardo 9, 9"));
	}
	
	@Test
	public void testMoveOver9DiceValue() {
		Assertions.assertThrows(InvalidCommandException.class, () -> MovePlayerExecuter.getInstance().apply("move riccardo 10, 3"));
	}
	
	@Test
	public void testMoveInvalidDiceNumber() {
		Assertions.assertThrows(InvalidCommandException.class, () -> MovePlayerExecuter.getInstance().apply("move riccardo 4, 4, 4"));
	}
	
	@Test
	public void testMoveInvalidPlayer() {
		Assertions.assertThrows(InvalidCommandException.class, () -> MovePlayerExecuter.getInstance().apply("move tomas 4, 4"));
	}

	@Test
	public void testMoveToEnd() {
		Board.getInstance().setPlayerAtPosition(riccardo, 54);
		MovePlayerExecuter.getInstance().apply("move riccardo 4,5");
		assertEquals(63,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveToEndAndBounce() {
		Board.getInstance().setPlayerAtPosition(riccardo, 56);
		MovePlayerExecuter.getInstance().apply("move riccardo 4,5");
		assertEquals(61,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
	
	@Test
	public void testMoveFromEndToNowhere() {
		Board.getInstance().setPlayerAtPosition(riccardo, 63);
		MovePlayerExecuter.getInstance().apply("move riccardo 4,5");
		assertEquals(63,Board.getInstance().getCurrentPlayerPosition(riccardo).getValue());
	}
}
