package uk.ac.kcl.inf.mdd6b.tests.etl;

import org.junit.jupiter.api.Test;

public class ETLFoldTest1 extends ETLFoldTest {
	@Test
	public void testBasicETLFold() throws Exception {
		runTest("test1.turtles", "test1.folded.turtles", 
				"Fold transformation must fold sequences of move statements.");
	}
}
