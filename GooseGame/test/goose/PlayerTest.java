package goose;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PlayerTest {

	@Test
	public void testEqualsObject() {
		Player antonella1 = new Player("antonella");
		Player antonella2 = new Player("antonella");
		assertEquals(antonella1, antonella2);
	}

	@Test
	public void testNotEqualsObject() {
		Player antonella1 = new Player("antonella");
		Player antonella2 = new Player("antonellA");
		assertNotEquals(antonella1, antonella2);
	}
}
