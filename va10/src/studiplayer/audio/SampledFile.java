package studiplayer.audio;
import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
	long duration;
	
	
	public SampledFile(){
		super();
	}
	public SampledFile(String s)throws NotPlayableException {
		super(s);
	}
	public void play()throws NotPlayableException { 
		try {
			BasicPlayer.play(pathname);
		}catch(RuntimeException e){
			throw new NotPlayableException(pathname, e.getMessage());
		}
	}
	public void togglePause() {
		BasicPlayer.togglePause();
	}
	public void stop() {
		BasicPlayer.stop();
	}
	public String getFormattedDuration() { return timeFormatter(duration); };
	
	public String getFormattedPosition() { return timeFormatter(BasicPlayer.getPosition()); }
	
	public static String timeFormatter(long microtime) {
		if (microtime < 0) {
			throw new RuntimeException("Negativ time value provided");
		}
		if (microtime >= 6_000_000_000L) { //größer als 99:59
			throw new RuntimeException("Time value exceeds allowed format");
		}
		long allseconds = microtime / 1000000;  //double zu long gemacht
		long minutes = allseconds/60;	
		long seconds = allseconds%60;
		return String.format("%02d:%02d", minutes, seconds);
	}
}
