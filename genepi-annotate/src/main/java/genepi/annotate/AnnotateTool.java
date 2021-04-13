package genepi.annotate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import genepi.annotate.util.MapLocus;
import genepi.annotate.util.MapLocusItem;
import genepi.annotate.util.SequenceUtil;
import genepi.base.Tool;
import genepi.io.table.reader.CsvTableReader;
import genepi.io.table.writer.CsvTableWriter;
import htsjdk.samtools.util.IntervalTree.Node;

public class AnnotateTool extends Tool {

	private String refSequence = "";

	public static String CODON_TABLE_FILENAME = "codon_table.csv";

	public AnnotateTool(String[] args) {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createParameters() {
		addParameter("reference", "Reference fasta filename");
		addParameter("input", "Input csv file");
		addParameter("position", "Position column in input file");
		addParameter("mutation", "Mutation column in input file");
		addParameter("maplocus", "MapLocus filename");
		addParameter("output", "Output csv filename");
		addParameter("columnwt", "New column for wildtype aas in output file");
		addParameter("columnmut", "New column for wt aas in output file");
	}

	@Override
	public void init() {

	}

	@Override
	public int run() {

		String input = getValue("input").toString();
		String reference = getValue("reference").toString();
		String positionCol = getValue("position").toString();
		String mutationCol = getValue("mutation").toString();
		String maplocusFilename = getValue("maplocus").toString();
		String output = getValue("output").toString();
		String columnWd = getValue("columnwt").toString();
		String columnMut = getValue("columnmut").toString();

		try {
			System.out.println("Reading reference file " + reference + "... ");
			refSequence = SequenceUtil.readReferenceSequence(reference);
			System.out.println("Reference length: " + refSequence.length() + "\n");
		} catch (Exception e) {
			System.out.println("Error reading file: " + e.getMessage());
			return -1;
		}

		System.out.println("Reading codon table " + CODON_TABLE_FILENAME + "...");
		Map<String, String> codonTable = SequenceUtil.loadCodonTable(CODON_TABLE_FILENAME);

		System.out.println("Reading maplocus file " + maplocusFilename + "...");
		MapLocus maplocus;
		try {
			maplocus = new MapLocus(maplocusFilename);
		} catch (IOException e) {
			System.out.println("Error loading MapLocus from file '" + maplocusFilename + "':");
			e.printStackTrace();
			return -1;
		}

		CsvTableReader reader = new CsvTableReader(input, '\t');
		CsvTableWriter writer = new CsvTableWriter(output, '\t');

		String[] readerCols = reader.getColumns();

		String[] writerCols = Arrays.copyOf(readerCols, readerCols.length + 3);
		writerCols[readerCols.length] = columnWd;
		writerCols[readerCols.length + 1] = columnMut;
		// for testing only!
		writerCols[readerCols.length + 2] = "mylocus";

		writer.setColumns(writerCols);

		System.out.println("Reading file " + input + " [" + readerCols.length + " columns]... ");
		System.out.println("Writing output " + input + " [" + writerCols.length + " columns]... ");

		int write = 0;
		while (reader.next()) {
			for (String col : readerCols) {
				writer.setString(col, reader.getString(col));
			}

			// load variant infos
			int position = reader.getInteger(positionCol) - 1;
			String mutation = reader.getString(mutationCol);

			Iterator<Node<MapLocusItem>> result = maplocus.findByPosition(position);

			int offset = 0;
			boolean exome = true;
			String shorthand = "????????";
			MapLocusItem item = null;
			if (result.hasNext()) {
				item = result.next().getValue();
				exome = item.getCoding().equals("coding");
				shorthand = item.getShorthand();
				if (!item.getReadingFrame().trim().isEmpty()) {
					offset = Integer.parseInt(item.getReadingFrame().trim());
				}
			} else {
				exome = false;
			}

			if (exome) {

				// get ref tripel
				String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart() - 1, offset - 1, position);
				if (!tripelRef.equals("-")) {
					// get changed tripel
					String tripelMutation = SequenceUtil.getTripelWithMutation(refSequence, item.getStart() - 1,
							offset - 1, position, mutation);

					// write aas to outputfile
					writer.setString(columnWd, codonTable.get(tripelRef));
					writer.setString(columnMut, codonTable.get(tripelMutation));
					writer.setString("mylocus", shorthand);
				} else {
					writer.setString(columnWd, "-");
					writer.setString(columnMut, "-");
					writer.setString("mylocus", shorthand);
				}

			} else {
				// no region found in maplocus file
				writer.setString(columnWd, "-");
				writer.setString(columnMut, "-");
				writer.setString("mylocus", shorthand);
			}

			writer.next();
			write++;
		}
		reader.close();
		writer.close();

		System.out.println("Written " + write + " lines. Done.");

		return 0;
	}

	public static void main(String[] args) {

		AnnotateTool stat = new AnnotateTool(
				new String[] { "--input", "test-input.txt", "--reference", "kiv2_6.fasta", "--position", "POS",
						"--mutation", "MINOR-BASE-FWD", "--maplocus", "MapLocusLPA (FOR LONG PCR) - v3.txt", "--output",
						"text-output.txt", "--columnwt", "wt aas", "--columnmut", "mut aas" });
		stat.start();

	}

}
