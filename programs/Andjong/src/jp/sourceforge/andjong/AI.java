package jp.sourceforge.andjong;

/**
 * AIを実装するクラスです。<br>
 * TODO オーバーライドしやすい設計にする。
 * 
 * @author Yuji Urushibara
 * 
 */
public class AI {
	private Info info;

	public AI(Info info) {
		this.info = info;
	}

	public final static int EVENTID_TSUMO = 0;
	public final static int EVENTID_SUTEHAI = 1;

	public void event(int eventId) {

	}
}
