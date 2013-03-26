package ReactorEE.sound;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javazoom.jl.player.Player;

public class Music {	
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
		if(instance == null) return;
		instance.ps = PlaybackStatus.PAUSE;
	}
	
	/**
	 * Enter <pre>Music.kill();</pre> to clear memory and stop the music. Any command will reinitialize the class.
	 */
	public static void kill() {
		if(instance == null) return;
		instance.ps = PlaybackStatus.CLOSE;
	}
	
	/**
	 * Valid: "menu" "game" (maybe "credits" in the future)
	 * @param newGameContext
	 */
	public static void changeGameContext(String newGameContext) {
		if (instance == null) return;

		GameStatus new_gs = GameStatus.fromString(newGameContext);
		if (new_gs != null)
			instance.gs = new_gs;
		else
			System.err.println("Incorrect Music.changeGameContext string!");
	}
	
	// implementation
	private Music() {
		ps = PlaybackStatus.PAUSE;
		gs = GameStatus.MENU;
		
		if(playlist == null) {
			playlist = new HashMap<GameStatus, List<String>>();
			playlist.put(GameStatus.MENU, menu);
			playlist.put(GameStatus.PLAYING, playing);
			playlist.put(GameStatus.CREDITS, credits);
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
					index = 0;

					player.close();
					player = createPlayer(gs, index);
				}
				else if(trackEnded) {
					trackEnded = false;
					index = (index+1) % playlist.get(gs).size();
					
					player.close();
					player = createPlayer(gs, index);
				}
				try { trackEnded = !player.play(8); } catch (Exception e) { System.out.println(e); }
				break;
			case PAUSE:
				try { Thread.sleep(500); } catch (InterruptedException e) { System.out.println(e); }
				break;
			}
		}
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
	private static final List<String> credits = Arrays.asList();
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
