package genepi.annotate;

import java.lang.reflect.InvocationTargetException;

import genepi.base.Toolbox;

public class App extends Toolbox {

	public App(String command, String[] args) {
		super(command, args);
	}

	public static final String APP = "genepi-annotate";

	public static final String VERSION = "0.1.0";

	public static final String URL = "https://github.com/lukfor/genepi-annotate";

	public static final String COPYRIGHT = "(c) 2016-2024 Lukas Forer";

	public static String[] ARGS = new String[0];

	public static void main(String[] args) {

		System.err.println();
		System.err.println(APP + " " + VERSION);
		if (URL != null && !URL.isEmpty()) {
			System.err.println(URL);
		}
		if (COPYRIGHT != null && !COPYRIGHT.isEmpty()) {
			System.err.println(COPYRIGHT);
		}
		System.err.println();

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
