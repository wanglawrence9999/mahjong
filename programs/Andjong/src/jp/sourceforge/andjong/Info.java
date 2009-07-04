package jp.sourceforge.andjong;

/**
 * AIやUIにGameクラスの情報を提供するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Info {
	private Game game;
	
	public int sutehaiIdx;

	public Info(Game game) {
		this.game = game;
	}

	public void getTsumoHai(Hai tsumoHai) {
		tsumoHai.copy(game.tsumoHai);
	}
}
