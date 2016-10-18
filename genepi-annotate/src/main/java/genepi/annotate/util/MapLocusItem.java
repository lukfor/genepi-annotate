package genepi.annotate.util;

public class MapLocusItem {

	private String shorthand;

	private String description;

	private String coding;

	private String translated;

	private String readingFrame;

	private int start;

	private int stop;

	public String getShorthand() {
		return shorthand;
	}

	public void setShorthand(String shorthand) {
		this.shorthand = shorthand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoding() {
		return coding;
	}

	public void setCoding(String coding) {
		this.coding = coding;
	}

	public String getTranslated() {
		return translated;
	}

	public void setTranslated(String translated) {
		this.translated = translated;
	}

	public String getReadingFrame() {
		return readingFrame;
	}

	public void setReadingFrame(String readingFrame) {
		this.readingFrame = readingFrame;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getStop() {
		return stop;
	}

	public void setStop(int stop) {
		this.stop = stop;
	}

}
