package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {
	public int compare(AudioFile af1, AudioFile af2) {
	    if(!(af1 instanceof SampledFile)&&(af2 instanceof SampledFile)) {
	        return -1;
	    }
	    if((af1 instanceof SampledFile)&&!(af2 instanceof SampledFile)) {
	        return 1;
	    }
	    if(!(af1 instanceof SampledFile)&&!(af2 instanceof SampledFile)) {
	    	return 0;
	    }
	    SampledFile sf1 = (SampledFile) af1;
	    SampledFile sf2 = (SampledFile) af2;
		if(sf1 == null && sf2 == null) {
			throw new NullPointerException("tf1 and 2 is null");
		}if(sf1 == null && sf2 != null) {
			throw new NullPointerException("tf1 is null");
		}if(sf2 == null && sf1 != null) {
			throw new NullPointerException("tf2 is null");
		}if(sf1.getFormattedDuration()!= null && sf2.getFormattedDuration() != null) {
			return sf1.getFormattedDuration().compareTo(sf2.getFormattedDuration());
		}if(sf1.getFormattedDuration() == null && sf2.getFormattedDuration() != null) {
			return 1;
		}if(sf1.getFormattedDuration() != null && sf2.getFormattedDuration() == null) {
			return -1;
		}
		return 0;
	}
}
