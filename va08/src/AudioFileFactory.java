public class AudioFileFactory {

	public static AudioFile getInstance(String pathname) { 
		if (pathname.toLowerCase().endsWith(".wav")) {
			WavFile file = new WavFile(pathname);
			return file;
		}
		if(pathname.toLowerCase().endsWith(".mp3")||pathname.toLowerCase().endsWith(".ogg")){
			TaggedFile file = new TaggedFile(pathname);
			return file;
		} else {
			throw new RuntimeException("Unknown suffix for AudioFile: \""+ pathname + "\"");
		}
	}
}
