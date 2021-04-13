package genepi.annotate.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import htsjdk.samtools.util.IntervalTree;

public class MapLocusGFF3 extends AbstractMapLocus {

	public MapLocusGFF3(String filename) throws IOException {
		super(filename);
	}

	@Override
	protected IntervalTree<MapLocusItem> loadFromFile(String filename) throws IOException {
		IntervalTree<MapLocusItem> intervalTree = new IntervalTree<MapLocusItem>();
		FileReader reader = new FileReader(filename);

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
					String name = st2.nextToken().split("gene-")[1];
					MapLocusItem mapLocusItem = new MapLocusItem();
					mapLocusItem.setShorthand(name);
					mapLocusItem.setDescription(name);
					mapLocusItem.setCoding("coding");
					mapLocusItem.setTranslated(strand.equals("+") ? "F" : "R");
					mapLocusItem.setReadingFrame(phase);
					mapLocusItem.setStart(start);
					mapLocusItem.setStop(end);

					intervalTree.put(mapLocusItem.getStart(), mapLocusItem.getStop(), mapLocusItem);
				}
			}
		}
		br.close();
		reader.close();

		return intervalTree;
	}

}
