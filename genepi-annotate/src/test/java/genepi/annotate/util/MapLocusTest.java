package genepi.annotate.util;

import java.util.Iterator;

import htsjdk.samtools.util.IntervalTree.Node;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MapLocusTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public MapLocusTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MapLocusTest.class);
	}

	public void testMapLocus() {

		String filename = "MapLocusLPA (FOR LONG PCR) - v3.txt";

		MapLocus mapLocus = new MapLocus(filename);

		// find all intervals
		Iterator<Node<MapLocusItem>> results = mapLocus.findByRange(1, 5104);
		int foundIntervals = 0;
		while (results.hasNext()) {
			results.next();
			foundIntervals++;
		}
		assertEquals(13, foundIntervals);

		// find first 3 intervaös
		results = mapLocus.findByRange(1, 580);
		foundIntervals = 0;
		while (results.hasNext()) {
			results.next();
			foundIntervals++;
		}
		assertEquals(3, foundIntervals);
		
		// find iterval on pos 500
		results = mapLocus.findByPosition(500);
		foundIntervals = 0;
		while (results.hasNext()) {
			MapLocusItem item = results.next().getValue();
			assertEquals("short-I", item.getShorthand());
			assertEquals("short intron", item.getDescription());
			foundIntervals++;
		}
		assertEquals(1, foundIntervals);

		// find iterval on pos 740
		results = mapLocus.findByPosition(740);
		foundIntervals = 0;
		while (results.hasNext()) {
			MapLocusItem item = results.next().getValue();
			assertEquals("Exon421", item.getShorthand());
			assertEquals("Exon 421", item.getDescription());
			foundIntervals++;
		}
		assertEquals(1, foundIntervals);

		
	}

}
