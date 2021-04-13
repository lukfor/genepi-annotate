package genepi.annotate.util;

import java.io.IOException;

import genepi.io.table.reader.CsvTableReader;
import htsjdk.samtools.util.IntervalTree;

public class MapLocus extends AbstractMapLocus{

	public MapLocus(String filename) throws IOException {
		super(filename);
	}

	@Override
	protected IntervalTree<MapLocusItem> loadFromFile(String filename) {
		IntervalTree<MapLocusItem> intervalTree = new IntervalTree<MapLocusItem>();
		CsvTableReader reader = new CsvTableReader(filename, '\t');
		while (reader.next()) {
			MapLocusItem mapLocusItem = new MapLocusItem();
			mapLocusItem.setShorthand(reader.getString("Shorthand"));
			mapLocusItem.setDescription(reader.getString("Description"));
			mapLocusItem.setCoding(reader.getString("Coding"));
			mapLocusItem.setTranslated(reader.getString("Translated"));
			mapLocusItem.setReadingFrame(reader.getString("ReadingFrame"));
			mapLocusItem.setStart(reader.getInteger("Start"));
			mapLocusItem.setStop(reader.getInteger("Stop"));

			intervalTree.put(mapLocusItem.getStart(), mapLocusItem.getStop(), mapLocusItem);

		}
		reader.close();
		return intervalTree;
	}
	
}
