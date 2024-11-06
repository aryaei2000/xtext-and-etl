package uk.ac.kcl.inf.mdd6b.tests.etl;

import org.junit.jupiter.api.Test;

public class ETLFoldTest3 extends ETLFoldTest {

	
	@Test
	public void testETLFoldAtStart() throws Exception {
		runTest("test4.turtles", "test4.folded.turtles", 
				"Fold transformation must fold sequences of move statements even at the start of a block of statements.");
	}
}
