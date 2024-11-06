package uk.ac.kcl.inf.mdd6b.tests.etl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.inject.Inject;

import uk.ac.kcl.inf.mdd6b.generator.TurtlesGenerator;
import uk.ac.kcl.inf.mdd6b.tests.TurtlesInjectorProvider;
import uk.ac.kcl.inf.mdd6b.turtles.TurtleProgram;
import uk.ac.kcl.inf.mdd6b.turtles.TurtlesPackage;


@ExtendWith(value=InjectionExtension.class)
@InjectWith(value=TurtlesInjectorProvider.class)
public class ETLFoldTest {

	private static String readFile(Class<?> c, String fileName) throws IOException {
		StringBuffer sb = new StringBuffer();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getResourceAsStream(fileName)))) {
			String nextLine;
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine).append('\n');
			}
		}

		return sb.toString();
	}
	
	private static abstract class TurtleTransformer extends ETLRunner {

		private Resource inModel;

		TurtleTransformer (Resource inModel) {
			this.inModel = inModel;
		}

		protected List<IModel> getModels() throws Exception {
			List<IModel> result = new ArrayList<>();
			result.add(createInMemoryEmfModel(inModel, "Source", TurtlesPackage.eNS_URI));
			result.add(createInMemoryEmfModel(inModel.getResourceSet().createResource(URI.createFileURI("synthetic.turtles")),
					"Target", TurtlesPackage.eNS_URI));
			
			return result;
		}
	}
	
	private static class ETLFolder extends TurtleTransformer {
		ETLFolder (Resource inModel) {
			super(inModel);
		}
		
		protected String getSourceText() throws IOException {
			return readFile(TurtlesGenerator.class, "fold_moves.etl");
		}		
	}
	
	@Inject
	private ParseHelper<TurtleProgram> parseHelper;
	
	public void runTest(String srcModel, String expectedModel, String message) throws Exception {
		
		TurtleProgram original = parseHelper.parse(readFile(ETLFoldTest.class, srcModel));		
		Assertions.assertNotNull(original);
		Assertions.assertTrue(original.eResource().getErrors().isEmpty(), "Unexpected errors");

		ETLFolder etlRunner = new ETLFolder(original.eResource());
		
		TurtleProgram copied = (TurtleProgram) etlRunner.execute();
		
		TurtleProgram expected = parseHelper.parse(readFile(ETLFoldTest.class, expectedModel));		
		Assertions.assertNotNull(expected);
		Assertions.assertTrue(expected.eResource().getErrors().isEmpty(), "Unexpected errors");
		
		
		Assertions.assertTrue(EcoreUtil2.equals(expected, copied), message);
	}
}