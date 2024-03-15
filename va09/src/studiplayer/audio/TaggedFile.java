package studiplayer.audio;
import studiplayer.basic.TagReader;
import java.util.Map;

public class TaggedFile extends SampledFile {
	String album;
	
	public TaggedFile() {
		super();
	}
	public TaggedFile(String s)throws NotPlayableException {
		super(s); 
		readAndStoreTags();
	}
	
	public void readAndStoreTags()throws NotPlayableException {
		try {
			Map<String, Object> tag_map = TagReader.readTags(pathname);
			if (tag_map.containsKey("title")) {
				title = (String)tag_map.get("title");
				if (title!=null&&!title.isEmpty()) {
					title = title.trim();
				}
			}
			if (tag_map.containsKey("author")) {
				author = (String)tag_map.get("author");
				if (author!=null&&!author.isEmpty()) {
					author = author.trim();
				}
			}
			if (tag_map.containsKey("album")) {
				album = (String)tag_map.get("album");
				if (album!=null&&!album.isEmpty()) {
					album = album.trim();
				}
			}
			if (tag_map.containsKey("duration")) {
				duration = (Long)tag_map.get("duration");
			}
		}catch(RuntimeException e){
			throw new NotPlayableException(pathname, e.getMessage());
		}
	}

	public String getAlbum() {return album;}
	
	public String toString() {
		String authorprint;
		String titleprint;
		String albumprint="";
		if (author!=null&&!author.isBlank()) {
			authorprint = author + " - ";
		} else {
			authorprint = "";
		}
		if (title!=null&&!title.isBlank()) {
			titleprint = title + " - ";
		} else {
			titleprint = "";
		}
		if (album!=null&&!album.isBlank()) {
			albumprint = album + " - ";
		} else {
			album = "";
		}
		return (authorprint + titleprint + albumprint + getFormattedDuration()).trim();
	}
	public String[] fields() {
		String [] fields = new String[4];
		fields[0] = author;
		fields[1] = title;
		fields[2] = album;
		fields[3] = getFormattedDuration();
		return fields;
	}
}
