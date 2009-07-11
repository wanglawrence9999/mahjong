package jp.sourceforge.andjong;

/**
 * プレイヤーの情報を管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Player {
	/** EventIF */
	public EventIF eventIf;

	/** 手牌 */
	public Tehai tehai = new Tehai();

	/** 河 */
	public Kawa kawa = new Kawa();

	/** 自風 */
	private int jikaze;

	public int getJikaze() {
		return jikaze;
	}

	public void setJikaze(int jikaze) {
		this.jikaze = jikaze;
	}

	/** 点棒 */
	public int tenbou;

	/**
	 * プレイヤーを初期化する。
	 * 
	 * @param eventIf
	 *            EventIF
	 */
	public Player(EventIF eventIf) {
		this.eventIf = eventIf;
	}

	public void init() {
		tehai.init();
		kawa.init();
	}
}
