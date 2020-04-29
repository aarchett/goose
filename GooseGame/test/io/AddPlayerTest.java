package io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import goose.GooseGame;

public class AddPlayerTest {

	private static final AddPlayerExecuter addPlayer = AddPlayerExecuter.getInstance();
	
	@BeforeEach
	public void init() {
		GooseGame.getInstance().removeAllPlayers();
	}
	
	@Test
	public void testAddNewPlayers() {
		boolean added = addPlayer.apply("add player tomas");
		assertTrue(added);
		added = addPlayer.apply("add player ricky");
		assertTrue(added);
		added = addPlayer.apply("add player matteo");
		assertTrue(added);
		added = addPlayer.apply("add player antonella");
		assertTrue(added);
	}
	
	@Test
	public void testAddAlreadyExistingPlayer() {
		boolean added = addPlayer.apply("add player tomas");
		assertTrue(added);
		added = addPlayer.apply("add player ricky");
		assertTrue(added);
		added = addPlayer.apply("add player matteo");
		assertTrue(added);
		added = addPlayer.apply("add player ricky");
		assertTrue(added);
	}

	@Test
	public void testAddNoPlayer() {
		Assertions.assertThrows(InvalidCommandException.class, () -> addPlayer.apply("add player "));
		GooseGame game = GooseGame.getInstance();
		assertTrue(game.getPlayers().isEmpty());
	}
	
	@Test
	public void testAddEmptyPlayer() {
		Assertions.assertThrows(InvalidCommandException.class, () -> addPlayer.apply("add player                            "));
		GooseGame game = GooseGame.getInstance();
		assertTrue(game.getPlayers().isEmpty());
	}
	
	@Test
	public void testAddManyPlayersWithComma() {
		Assertions.assertThrows(InvalidCommandException.class, () -> addPlayer.apply("add player tomas, antonella"));
		GooseGame game = GooseGame.getInstance();
		assertTrue(game.getPlayers().isEmpty());
	}
	
	@Test
	public void testAddManyPlayersWithoutComma() {
		Assertions.assertThrows(InvalidCommandException.class, () -> addPlayer.apply("add player tomas antonella"));
		GooseGame game = GooseGame.getInstance();
		assertTrue(game.getPlayers().isEmpty());
	}
}
