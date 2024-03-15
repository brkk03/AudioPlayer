package studiplayer.audio;
import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

	
	public WavFile(){};
	public WavFile(String pathname)throws NotPlayableException {
		super(pathname);
		readAndSetDurationFromFile(pathname);
	}
	public static long computeDuration(long numberOfFrames, float frameRate) { 
		return (long)(numberOfFrames / frameRate * 1_000_000.0F);
	}
	public void readAndSetDurationFromFile(String pathname)throws NotPlayableException { 
		try {
			WavParamReader.readParams(pathname);
			float frameRate = WavParamReader.getFrameRate();
			long numberOfFrames = WavParamReader.getNumberOfFrames();
			duration = computeDuration(numberOfFrames, frameRate);
		}catch(RuntimeException e){
			throw new NotPlayableException(pathname, e.getMessage());
		}
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
