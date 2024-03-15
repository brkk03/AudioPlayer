import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
	long duration;
	
	
	SampledFile(){
		super();
	}
	SampledFile(String s){
		super(s);
	}
	public void play() {
		BasicPlayer.play(pathname);
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
