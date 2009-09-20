package jp.sourceforge.andjong.mahjong;

public class InfoUI extends Info {
	public InfoUI(Game game) {
		super(game);
	}

	public Hai[] getDoraAll() {
		return game.getYama().getUraDoraHais();
	}
}
