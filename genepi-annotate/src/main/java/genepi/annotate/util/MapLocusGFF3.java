package genepi.annotate.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import genepi.io.table.reader.CsvTableReader;
import htsjdk.samtools.util.IntervalTree;
import htsjdk.samtools.util.IntervalTree.Node;

public class MapLocusGFF3 {

	private IntervalTree<MapLocusItem> intervalTree;

	public MapLocusGFF3(String filename) {
		intervalTree = loadFromGFF3File(filename);
	}

	private IntervalTree<MapLocusItem> loadFromGFF3File(String filename) {
		IntervalTree<MapLocusItem> intervalTree = new IntervalTree<MapLocusItem>();
		FileReader reader;
		try {
			reader = new FileReader(filename);

			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.contains("##")) {
					StringTokenizer st = new StringTokenizer(line, "\t");
					st.nextToken(); // 1. Sequence ID
					st.nextToken(); // 2. Source e.g. GenBank
					String featureType = st.nextToken(); // 3. Feature Type
					if (featureType.equals("CDS")) {
						int start = Integer.valueOf(st.nextToken()); // 4. Feature Start
						int end = Integer.valueOf(st.nextToken()); // 5. Feature Stop
						st.nextToken(); // 6. Score
						String strand = st.nextToken(); // 7. Strand
						String phase = st.nextToken(); // 8. Phase
						StringTokenizer st2 = new StringTokenizer(st.nextToken(), ";");
						String id = st2.nextToken().split("=")[1];
						String name = st2.nextToken().split("=")[1];
						System.out.println(start + "  " + end + " " + id + " " + name + " " + phase);

						MapLocusItem mapLocusItem = new MapLocusItem();
						mapLocusItem.setShorthand(name);
						mapLocusItem.setDescription(name);
						mapLocusItem.setCoding("coding");
						mapLocusItem.setTranslated(strand == "+" ? "F" : "R");
						mapLocusItem.setReadingFrame(phase);
						mapLocusItem.setStart(start);
						mapLocusItem.setStop(end);

						intervalTree.put(mapLocusItem.getStart(), mapLocusItem.getStop(), mapLocusItem);
					}
				}
			}
			br.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return intervalTree;
	}

	public Iterator<Node<MapLocusItem>> findByRange(int start, int stop) {
		return intervalTree.overlappers(start, stop);
	}

	public Iterator<Node<MapLocusItem>> findByPosition(int position) {
		return findByRange(position, position);
	}
}
