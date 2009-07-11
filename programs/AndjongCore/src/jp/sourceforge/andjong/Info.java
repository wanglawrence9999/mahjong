package jp.sourceforge.andjong;

import jp.sourceforge.andjong.Game.SAI;

/**
 * AIやUIにGameクラスの情報を提供するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Info {
	/** ゲームオブジェクト */
	protected Game game;

	public SAI[] getSai() {
		return game.getSais();
	}

	public Hai[] getDora() {
		return game.getYama().getDora();
	}

	/**
	 * 手牌をコピーします。
	 * 
	 * @param tehai
	 *            手牌
	 * @param kaze
	 *            風
	 */
	public void copyTehai(Tehai tehai, int kaze) {
		game.copyTehai(tehai, kaze);
	}

	/**
	 * ツモ牌を取得します。
	 * 
	 * @return ツモ牌
	 */
	public Hai getTsumoHai() {
		return new Hai(game.tsumoHai);
	}

	/**
	 * あがり点を取得します。
	 * 
	 * @param tehai
	 *            手牌
	 * @param addHai
	 *            手牌に追加する牌
	 * @return
	 */
	public int getAgariScore(Tehai tehai, Hai addHai) {
		return game.getAgariScore(tehai, addHai);
	}

	private int sutehaiIdx;

	public int getSutehaiIdx() {
		return sutehaiIdx;
	}

	public void setSutehaiIdx(int sutehaiIdx) {
		this.sutehaiIdx = sutehaiIdx;
	}

	public Info(Game game) {
		this.game = game;
	}

	public Hai getSuteHai() {
		return new Hai(game.suteHai);
	}

	public void copyKawa(Kawa kawa, int kaze) {
		game.copyKawa(kawa, kaze);
	}

	public int getJikaze() {
		return game.getJikaze();
	}
}
