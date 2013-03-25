package ReactorEE.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javazoom.jl.player.Player;

public class Music {
	public static void main(String[] args) { // this is bleeding edge i.e. not finished code! to have fun with it, run and type in a,s,d as seen below (you might have to add the .jar in Libraries/ to your build path)
		Music.play();
		System.out.println("hi 14");
		char x = 'y'; Scanner sc = new Scanner(System.in);
		while(x != 'q') {
			x = sc.next().toCharArray()[0];
			if(x == 'a') Music.play();
			if(x == 's') Music.pause();
			if(x == 'd') Music.kill();
		}
	}
	
	// API
	public static void play() {
		if(instance == null) setup();
		instance.ps = PlaybackStatus.PLAY;
	}
	
	public static void pause() {
		if(instance == null) setup();
		instance.ps = PlaybackStatus.PAUSE;
	}
	
	public static void kill() {
		if(instance == null) setup();
		instance.ps = PlaybackStatus.CLOSE;
	}
	
	public static void changeGameContext(String newGameContext) {
		GameStatus new_gs = GameStatus.fromString(newGameContext);
		
		if(new_gs == null)
			System.err.println("Music.changeGameContext: Incorrect string for game context (possible values are \"menu\", \"game\", \"credits\")");
		else
			instance.gs = new_gs;
	}
	
	// implementation
	private Music() {
		ps = PlaybackStatus.PAUSE;
		gs = GameStatus.MENU;
		
		if(playlist == null) {
			playlist = new HashMap<GameStatus, List<String>>();
			playlist.put(GameStatus.MENU, menu);
			playlist.put(GameStatus.PLAYING, playing);
			//playlist.put(GameStatus.CREDITS, credits);
		}
		System.out.println("hi 53");
		playback = new Thread(new Runnable() { public void run(){ instance.playback(); } });
		playback.start();
	}
	
	protected void playback() {
		assert(instance != null);
		Player player = createPlayer(gs,0); 
		
		while(true) {System.out.println("hi 61");
			if(ps == PlaybackStatus.CLOSE) {
				instance = null;
				return;
			} else if(ps == PlaybackStatus.PLAY) {
				try { player.play(8); } catch (Exception e) { System.out.println(e); }
			} else {
				try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	}

	protected static void play(final GameStatus gs, final int index) {
		if(instance == null) setup();
		Thread playback = new Thread(new Runnable() {
			public void run() {
				try { createPlayer(gs,index).play(); }
                catch (Exception e) { System.out.println(e); }
			}
		});
		
		playback.start();
	}
	
	protected static Player createPlayer(GameStatus gs, int index) {
		Player out = null;
		
		assert(playlist != null);
		String filename = path + playlist.get(gs).get(index) + extension;

		try {
			out = new Player(new BufferedInputStream(new FileInputStream(
					filename)));
		} catch (Exception e) {
			System.out.println(e);
		}

		return out;
	}
	
	// fields
	protected PlaybackStatus ps;
	protected GameStatus gs;
	protected Thread playback;
	
	// data
	private static final List<String> menu = Arrays.asList("menu-1", "menu-2");
	private static final List<String> playing = Arrays.asList("game-1", "game-2", "game-3", "game-4");
	private static final String path = "Music/";
	private static final String extension = ".mp3";
	private static HashMap<GameStatus, List<String>> playlist = null;
	
	// singleton stuff
	private static Music instance;
	private static void setup() { instance = new Music(); }
}


enum PlaybackStatus {
	PLAY, PAUSE, CLOSE
}

enum GameStatus {
	MENU("menu"), PLAYING("game"), CREDITS("credits");
	
	private String filename;
	private GameStatus(String s) {
		filename = s;
	}
	
	@Override
	public String toString() {
		return filename;
	}
	
	public static GameStatus fromString(String in) {
		if(in.toLowerCase().equals("menu")) return MENU;
		if(in.toLowerCase().equals("game")) return PLAYING;
		if(in.toLowerCase().equals("menu")) return CREDITS;
		
		return null;
	}
}
