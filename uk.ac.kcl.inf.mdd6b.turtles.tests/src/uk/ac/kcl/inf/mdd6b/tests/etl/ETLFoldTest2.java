package uk.ac.kcl.inf.mdd6b.tests.etl;

import org.junit.jupiter.api.Test;

public class ETLFoldTest2 extends ETLFoldTest {

	@Test
	public void testETLFoldInLoop() throws Exception {
		runTest("test2.turtles", "test2.folded.turtles", 
				"Fold transformation must fold sequences of move statements even if they occur in a LoopStatement.");
	}

}
