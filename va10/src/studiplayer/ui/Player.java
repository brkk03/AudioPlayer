package studiplayer.ui;
import java.net.URL;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import studiplayer.audio.PlayList;
import studiplayer.audio.AudioFile;
import studiplayer.audio.NotPlayableException;

public class Player extends Application{
	private Stage primaryStage;
	private PlayList playList;
	private Label songDescription, playTime;
	private Button playButton, pauseButton, stopButton, editorButton, nextButton;
	private volatile boolean stopped = true;
	private boolean editorVisible;
	List<String> parameters;
	private volatile AudioFile af;
	private PlayListEditor playListEditor;
	public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
	public static final String DEFAULT_PLAYTIME = "00:00";
	public static final String STAGE_TITLE =	  "Current song: ";
	
	public Player() {}
	
	public static void main(String[]args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage=primaryStage;
		editorVisible = false;
		parameters = getParameters().getRaw();
		BorderPane mainPane = new BorderPane();
		Scene scene = new Scene(mainPane,700,90);
		//Objekterzeugung vom Typ PlayList
		if (!parameters.isEmpty()) {
			this.playList = new PlayList(this.parameters.get(0));
		} else {
			this.playList = new PlayList(DEFAULT_PLAYLIST);;
		}
		if(playList !=null) {
			af = playList.getCurrentAudioFile();
		} else {
			af = null;
		}
		//PlayListEditor wird erzeugt
		this.playListEditor = new PlayListEditor(this, playList);
		//Titel des Hauptfensters
		if(af != null) {
			primaryStage.setTitle(STAGE_TITLE + af.toString()); 
		} else {
			primaryStage.setTitle("no current song");
		}
		//songDescription wird erzeugt
		songDescription= new Label("");
		//UI Oben songDescription
		mainPane.setTop(songDescription);
		//playTime wird erzeugt
		playTime = new Label(DEFAULT_PLAYTIME);
		//Buttons für HBOX
		playButton = this.createButton("play.png");
		pauseButton = this.createButton("pause.png");
		stopButton = this.createButton("stop.png");
		nextButton = this.createButton("next.png");
		editorButton = this.createButton("pl_editor.png");
		//HBOX
		HBox hbox = new HBox();
		hbox.getChildren().add(this.playTime);
		hbox.getChildren().add(this.playButton);
		hbox.getChildren().add(this.pauseButton);
		hbox.getChildren().add(this.stopButton);
		hbox.getChildren().add(this.nextButton);
		hbox.getChildren().add(this.editorButton);
		//UI Mitte HBOX
		mainPane.setCenter(hbox);
		primaryStage.setScene(scene);
		primaryStage.show();
		//Buttons Funktionen
		playButton.setOnAction(e -> {playCurrentSong();});
		pauseButton.setOnAction(e -> {pauseCurrentSong();});
		stopButton.setOnAction(e -> {stopCurrentSong();});
		nextButton.setOnAction(e -> {nextCurrentSong();});
		editorButton.setOnAction(e -> {
			if(editorVisible) {
				editorVisible = false;
				playListEditor.hide();
			}else {
				editorVisible = true;
				playListEditor.show();
			}
		});
		//Initialisierung der Button Zustände
		refreshUI();
	}
	//Methoden für Button Funktionen
	public void playCurrentSong() {
		stopped = false;
		if (af != null) {
			(new TimerThread()).start();
			(new PlayerThread()).start();
		}
		setButtonStates(true, false, false, false, false);
		//updateSongInfo(af);
		primaryStage.setTitle(STAGE_TITLE + af.toString());
	}
	public void stopCurrentSong() {
		playTime.setText(DEFAULT_PLAYTIME);
		updateSongInfo(af);
		stopped = true; 
		setButtonStates(false, true, false, true, false);
		if(af != null) {
			af.stop();
		}
		primaryStage.setTitle(STAGE_TITLE + af.toString());
	}
	public void nextCurrentSong() {
		playTime.setText(DEFAULT_PLAYTIME);
		if(!stopped) {
			stopCurrentSong();
		}
		setButtonStates(true, false, false, false, false);
		playList.changeCurrent();
		af=playList.getCurrentAudioFile();
		playCurrentSong();
	}
	public void pauseCurrentSong() {
		setButtonStates(true, false, false, false, false);
		if(af != null) {
			af.togglePause();
		}
		af = playList.getCurrentAudioFile();
		updateSongInfo(af);
	}
	//Aktualisierung von songDescription und playTime
	private void updateSongInfo(AudioFile af) {
		if(af!=null) {
			playTime.setText(af.getFormattedPosition());
			af= playList.getCurrentAudioFile();
			songDescription.setText(af.toString());
			primaryStage.setTitle(af.toString());
		} else {
			songDescription.setText("no current song");
			playTime.setText("00:00");
		}
	}
	//Aktualisierung der Buttons
	private void refreshUI() {
		Platform.runLater(() -> {
			if(playList != null && playList.size()>0) {
				updateSongInfo(af);
				setButtonStates(false, true, false, true, false);
			}else {
				updateSongInfo(null);
				setButtonStates(true, true, true, true, false);
			}
		});
	}
	//Button Zustand abhängig von Situation
	private void setButtonStates(boolean playButtonState, boolean stopButtonState, boolean nextButtonState, boolean pauseButtonState, boolean editorButtonState) {
		playButton.setDisable(playButtonState);;
		stopButton.setDisable(stopButtonState);
		nextButton.setDisable(nextButtonState);
		pauseButton.setDisable(pauseButtonState);
		editorButton.setDisable(editorButtonState);
	}
	//Erstellung der Button
	private Button createButton(String iconfile) {
		Button button = null;
		try {
			URL url = getClass().getResource("/icons/" + iconfile);
				Image icon = new Image(url.toString());
				ImageView imageView = new ImageView(icon);
				imageView.setFitHeight(48);
				imageView.setFitWidth(48);
				button = new Button("", imageView);
				button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		} catch(Exception e) {
			System.out.println("Image " + "icons/"+iconfile+" not found!");
			System.exit(-1);
		}
		return button;
	}
	//Threads
	private class TimerThread extends Thread {
		public void run() {
			while(!stopped&& af!=null) { 
				Platform.runLater(() -> {playTime.setText(af.getFormattedPosition());});
				try {
					sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException();
				}
			}
		}
	}
	private class PlayerThread extends Thread {
		public void run() {
			while(!stopped && af!=null) {
				try {
					af.play();
					Platform.runLater(() -> updateSongInfo(playList.getCurrentAudioFile()));
				} catch (NotPlayableException e) {
					e.printStackTrace();
				}
				if(!stopped) {
					playList.changeCurrent();
					af=playList.getCurrentAudioFile();
					Platform.runLater(() -> {updateSongInfo(af);});
				}
			}
		}
	}
	//Setter und Getter
	public void setEditorVisible(boolean editorVisible) { this.editorVisible = editorVisible;}
	public void setPlayList(String playListPath) {
		if(playListPath==null||playListPath.isBlank()) {
			playList = new PlayList(DEFAULT_PLAYLIST);
		} else {
			playList = new PlayList(playListPath);
		}
		refreshUI();
	}
	public String getPlayListPathname() {return parameters.get(0);}
}