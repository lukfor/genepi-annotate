package genepi.annotate;

import java.lang.reflect.InvocationTargetException;

import genepi.base.Toolbox;

/**
 * Hello world!
 *
 */
public class App extends Toolbox {

	public App(String command, String[] args) {
		super(command, args);
	}

	public static void main(String[] args) {

		App tools = new App("jar genepi-annotate.jar", args);

		tools.addTool("annotate", AnnotateTool.class);

		try {
			tools.start();
		} catch (InstantiationException | IllegalAccessException | SecurityException | NoSuchMethodException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}
