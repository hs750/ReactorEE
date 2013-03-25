package ReactorEE.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Music {
	protected static final List<String> menu = Arrays.asList("menu-1", "menu-2");
	protected static final List<String> playing = Arrays.asList("game-1", "game-2", "game-3", "game-4");
	protected static final String path = "Music/";
	protected static final String extension = ".mp3";
	
	protected Map<GameStatus, List<String>> files;
	protected GameStatus status;
	protected String currentlyPlaying;
	protected AdvancedPlayer player;
	protected Thread musicThread;
	
	// singleton stuff
	private static Music instance = null;
	private static void setup() { instance = new Music(); }
	
	private Music() {
		status = GameStatus.MENU;
		files =	new HashMap<GameStatus, List<String>>();
		
		files.put(GameStatus.MENU, menu);
		files.put(GameStatus.PLAYING, playing);
		
		currentlyPlaying = path + files.get(GameStatus.MENU).get(0) + extension;
		player = getPlayer(currentlyPlaying);
	}
	
	// useful
	protected AdvancedPlayer getPlayer(String filename) {
		AdvancedPlayer out = null;
		
		try {
			player = new AdvancedPlayer(new BufferedInputStream(new FileInputStream(filename)));
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (JavaLayerException e) {
			System.out.println(e);
		}
		
		return out;
	}
	
	// API
	public static void play() {
		if(instance == null) setup();
		
	}
	
	public static void pause() {
		if(instance == null) setup();
		
	}
	
	public static void changeStatus(GameStatus newStatus) {
		if(newStatus == instance.status)
			return;
		
		instance.status = newStatus;
		
		// TODO add code to handle track change
	}
	
	public static void play(GameStatus newStatus) {
		if(instance == null) setup();
		
		pause();
		changeStatus(newStatus);
		play();
	}
	
	public static Thread getThread() {
		if(instance == null) setup();
		return instance.musicThread;
	}
	
	// legacy
	;
	public static void test() {
		String filename = "Music/game-1.mp3";
		MP3 mp3 = new MP3(filename);
		mp3.play();

		// do whatever computation you like, while music plays
		int N = 15000;
		double sum = 0.0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += Math.sin(i + j);
			}
		}
		System.out.println(sum);

		// when the computation is done, stop playing it
		mp3.close();
	}
	public static void main(String[] args) { test(); }
}



class MP3 {
	private String filename;
	private AdvancedPlayer player;

	// constructor that takes the path of an MP3 file
	public MP3(String filename) {
		this.filename = filename;
	}

	public void close() {
		if (player != null)
			player.close();
	}

	// play the MP3 file to the sound card
	public void play() {
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new AdvancedPlayer(bis);
		} catch (Exception e) {
			System.out.println("Problem playing file " + filename);
			System.out.println(e);
		}

		// run in new thread to play in background
		new Thread() {
			public void run() {
				try {
					player.play();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}.start();
	}
}

