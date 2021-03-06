package ReactorEE.test;


import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import ReactorEE.model.*;

public class HighScoreTest {

	private HighScore highScore;

	@Before
	public void setUp() {
		this.highScore = new HighScore("Bob", 1233);
	}
	
	@Test
	public void testGetName() {
		
		assertEquals("Result", "Bob", this.highScore.getName());
		
	}
	
	@Test
	public void testGetHighScore() {
		
		assertEquals("Result", 1233, this.highScore.getHighScore());
		
	}
	
	@Test
	public void testCompareTo() {
		
		HighScore anotherHighScore = new HighScore("George", 540);
		
		assertEquals("Result", (1233 - 540), this.highScore.compareTo(anotherHighScore));
		
	}

}
