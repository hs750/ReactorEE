package ReactorEE.sound;

public enum GameStatus {
	MENU("menu"), PLAYING("game"), CREDITS("credits");
	
	private String filename;
	private GameStatus(String s) {
		filename = s;
	}
	
	@Override
	public String toString() {
		return filename;
	}
}
