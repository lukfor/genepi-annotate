package genepi.annotate.util;

import java.io.IOException;
import java.util.Iterator;

import htsjdk.samtools.util.IntervalTree;
import htsjdk.samtools.util.IntervalTree.Node;

public abstract class AbstractMapLocus {

	protected IntervalTree<MapLocusItem> intervalTree;

	public AbstractMapLocus(String filename) throws IOException {
		intervalTree = loadFromFile(filename);
	}

	protected abstract IntervalTree<MapLocusItem> loadFromFile(String filename) throws IOException;
	
	public Iterator<Node<MapLocusItem>> findByRange(int start, int stop){
		return intervalTree.overlappers(start, stop);
	}
	
	public Iterator<Node<MapLocusItem>> findByPosition(int position){
		return findByRange(position, position);
	}
}
