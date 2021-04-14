package genepi.annotate.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import htsjdk.samtools.util.IntervalTree.Node;

public class MapLocusGFF3Test {

	@Test
	public void testLoadFromFile() throws IOException {

		String filename = "test-data/SARSCOV2.gff";

		MapLocusGFF3 mapLocus = new MapLocusGFF3(filename);

		// find all intervals
		Iterator<Node<MapLocusItem>> results = mapLocus.findByRange(1, 25384);
		int foundIntervals = 0;
		while (results.hasNext()) {
			results.next();
			foundIntervals++;
		}
		assertEquals(3, foundIntervals);

		// find first 3 intervals
		results = mapLocus.findByRange(1, 25385);
		foundIntervals = 0;
		while (results.hasNext()) {
			results.next();
			foundIntervals++;
		}
		assertEquals(3, foundIntervals);
		
		// find iterval on pos 23063
		results = mapLocus.findByPosition(23063);
		foundIntervals = 0;
		while (results.hasNext()) {
			MapLocusItem item = results.next().getValue();
			assertEquals("S", item.getShorthand());
			assertEquals("S", item.getDescription());
			foundIntervals++;
		}
		assertEquals(1, foundIntervals);

		// find iterval on pos 27750
		results = mapLocus.findByPosition(27750);
		foundIntervals = 0;
		while (results.hasNext()) {
			MapLocusItem item = results.next().getValue();
			assertEquals("ORF7a", item.getShorthand());
			foundIntervals++;
		}
		assertEquals(1, foundIntervals);

		
	}

}
