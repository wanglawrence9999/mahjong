package jp.sourceforge.andjong.mahjong;

/**
 * プレイヤーに提供する情報を管理するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
public class Info {
	/** Game */
	protected Mahjong game;

	/**
	 * インスタンスを初期化する。
	 *
	 * @param game
	 *            Game
	 */
	public Info(Mahjong game) {
		this.game = game;
	}

	/**
	 * サイコロの配列を取得する。
	 *
	 * @return サイコロの配列
	 */
	public Sai[] getSais() {
		return game.getSais();
	}

	/**
	 * 表ドラ、槓ドラの配列を取得する。
	 *
	 * @return 表ドラ、槓ドラの配列
	 */
	public Hai[] getDoraHais() {
		return game.getDoras();
	}

	/**
	 * 自風を取得する。
	 *
	 * @return 自風
	 */
	public int getJikaze() {
		return game.getJikaze();
	}

	public void copyTehai(Tehai tehai) {
		game.copyTehai(tehai, game.getJikaze());
	}

	/**
	 * 手牌をコピーする。
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
	 * 河をコピーする。
	 *
	 * @param kawa
	 *            河
	 * @param kaze
	 *            風
	 */
	public void copyKawa(Kawa kawa, int kaze) {
		game.copyKawa(kawa, kaze);
	}

	/**
	 * ツモ牌を取得する。
	 *
	 * @return ツモ牌
	 */
	public Hai getTsumoHai() {
		return new Hai(game.getTsumoHai());
	}

	/**
	 * 捨牌を取得する。
	 *
	 * @return 捨牌
	 */
	public Hai getSuteHai() {
		return new Hai(game.getSuteHai());
	}

	public int getAgariScore() {
		return 0;
	}

	/**
	 * あがり点を取得する。
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

	public boolean isReach() {
		return game.isReach(game.getJikaze());
	}

	/**
	 * リーチを取得する。
	 *
	 * @param kaze
	 *            風
	 * @return リーチ
	 */
	public boolean isReach(int kaze) {
		return game.isReach(kaze);
	}

	/**
	 * ツモの残り数を取得する。
	 *
	 * @return ツモの残り数
	 */
	public int getTsumoRemain() {
		return game.getTsumoRemain();
	}

	/**
	 * 局を取得する。
	 *
	 * @return 局
	 */
	public int getkyoku() {
		return game.getkyoku();
	}

	/**
	 * 名前を取得する。
	 *
	 * @param kaze
	 *            風
	 * @return 名前
	 */
	public String getName(int kaze) {
		return game.getName(kaze);
	}

	/**
	 * 本場を取得する。
	 *
	 * @return 本場
	 */
	public int getHonba() {
		return game.getHonba();
	}

	/**
	 * リーチ棒の数を取得する。
	 *
	 * @return リーチ棒の数
	 */
	public int getReachbou() {
		return game.getReachbou();
	}

	/**
	 * 点棒を取得する。
	 *
	 * @param kaze
	 *            風
	 * @return 点棒
	 */
	public int getTenbou(int kaze) {
		return game.getTenbou(kaze);
	}

	public String[] getYakuName(Tehai tehai, Hai addHai){
		return game.getYakuName(tehai, addHai);
	}

	public void setSutehaiIdx(
			int mSutehaiIdx) {
		this.mSutehaiIdx = mSutehaiIdx;
	}

	public int getSutehaiIdx() {
		return mSutehaiIdx;
	}

	private int mSutehaiIdx;

	{
		setSutehaiIdx(Integer.MAX_VALUE);
	}
}
