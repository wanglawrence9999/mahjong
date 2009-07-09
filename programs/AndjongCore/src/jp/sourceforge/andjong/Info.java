package jp.sourceforge.andjong;

import jp.sourceforge.andjong.Game.SAI;
import jp.sourceforge.andjong.Tehai.Combi;

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
		return game.getSai();
	}
	
	public Hai[] getDora() {
		return game.getYama().getDora();
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

	public Hai getTsumoHai() {
		return new Hai(game.tsumoHai);
	}

	public Hai getSuteHai() {
		return new Hai(game.suteHai);
	}

	public void copyTehai(Tehai tehai, int cha) {
		if (cha == 0) {
			tehai.copy((game.getActivePlayer()).players[cha].tehai, true);
		} else {
			tehai.copy((game.getActivePlayer()).players[cha].tehai, false);
		}
	}

	public void copyKawa(Kawa kawa, int cha) {
		kawa.copy((game.getActivePlayer()).players[cha].kawa);
	}

	public int getJikaze() {
		return (game.getActivePlayer()).getJikaze();
	}

	public int getAgariScore(Tehai tehai, Hai addHai, int combisCount,
			Combi[] combis) {
		return game.getAgariScore(tehai, addHai, combisCount, combis);
	}
}
