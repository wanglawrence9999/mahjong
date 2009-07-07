package jp.sourceforge.andjong;

public class InfoUI extends Info {
	public InfoUI(Game game) {
		super(game);
	}

	public Hai[] getDoraAll() {
		return game.getYama().getDoraAll();
	}
}
