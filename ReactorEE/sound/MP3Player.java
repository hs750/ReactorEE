package ReactorEE.sound;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.List;

public class MP3Player {
    protected Player player;
    protected List<String> tracks;
    Thread thread = new Thread() {
        public void interrupt() {
            player.close();
            if(isDebug) System.out.println("DEBUG Killed thread " + this);
            Thread.currentThread().interrupt();
        }
        public void run() {
            try {
                if(tracks == null) {
                    // System.out.println("[MP3] Playing a track");
                    player.play();
                }
                else {
                    // System.out.println("[MP3] Playing a playlist");
                    boolean hasEnded = false;
                    int index = 0;

                    while(true) {
                        // System.out.println("[MP3] Playing " + tracks.get(index));

                        // 1 frame ~= 28 milliseconds
                        while(!hasEnded) hasEnded = !player.play(8);

                        player.close();
                        hasEnded = false;

                        index = (index+1) % tracks.size();
                        player = createPlayer(tracks.get(index));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public MP3Player(final String filename) {
        player = createPlayer(filename);
    }

    public MP3Player(List<String> tracks) {
        // doesn't create a new list, just a reference to 'tracks'
        this.tracks = tracks;
        player =createPlayer(this.tracks.get(0));
    }

    public Thread play() {

        thread.start();
        return thread;
    }


    private Player createPlayer(String filename) {
        Player out = null;
        try {
            out = new Player(new BufferedInputStream(new FileInputStream(filename)));
        } catch (Exception e) {
            if(isDebug) System.out.println("DEBUG MP3 track not found");
            e.printStackTrace();
        }
        return out;
    }

    private static boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
            getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
}
