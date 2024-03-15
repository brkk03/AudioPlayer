package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile>{
	public int compare(AudioFile af1, AudioFile af2) {
		if(af1 == null && af2 == null) {
			throw new NullPointerException("af1 and 2 is null");
		}if(af1 == null && af2 != null) {
			throw new NullPointerException("af1 is null");
		}if(af2 == null && af1 != null) {
			throw new NullPointerException("af2 is null");
		}if(af1.getTitle()!= null && af2.getTitle() != null) {
			return af1.getTitle().compareTo(af2.getTitle());
		}if(af1.getTitle() == null && af2.getTitle() != null) {
			return 1;
		}if(af1.getTitle() != null && af2.getTitle() == null) {
			return -1;
		}
		return 0;
	}
}


