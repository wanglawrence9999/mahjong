package jp.sourceforge.andjong;

/**
 * プレイヤーの情報を管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Player {
	/** AI */
	public AI ai;

	/** 手牌 */
	public Tehai tehai = new Tehai();

	/** 河 */
	public Kawa kawa = new Kawa();

	/** 自風 */
	public int jikaze;

	/** 点棒 */
	public int tenbou;

	/**
	 * プレイヤーを初期化する。
	 */
	public Player() {

	}

	/**
	 * プレイヤーを初期化する。
	 * 
	 * @param ai
	 *            AI
	 */
	public Player(AI ai) {
		this.ai = ai;
	}
}
