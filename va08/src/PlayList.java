import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;


@SuppressWarnings("serial")
public class PlayList extends LinkedList<AudioFile> { 
	private int current=0;
	private boolean randomOrder;
	
	PlayList(){
		setRandomOrder(randomOrder);
		changeCurrent();
		setCurrent(current);
	}
	PlayList(String pathname) {
		this();
		loadFromM3U(pathname);
	}
	
	public int getCurrent() {return current;}
	public void setCurrent(int current) {this.current=current;}
	AudioFile getCurrentAudioFile() {
		if(current>=this.size()-1) {
			return null;
		}
		return this.get(current);
	}
	public void changeCurrent() {
		if(current>=this.size()-1) {
			current = 0;
			this.setRandomOrder(randomOrder);
		}else {
			current += 1;
		}
	}
	public void setRandomOrder(boolean randomOrder) {
		if(randomOrder) {
			Collections.shuffle(this);
		}
		this.randomOrder= randomOrder;
	}
	public void saveAsM3U(String pathname){
		FileWriter writer = null;
		String fname = pathname;
		String linesep = System.getProperty("line.separator");
		try {
			writer = new FileWriter(fname);
			for (int i=0; i<=this.size()-1;i++) {
				writer.write(this.get(i).getPathname() + linesep); 
			}
		} catch(IOException e) {
			throw new RuntimeException("Unable to write to file " + fname + ":" + e.getMessage());
		}finally {
			try {
				writer.close();
			}catch(Exception e) {
			}
		}
	}
	public void loadFromM3U(String pathname) {
		String fname = pathname;
		Scanner scanner  = null;
		String line;
		this.clear();
		try {scanner = new Scanner(new File(fname));
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (!line.trim().startsWith("#")&&!line.isBlank()) {
					this.add(AudioFileFactory.getInstance(line));
				}
			}
		}catch (IOException e) {
			throw new RuntimeException(e);
		}finally {
			try {scanner.close();
			} catch (Exception e) {
			}
		}
	}
}