package uk.ac.kcl.inf.mdd6b.tests.etl;

import org.junit.jupiter.api.Test;

public class ETLFoldTest4 extends ETLFoldTest {


	@Test
	public void testETLFoldAtEnd() throws Exception {
		runTest("test3.turtles", "test3.folded.turtles", 
				"Fold transformation must fold sequences of move statements even at the end of a block of statements.");
	}

}
