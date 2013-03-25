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
		
		char x = 'y'; Scanner sc = new Scanner(System.in);
		while(x != 'q') {
			x = sc.next().toCharArray()[0];
			if(x == 'a') Music.play();
			if(x == 's') Music.pause();
			if(x == 'd') Music.kill();
			if(x == 'f') Music.changeGameContext("game");
			if(x == 'g') Music.changeGameContext("menu");
		}
	}
	
	// API
	/**
	 * Enter <pre>Music.play();</pre> to play/resume the current track. Make sure to change game context with <pre>Music.changeGameContext(String)</pre>
	 * The default value for game context is "menu"
	 */
	public static void play() {
		if(instance == null) setup();
		instance.ps = PlaybackStatus.PLAY;
	}
	
	/**
	 * Enter <pre>Music.pause();</pre> to
	 */
	public static void pause() {
		if(instance == null) setup();
		instance.ps = PlaybackStatus.PAUSE;
	}
	
	/**
	 * Enter <pre>Music.kill();</pre> to clear memory and stop the music. Any command will reinitialize the class.
	 */
	public static void kill() {
		if(instance == null) setup();
		instance.ps = PlaybackStatus.CLOSE;
	}
	
	/**
	 * Valid: "menu" "game" (maybe "credits" in the future)
	 * @param newGameContext
	 */
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
		
		playback = new Thread(new Runnable() { public void run(){ instance.playback(); } });
		playback.start();
	}
	
	protected void playback() {
		Player player = createPlayer(gs, 0);
		boolean trackEnded = false;
		GameStatus oldGS = gs;
		int index = 0;
		boolean _end = true;

		while(_end) {
			switch(ps) {
			case CLOSE:
				instance = null;
				_end = false;
				break;
			case PLAY:
				if(gs != oldGS) {
					oldGS = gs;
					trackEnded = false;
					index = 0; 			System.out.println("hi 1");

					player.close();
					player = createPlayer(gs, index);
				}
				else if(trackEnded) {
					trackEnded = false;
					index = (index+1) % playlist.get(gs).size();
					
					player.close();			System.out.println("hi 2");
					player = createPlayer(gs, index);
				}
				try { trackEnded = !player.play(8); } catch (Exception e) { System.out.println(e); } System.out.println("hi 3");
				break;
			case PAUSE:
				try { Thread.sleep(500); } catch (InterruptedException e) { System.out.println(e); } System.out.println("hi 4");
				break;
			}
		}
		
		while(true) {
			if(ps == PlaybackStatus.CLOSE) {
				instance = null;
				return;
			} else if(ps == PlaybackStatus.PLAY) { 
				if(gs != oldGS) {
					player = createPlayer(gs,0);
					oldGS = gs;
					trackEnded = false;
					index = 0;
				} else if(trackEnded) {
					int size = playlist.get(gs).size();
					player = createPlayer(gs, (index+1) % size);
					trackEnded = false;
				}
				else {
					try { trackEnded = player.play(8); } catch (Exception e) { System.out.println(e); }
				}
			} else {
				try { Thread.sleep(500); } catch (InterruptedException e) { System.out.println(e); }
			}
		} //end while(true)
	}
	
	protected static Player createPlayer(GameStatus gs, int index) {
		Player out = null;
		
		assert(playlist != null);
		String filename = path + playlist.get(gs).get(index) + extension;

		try {
			out = new Player(new BufferedInputStream(new FileInputStream(filename)));
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
		if(in.toLowerCase().equals("credits")) return CREDITS;
		
		return null;
	}
}
