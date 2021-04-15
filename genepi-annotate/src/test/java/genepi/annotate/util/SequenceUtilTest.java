package genepi.annotate.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Test;

import genepi.annotate.AnnotateTool;

public class SequenceUtilTest {

	@Test
	public void testGetTrippel() {

		String sequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		assertEquals("ABC", SequenceUtil.getTripel(sequence, 0, 0, 0));
		assertEquals("ABC", SequenceUtil.getTripel(sequence, 0, 0, 1));
		assertEquals("ABC", SequenceUtil.getTripel(sequence, 0, 0, 2));
		assertEquals("DEF", SequenceUtil.getTripel(sequence, 0, 0, 3));
		assertEquals("DEF", SequenceUtil.getTripel(sequence, 0, 0, 4));
		assertEquals("DEF", SequenceUtil.getTripel(sequence, 0, 0, 5));
		assertEquals("GHI", SequenceUtil.getTripel(sequence, 0, 0, 6));

		assertEquals("-", SequenceUtil.getTripel(sequence, 0, 1, 0));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 0, 1, 1));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 0, 1, 2));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 0, 1, 3));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 0, 1, 4));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 0, 1, 5));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 0, 1, 6));
		assertEquals("HIJ", SequenceUtil.getTripel(sequence, 0, 1, 7));

		assertEquals("-", SequenceUtil.getTripel(sequence, 0, 2, 0));
		assertEquals("-", SequenceUtil.getTripel(sequence, 0, 2, 1));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 0, 2, 2));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 0, 2, 3));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 0, 2, 4));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 0, 2, 5));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 0, 2, 6));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 0, 2, 7));
		assertEquals("IJK", SequenceUtil.getTripel(sequence, 0, 2, 8));

		assertEquals("-", SequenceUtil.getTripel(sequence, 1, 0, 0));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 1, 0, 1));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 1, 0, 2));
		assertEquals("BCD", SequenceUtil.getTripel(sequence, 1, 0, 3));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 1, 0, 4));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 1, 0, 5));
		assertEquals("EFG", SequenceUtil.getTripel(sequence, 1, 0, 6));

		assertEquals("-", SequenceUtil.getTripel(sequence, 2, 0, 0));
		assertEquals("-", SequenceUtil.getTripel(sequence, 2, 0, 1));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 2, 0, 2));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 2, 0, 3));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 2, 0, 4));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 2, 0, 5));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 2, 0, 6));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 2, 0, 7));
		assertEquals("IJK", SequenceUtil.getTripel(sequence, 2, 0, 8));

		assertEquals("-", SequenceUtil.getTripel(sequence, 1, 1, 0));
		assertEquals("-", SequenceUtil.getTripel(sequence, 1, 1, 1));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 1, 1, 2));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 1, 1, 3));
		assertEquals("CDE", SequenceUtil.getTripel(sequence, 1, 1, 4));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 1, 1, 5));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 1, 1, 6));
		assertEquals("FGH", SequenceUtil.getTripel(sequence, 1, 1, 7));
		assertEquals("IJK", SequenceUtil.getTripel(sequence, 1, 1, 8));

	}

	@Test
	public void testGetTrippelRev() throws Exception {

		String sequence = "ACGTTGACACGT";

		assertEquals(SequenceUtil.getReverseComplement("ACG"), SequenceUtil.getTripelRev(sequence, 6, 0, 1));
		assertEquals(SequenceUtil.getReverseComplement("ACG"), SequenceUtil.getTripelRev(sequence, 6, 0, 2));
		assertEquals(SequenceUtil.getReverseComplement("ACG"), SequenceUtil.getTripelRev(sequence, 6, 0, 3));
		assertEquals(SequenceUtil.getReverseComplement("TTG"), SequenceUtil.getTripelRev(sequence, 6, 0, 4));
		assertEquals(SequenceUtil.getReverseComplement("TTG"), SequenceUtil.getTripelRev(sequence, 6, 0, 5));
		assertEquals(SequenceUtil.getReverseComplement("TTG"), SequenceUtil.getTripelRev(sequence, 6, 0, 6));

		assertEquals(SequenceUtil.getReverseComplement("CGT"), SequenceUtil.getTripelRev(sequence, 7, 0, 2));
		assertEquals(SequenceUtil.getReverseComplement("CGT"), SequenceUtil.getTripelRev(sequence, 7, 0, 3));
		assertEquals(SequenceUtil.getReverseComplement("CGT"), SequenceUtil.getTripelRev(sequence, 7, 0, 4));

		assertEquals("-", SequenceUtil.getTripelRev(sequence, 7, 0, 1));
		assertEquals("-", SequenceUtil.getTripelRev(sequence, 6, 0, 7));
		
		assertEquals(SequenceUtil.getReverseComplement("GTT"), SequenceUtil.getTripelRev(sequence, 6, 1, 3));
		assertEquals(SequenceUtil.getReverseComplement("CGT"), SequenceUtil.getTripelRev(sequence, 6, 2, 3));
		assertEquals(SequenceUtil.getReverseComplement("ACG"), SequenceUtil.getTripelRev(sequence, 6, 3, 3));

	}

	@Test
	public void testGetReverseComplement() throws Exception {
		assertEquals("ggggaaaaaaaatttatatat", SequenceUtil.getReverseComplement("atatataaattttttttcccc"));
		assertEquals("atatataaattttttttcccc", SequenceUtil.getReverseComplement("ggggaaaaaaaatttatatat"));
		assertEquals("atatataaattttttttccNc", SequenceUtil.getReverseComplement("gNggaaaaaaaatttatatat"));
		assertEquals("", SequenceUtil.getReverseComplement(""));
		assertEquals("", SequenceUtil.getReverseComplement(null));
	}

	@Test(expected = Exception.class)
	public void testGetReverseComplementWithIllegalCharacter() throws Exception {
		SequenceUtil.getReverseComplement("atatataatttttttXtcccc");
	}

	@Test
	public void testGetTripelWithMutation() {

		String sequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		assertEquals("XBC", SequenceUtil.getTripelWithMutation(sequence, 0, 0, 0, "X"));
		assertEquals("AXC", SequenceUtil.getTripelWithMutation(sequence, 0, 0, 1, "X"));
		assertEquals("ABX", SequenceUtil.getTripelWithMutation(sequence, 0, 0, 2, "X"));

		assertEquals("XCD", SequenceUtil.getTripelWithMutation(sequence, 0, 1, 1, "X"));
		assertEquals("BXD", SequenceUtil.getTripelWithMutation(sequence, 0, 1, 2, "X"));
		assertEquals("BCX", SequenceUtil.getTripelWithMutation(sequence, 0, 1, 3, "X"));

		assertEquals("XCD", SequenceUtil.getTripelWithMutation(sequence, 1, 0, 1, "X"));
		assertEquals("BXD", SequenceUtil.getTripelWithMutation(sequence, 1, 0, 2, "X"));
		assertEquals("BCX", SequenceUtil.getTripelWithMutation(sequence, 1, 0, 3, "X"));

		assertEquals("XDE", SequenceUtil.getTripelWithMutation(sequence, 1, 1, 2, "X"));
		assertEquals("CXE", SequenceUtil.getTripelWithMutation(sequence, 1, 1, 3, "X"));
		assertEquals("CDX", SequenceUtil.getTripelWithMutation(sequence, 1, 1, 4, "X"));

		assertEquals("XDE", SequenceUtil.getTripelWithMutation(sequence, 0, 2, 2, "X"));
		assertEquals("CXE", SequenceUtil.getTripelWithMutation(sequence, 0, 2, 3, "X"));
		assertEquals("CDX", SequenceUtil.getTripelWithMutation(sequence, 0, 2, 4, "X"));

		assertEquals("XDE", SequenceUtil.getTripelWithMutation(sequence, 2, 0, 2, "X"));
		assertEquals("CXE", SequenceUtil.getTripelWithMutation(sequence, 2, 0, 3, "X"));
		assertEquals("CDX", SequenceUtil.getTripelWithMutation(sequence, 2, 0, 4, "X"));

	}

	@Test
	public void testLoadCodonTable() {
		Map<String, String> codonTable = SequenceUtil.loadCodonTable(AnnotateTool.CODON_TABLE_FILENAME);

		assertEquals("Ala", codonTable.get("GCT"));
		assertEquals("Ala", codonTable.get("GCG"));
		assertEquals("Asp", codonTable.get("GAC"));
		assertEquals("STOP", codonTable.get("TAG"));
		assertEquals("Met", codonTable.get("ATG"));

	}

	@Test
	public void testLoadCodonTableLong() throws FileNotFoundException {
		Map<String, String> codonTable = SequenceUtil.loadCodonTableLong("test-data/SARSCOV2.aac.txt");
		assertEquals("A", codonTable.get("GCT"));
		assertEquals("A", codonTable.get("GCG"));
		assertEquals("D", codonTable.get("GAC"));
		assertEquals("*", codonTable.get("TAG"));
		assertEquals("M", codonTable.get("ATG"));

	}

	@Test
	public void testgetTripleReference() throws Exception {

		String reference = "test-data/SARSCOV2.fasta";

		String refSequence = SequenceUtil.readReferenceSequence(reference);
		assertEquals(29903, refSequence.length());
		assertEquals("AAT", SequenceUtil.getTripelZeroBased(refSequence, 21563, 0, 23063));

	}

}
