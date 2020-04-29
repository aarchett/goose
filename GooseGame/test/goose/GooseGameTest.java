package goose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

public class GooseGameTest {

	@BeforeEach
	public void init() {
		GooseGame.getInstance().setPlayers(Collections.emptySet());
	}
	
	@Test
	public void testAddPlayers() {
		GooseGame game = GooseGame.getInstance();
		boolean added = game.addPlayer("tomas");
		assertTrue(added);
		assertEquals(1,game.getPlayers().size());
		added = game.addPlayer("riccardo");
		assertTrue(added);
		assertEquals(2,game.getPlayers().size());
		added = game.addPlayer("tomas");
		assertFalse(added);
		assertEquals(2,game.getPlayers().size());
		
	}

}
