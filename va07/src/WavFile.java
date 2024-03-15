import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

	
	WavFile(){};
	WavFile(String pathname){
		super(pathname);
		readAndSetDurationFromFile(pathname);
	}
	public static long computeDuration(long numberOfFrames, float frameRate) { 
		return (long)(numberOfFrames / frameRate * 1_000_000.0F);
	}
	public void readAndSetDurationFromFile(String pathname) { 
		WavParamReader.readParams(pathname);
		float frameRate = WavParamReader.getFrameRate();
		long numberOfFrames = WavParamReader.getNumberOfFrames();
		duration = computeDuration(numberOfFrames, frameRate);
	}
	public String toString() {
		return author +  " - " + title + " - " + getFormattedDuration();
	}
	public String[] fields() {
		String [] fields = new String[4];
		fields[0] = author;
		fields[1] = title;
		fields[2] = "";
		fields[3] = getFormattedDuration();
		return fields;
	}
}
