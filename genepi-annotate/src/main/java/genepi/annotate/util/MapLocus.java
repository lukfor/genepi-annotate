package genepi.annotate.util;

import java.util.Iterator;

import genepi.io.table.reader.CsvTableReader;
import htsjdk.samtools.util.IntervalTree;
import htsjdk.samtools.util.IntervalTree.Node;

public class MapLocus {

	private IntervalTree<MapLocusItem> intervalTree;

	public MapLocus(String filename) {
		intervalTree = loadFromFile(filename);
	}

	private IntervalTree<MapLocusItem> loadFromFile(String filename) {
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
	
	public Iterator<Node<MapLocusItem>> findByRange(int start, int stop){
		return intervalTree.overlappers(start, stop);
	}
	
	public Iterator<Node<MapLocusItem>> findByPosition(int position){
		return findByRange(position, position);
	}
}
