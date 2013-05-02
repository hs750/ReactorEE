package ReactorEE.sound;

import java.util.*;
import java.util.Arrays;

/**
 * Created by andrei on 23/04/2013
 */
public class Sound {
	public static final String DEFAULT_BUTTON_CLICK = "bs1";
	public static final String DEFAULT_PLAY_BUTTON_CLICK = "bs2";
	public static final String DEFAULT_MENU_BUTTON_CLICK = "bm2";
    private Sound() {
        if(instance != null) { System.out.println("CRITICAL: Sound constructor called more than once"); new Throwable().getStackTrace(); System.exit(1); }

        library = new HashMap<String, List<String>>();
        library.put("menu", Arrays.asList("Music/game-3.mp3","Music/game-3.mp3"));
        library.put("game", Arrays.asList("Music/menu-1.mp3","Music/menu-1.mp3"));
        library.put("bm1", Arrays.asList("Music/button-med.mp3"));
        library.put("bm2", Arrays.asList("Music/button-med2.mp3"));
        library.put("bs1", Arrays.asList("Music/button-short.mp3"));
        library.put("bs2", Arrays.asList("Music/button-short2.mp3"));
        //library.put("", Arrays.asList("Music/.mp3"));
        library.put("button-test", Arrays.asList("Music/button-short.mp3", "Music/button-short2.mp3"));
    }

    // API
    public static void play(String name) {
        if(instance == null) setup();

        if(instance.library.get(name) == null) {
            if(isDebug) System.out.println("DEBUG Playlist " + name + " not found");
        }

        else if(instance.library.get(name).size() == 1)
            new MP3Player(instance.library.get(name).get(0)).play();

        else {
            stopBackgroundMusic();
            instance.bgName = name;
            instance.bgThread = new MP3Player(instance.library.get(name)).play();
        }
    }

    public static void stopBackgroundMusic() {
        if(instance == null) setup();

        if(instance.bgThread != null) instance.bgThread.interrupt();
        instance.bgName = "";
    }

    // implementation
    protected HashMap<String, List<String>> library;

    protected Thread bgThread = null;
    protected String bgName = "";

    private static Sound instance = null;
    private static void setup() {
        instance = new Sound();
    }

    private static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
}