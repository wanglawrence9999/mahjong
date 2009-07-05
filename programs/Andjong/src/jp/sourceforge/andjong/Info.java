package jp.sourceforge.andjong;

import jp.sourceforge.andjong.Tehai.Combi;

/**
 * AIやUIにGameクラスの情報を提供するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Info {
	private Game game;

	private int sutehaiIdx;

	public int getSutehaiIdx() {
		return sutehaiIdx;
	}

	public void setSutehaiIdx(int sutehaiIdx) {
		this.sutehaiIdx = sutehaiIdx;
	}

	public final static int EVENTID_TSUMO = 0x00000001;
	public final static int EVENTID_SUTEHAI = 0x00000002;
	public final static int EVENTID_RON = 0x00000003;
	public final static int EVENTID_NAGASHI = 0x00000004;
	public final static int EVENTID_TSUMOAGARI = 0x00000005;

	public final static int EVENTID_KYOKUSTART = 0x00010000;

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
