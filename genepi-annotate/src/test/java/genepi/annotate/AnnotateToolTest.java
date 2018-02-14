package genepi.annotate;

import java.util.Iterator;
import java.util.Map;

import genepi.annotate.util.MapLocus;
import genepi.annotate.util.MapLocusItem;
import genepi.annotate.util.SequenceUtil;
import htsjdk.samtools.util.IntervalTree.Node;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AnnotateToolTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AnnotateToolTest(String testName) {
		super(testName);
	}

	public void testOffsetExon421() throws Exception {

		int position = 621;
		position = position - 1;

		String maplocusFilename = "MapLocusLPA (FOR LONG PCR) - v3.txt";
		String reference = "kiv2_6.fasta";
		String codonTableFilename = "codon_table.csv";

		Map<String, String> codonTable = SequenceUtil.loadCodonTable(codonTableFilename);
		String refSequence = SequenceUtil.readReferenceSequence(reference);
		MapLocus maplocus = new MapLocus(maplocusFilename);

		Iterator<Node<MapLocusItem>> result = maplocus.findByPosition(position);

		assertTrue(result.hasNext());

		MapLocusItem item = result.next().getValue();

		int offset = Integer.parseInt(item.getReadingFrame().trim());
		assertEquals(3, offset);

		// get ref tripel
		String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart() - 1, offset - 1, position);

		assertEquals("His", codonTable.get(tripelRef));

	}

	public void testOffsetExon422() throws Exception {

		int position = 4912;
		position = position - 1;

		String maplocusFilename = "MapLocusLPA (FOR LONG PCR) - v3.txt";
		String reference = "kiv2_6.fasta";
		String codonTableFilename = "codon_table.csv";

		Map<String, String> codonTable = SequenceUtil.loadCodonTable(codonTableFilename);
		String refSequence = SequenceUtil.readReferenceSequence(reference);
		MapLocus maplocus = new MapLocus(maplocusFilename);

		Iterator<Node<MapLocusItem>> result = maplocus.findByPosition(position);

		assertTrue(result.hasNext());

		MapLocusItem item = result.next().getValue();

		int offset = Integer.parseInt(item.getReadingFrame().trim());
		assertEquals(2, offset);

		// get ref tripel
		String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart() - 1, offset - 1, position);

		assertEquals("Ala", codonTable.get(tripelRef));

	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AnnotateToolTest.class);
	}

}
