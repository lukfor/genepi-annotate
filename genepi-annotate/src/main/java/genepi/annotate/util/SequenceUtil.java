package genepi.annotate.util;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import genepi.io.table.reader.CsvTableReader;
import genepi.io.text.LineReader;

public class SequenceUtil {
	public static String readReferenceSequence(String filename) throws Exception {
		LineReader reader = new LineReader(filename);
		if (!reader.next()) {
			throw new Exception("Empty file.");
		}
		String name = reader.get();
		if (!name.startsWith(">")) {
			throw new Exception("Not valid fasta file. Missing name in line 1.");

		}
		if (!reader.next()) {
			throw new Exception("File too short. No second line.");
		}
		return reader.get();
	}

	public static String getTripel(String refSequence, int startExon, int offset, int position) {

		int firstBase = startExon + offset;
		if (position < firstBase) {
			return "-";
		}

		int difference = position - firstBase;
		int relativeOffset = difference % 3;
		int start = position - relativeOffset;
		if (start + 3 >= refSequence.length()) {
			return "-";
		}

		return refSequence.substring(start, start + 3);
	}

	public static String getTripelWithMutation(String refSequence, int startExon, int offset, int position,
			String variant) {

		int firstBase = startExon + offset;
		if (position < firstBase) {
			return "-";
		}

		int difference = position - firstBase;
		int relativeOffset = difference % 3;

		StringBuilder temp = new StringBuilder(getTripel(refSequence, startExon, offset, position));
		temp.setCharAt(relativeOffset, variant.charAt(0));
		return temp.toString();
	}

	public static Map<String, String> loadCodonTable(String filename) {
		InputStream cpResource = SequenceUtil.class.getClassLoader().getResourceAsStream(filename);
		HashMap<String, String> codonTable = new HashMap<String, String>();
		CsvTableReader reader = new CsvTableReader(new DataInputStream(cpResource), '\t');
		while (reader.next()) {
			String aminoAcid = reader.getString("Amino acid");
			String[] codons = reader.getString("Codons").split(",");
			for (String codon : codons) {
				codonTable.put(codon.trim(), aminoAcid);
			}
		}
		reader.close();

		return codonTable;
	}

}
