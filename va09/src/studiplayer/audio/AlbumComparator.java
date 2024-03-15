package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
	public int compare(AudioFile af1, AudioFile af2) {
	    if (!(af1 instanceof TaggedFile)&&(af2 instanceof TaggedFile)) {
	        return -1;
	    }
	    if (!(af2 instanceof TaggedFile)&&(af1 instanceof TaggedFile)) {
	        return 1;
	    }
	    if(!(af2 instanceof TaggedFile)&&!(af1 instanceof TaggedFile)) {
	    	return 0;
	    }
	    TaggedFile tf1 = (TaggedFile) af1;
	    TaggedFile tf2 = (TaggedFile) af2;
		if(tf1 == null && tf2 == null) {
			throw new NullPointerException("tf1 and 2 is null");
		}if(tf1 == null && tf2 != null) {
			throw new NullPointerException("tf1 is null");
		}if(tf2 == null && tf1 != null) {
			throw new NullPointerException("tf2 is null");
		}if(tf1.getAlbum()!= null && tf2.getAlbum() != null) {
			return tf1.getAlbum().compareTo(tf2.getAlbum());
		}if(tf1.getAlbum() == null && tf2.getAlbum() != null) {
			return -1;
		}if(tf1.getAlbum() != null && tf2.getAlbum() == null) {
			return 1;
		}
		return 0;
	}
}
