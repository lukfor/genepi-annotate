package genepi.annotate.util;

import java.util.Map;

import genepi.annotate.AnnotateTool;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SequenceUtilTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public SequenceUtilTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(SequenceUtilTest.class);
	}

	public void testGetTrippel() {

		String sequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		assertEquals("ABC", SequenceUtil.getTripel(sequence, 0, 0));
		assertEquals("ABC", SequenceUtil.getTripel(sequence, 0, 1));
		assertEquals("ABC", SequenceUtil.getTripel(sequence, 0, 2));
		assertEquals("DEF", SequenceUtil.getTripel(sequence, 0, 3));
		assertEquals("DEF", SequenceUtil.getTripel(sequence, 0, 4));
		assertEquals("DEF", SequenceUtil.getTripel(sequence, 0, 5));
		assertEquals("GHI", SequenceUtil.getTripel(sequence, 0, 6));

		assertEquals("-", SequenceUtil.getTripel(sequence, 1, 0));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 1, 1));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 1, 2));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 1, 3));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 1, 4));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 1, 5));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 1, 6));
		assertEquals("HIJ", SequenceUtil.getTripel(sequence, 1, 7));

		assertEquals("-", SequenceUtil.getTripel(sequence, 2, 0));
		assertEquals("-", SequenceUtil.getTripel(sequence, 2, 1));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 2, 2));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 2, 3));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 2, 4));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 2, 5));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 2, 6));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 2, 7));
		assertEquals("IJK", SequenceUtil.getTripel(sequence, 2, 8));
	}

	public void testGetTripelWithMutation() {

		String sequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		assertEquals("XBC", SequenceUtil.getTripelWithMutation(sequence, 0, 0, "X"));
		assertEquals("AXC", SequenceUtil.getTripelWithMutation(sequence, 0, 1, "X"));
		assertEquals("ABX", SequenceUtil.getTripelWithMutation(sequence, 0, 2, "X"));

		assertEquals("XCD", SequenceUtil.getTripelWithMutation(sequence, 1, 1, "X"));
		assertEquals("BXD", SequenceUtil.getTripelWithMutation(sequence, 1, 2, "X"));
		assertEquals("BCX", SequenceUtil.getTripelWithMutation(sequence, 1, 3, "X"));

	}
	
	public void testLoadCodonTable(){
		Map<String, String> codonTable = SequenceUtil.loadCodonTable(AnnotateTool.CODON_TABLE_FILENAME);
		
		assertEquals("Ala", codonTable.get("GCT"));
		assertEquals("Ala", codonTable.get("GCG"));
		assertEquals("Asp", codonTable.get("GAC"));
		assertEquals("STOP", codonTable.get("TAG"));
		assertEquals("Met", codonTable.get("ATG"));
		
	}

}
