package genepi.annotate.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import genepi.io.table.reader.CsvTableReader;

public class SequenceUtil {
	public static String readReferenceSequence(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();

		if (line == null) {
			reader.close();
			throw new Exception("Empty file");
		}
		
		StringBuffer reference = new StringBuffer();

		if (!line.contains(">")) {
			reader.close();
			throw new Exception("Not a valid fasta file. Missing name in line 1.");
		}

		line = reader.readLine(); // read next line under sample name

		while (line != null) {
			if (line.contains(">")) {
				reader.close();
				throw new Exception("Mutlifasta file not allowed as reference.");
			} else {
				reference.append(line);
				line = reader.readLine();
			}
		}
		reader.close();
		return reference.toString();
	}

	// getTriple on FORWARD strand +
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

	public static String getTripelRev(String refSequence, int stopExon, int offset, int position) {

		int lastBase = stopExon - offset;
		if (position > lastBase) {
			return "-";
		}
		int difference = lastBase - position;
		int relativeOffset = difference % 3;
		int end = position + relativeOffset;
		if (end >= refSequence.length()) {
			return "-";
		}
		return getReverseComplement(refSequence.substring(end - 3, end));
	}

	public static String getTripelWithMutationRev(String refSequence, int stopExon, int offset, int position,
			String variant) {

		int lastBase = stopExon - offset;
		if (position > lastBase) {
			return "-";
		}
		int difference = lastBase - position;
		int relativeOffset = difference % 3;

		StringBuilder temp = new StringBuilder(getTripelRev(refSequence, stopExon, offset, position));
		temp.setCharAt(relativeOffset, getReverseComplement(variant).charAt(0));
		return temp.toString();

	}

	public static String getReverseComplement(String forwardTriple) {
		
		if (forwardTriple == null) {
			return "";
		}
		
		String result = "";
		for (int i = forwardTriple.length() - 1; i >= 0; i--) {
			switch (forwardTriple.charAt(i)) {
			case 'A':
				result += "T";
				break;
			case 'C':
				result += "G";
				break;
			case 'G':
				result += "C";
				break;
			case 'T':
				result += "A";
				break;
			case 'a':
				result += "t";
				break;
			case 'c':
				result += "g";
				break;
			case 'g':
				result += "c";
				break;
			case 't':
				result += "a";
				break;				
			}
		}
		return result;
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

	public static Map<String, String> loadCodonTableLong(String filename) throws FileNotFoundException {
		FileInputStream file;
		HashMap<String, String> codonTable = new HashMap<String, String>();

		file = new FileInputStream(filename);
		CsvTableReader reader = new CsvTableReader(new DataInputStream(file), '\t');
		while (reader.next()) {
			String aminoAcid = reader.getString("Letter");
			String codons = reader.getString("Codon");
			codonTable.put(codons.trim(), aminoAcid);
		}
		reader.close();

		return codonTable;
	}

	public static int getPosition(int start, int stop, int position, String direction) {
		if (direction.equals("F"))
			return ((position - start) / 3 + 1);
		else if (direction.equals("R"))
			return ((stop - position) / 3 + 1);
		else
			return 0;
	}

	public static String getTripelZeroBased(String refSequence, int start, int offset, int position) {
		return getTripel(refSequence, start - 1, offset, position - 1);
	}

	public static String getTripelWithMutationZeroBased(String refSequence, int startExon, int offset, int position,
			String variant) {
		return getTripelWithMutation(refSequence, startExon - 1, offset, position - 1, variant);
	}

	public static String getAAC(String refSequence, Map<String, String> codonTable, MapLocusItem item, int position,
			String variant) {

		if (item != null) {

			int offset = Integer.parseInt(item.getReadingFrame().trim());
			String tripelRef = "";
			String tripelMut = "";

			// FORWAD STRAND - Zerobased adapting
			if (item.getTranslated().equals("F")) {
				tripelRef = SequenceUtil.getTripelZeroBased(refSequence, item.getStart(), offset, position);
				tripelMut = SequenceUtil.getTripelWithMutationZeroBased(refSequence, item.getStart(), offset, position,
						variant);
			}
			// REVERSE STRAND
			else {
				tripelRef = SequenceUtil.getTripelRev(refSequence, item.getStop(), offset, position);
				tripelMut = SequenceUtil.getTripelWithMutationRev(refSequence, item.getStop(), offset, position,
						variant);
			}

			int posAAC = SequenceUtil.getPosition(item.getStart(), item.getStop(), position, item.getTranslated());

			String codonRef = codonTable.get(tripelRef);

			String codonMut = "?"; // for mixtures e.g. 22802S

			if (variant == "A" || variant == "C" || variant == "G" || variant == "T")
				codonMut = codonTable.get(tripelMut);

			else if (variant.toUpperCase() == "DEL")
				codonMut = "-";

			else if (variant.toUpperCase() == "INS")
				codonMut = "fs";

			else if (variant == "N")
				return "";

			if (!codonRef.equals(codonMut))
				return (codonRef + posAAC + codonMut);
		}
		return "";

	}
}