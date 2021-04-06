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
import genepi.io.text.LineReader;

public class SequenceUtil {
	public static String readReferenceSequence(String filename) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line = reader.readLine();
		StringBuffer reference = new StringBuffer();

		if (!line.contains(">"))
			throw new Exception("Not a valid fasta file. Missing name in line 1.");
		
		line= reader.readLine(); //read next line under sample name 
		
		if (line==null)
			throw new Exception("Empty file");
		
		while (line != null) {
			if (line.contains(">")) {
				throw new Exception("Mutlifasta file not allowed as reference.");
			} else {
				reference.append(line);
				line = reader.readLine();
			}
		}
		reader.close();
		return reference.toString();
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

		System.out.println("VARIANT " + variant + "  " + relativeOffset + " " + startExon + " " + position);
		StringBuilder temp = new StringBuilder(getTripel(refSequence, startExon, offset, position));
		System.out.println(temp);
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

	public static Map<String, String> loadCodonTableLong(String filename) {
		FileInputStream file;
		HashMap<String, String> codonTable = new HashMap<String, String>();
		try {
			file = new FileInputStream(filename);
			CsvTableReader reader = new CsvTableReader(new DataInputStream(file), '\t');
			while (reader.next()) {
				String aminoAcid = reader.getString("Letter");
				String codons = reader.getString("Codons");
				codonTable.put(codons.trim(), aminoAcid);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codonTable;
	}

}
