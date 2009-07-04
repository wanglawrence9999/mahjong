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

	public final static int EVENTID_TSUMO = 0x00000001;
	public final static int EVENTID_SUTEHAI = 0x00000002;
	public final static int EVENTID_RON = 0x00000003;
	public final static int EVENTID_NAGASHI = 0x00000004;

	public final static int EVENTID_KYOKUSTART = 0x00010000;

	public Info(Game game) {
		this.game = game;
	}

	public void copyTsumoHai(Hai tsumoHai) {
		tsumoHai.copy(game.tsumoHai);
	}

	public void copySuteHai(Hai suteHai) {
		suteHai.copy(game.suteHai);
	}

	public void copyTehai(Tehai tehai, int cha) {
		tehai.init();
		Tehai tehaiSource = game.activePlayer.players[cha].tehai;

		if (cha == 0) {
			tehaiSource.copyJyunTehai(tehai);
		}
		tehaiSource.copyMinshun(tehai);
		tehaiSource.copyMinkou(tehai);
		tehaiSource.copyMinkan(tehai);
		tehaiSource.copyAnkan(tehai);
	}
}
