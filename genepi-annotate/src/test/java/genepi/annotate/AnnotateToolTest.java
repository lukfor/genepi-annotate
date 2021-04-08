package genepi.annotate;

import java.util.Iterator;
import java.util.Map;

import genepi.annotate.util.MapLocus;
import genepi.annotate.util.MapLocusGFF3;
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
	 * @param testName name of the test case
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
		String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart()-1, offset - 1, position);

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
		String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart() -1, offset - 1, position);

		assertEquals("Ala", codonTable.get(tripelRef));

	}

	public void testSARSCOV2() throws Exception {

		// S:N501Y - A23063T
		// S:N501S - A23064C

		int position = 23063;
		String variant = "T";

		String maplocusFilename = "test-data/SARSCOV2.gff";
		String reference = "test-data/SARSCOV2.fasta";
		String codonTableFilename = "test-data/SARSCOV2.aac.txt";

		Map<String, String> codonTable = SequenceUtil.loadCodonTableLong(codonTableFilename);
		String refSequence = SequenceUtil.readReferenceSequence(reference);
		MapLocusGFF3 maplocus = new MapLocusGFF3(maplocusFilename);

		Iterator<Node<MapLocusItem>> result = maplocus.findByPosition(position);

		assertTrue(result.hasNext());

		MapLocusItem item = result.next().getValue();

		int offset = Integer.parseInt(item.getReadingFrame().trim());

		assertEquals(0, offset);

		// get ref tripel

		// 23063T

		String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart()-1, offset, position-1);
		String tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart()-1, offset, position-1, variant);
		int posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());

		assertEquals("N501Y", codonTable.get(tripelRef) + posAAC + codonTable.get(tripelMut));

		// 23064C
		position++;

		tripelRef = SequenceUtil.getTripel(refSequence, item.getStart()-1, offset, position-1);
		tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart()-1, offset, position-1, "C");
		posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());
		assertEquals("N501T", codonTable.get(tripelRef) + posAAC + codonTable.get(tripelMut));

		// 23065C
		position++;

		tripelRef = SequenceUtil.getTripel(refSequence, item.getStart()-1, offset, position-1);
		tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart()-1, offset, position-1, "A");
		posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());
		assertEquals("N501K", codonTable.get(tripelRef) + posAAC + codonTable.get(tripelMut));

		// 23066A

		position++;
		tripelRef = SequenceUtil.getTripel(refSequence, item.getStart()-1, offset, position-1);
		tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart()-1, offset, position-1, "A");
		posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());
		assertEquals("G502S", codonTable.get(tripelRef) + posAAC + codonTable.get(tripelMut));

		// 21562 - not in a gene

		position = 21562; // "intron"
		result = maplocus.findByPosition(position);
		assertFalse(result.hasNext());

		tripelRef = SequenceUtil.getTripel(refSequence, item.getStart(), offset, position);
		tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart(), offset, position, "A");
		posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());
		assertEquals(null, codonTable.get(tripelRef));

	}

	public void testmtDNA() throws Exception {

		String maplocusFilename = "test-data/RCRS.gff";
		String reference = "test-data/RCRS.fasta";
		String codonTableFilename = "test-data/RCRS.aac.txt";

		Map<String, String> codonTable = SequenceUtil.loadCodonTableLong(codonTableFilename);
		String refSequence = SequenceUtil.readReferenceSequence(reference);
		MapLocusGFF3 maplocus = new MapLocusGFF3(maplocusFilename);

		// 13831A
		int position = 13831;
		String variant = "A";

		Iterator<Node<MapLocusItem>> result = maplocus.findByPosition(position);

		assertTrue(result.hasNext());

		MapLocusItem item = result.next().getValue();

		int offset = Integer.parseInt(item.getReadingFrame().trim());

		assertEquals(0, offset);

		String tripelRef = SequenceUtil.getTripel(refSequence, item.getStart() -1 , offset, position -1);
		String tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart() -1 , offset, position -1, variant);
		int posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());

		assertEquals("L499M", codonTable.get(tripelRef) + posAAC + codonTable.get(tripelMut));

		// 14225T in ND6 -> Reverse strand
		position = 14225;
		variant = "T";

		result = maplocus.findByPosition(position);

		assertTrue(result.hasNext());

		item = result.next().getValue();

		System.out.println(item.getShorthand() + " " + item.getStart() + " " + item.getTranslated());

		offset = Integer.parseInt(item.getReadingFrame().trim());

		assertEquals(0, offset);

		tripelRef = SequenceUtil.getTripel(refSequence, item.getStart(), offset, position);
		tripelMut = SequenceUtil.getTripelWithMutation(refSequence, item.getStart(), offset, position, variant);
		posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());

		assertEquals(150, posAAC);
		// TODO ADAPT FOR STRAND check
		// assertEquals("R150H", codonTable.get(tripelRef) + posAAC +
		// codonTable.get(tripelMut));

	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AnnotateToolTest.class);
	}

}
