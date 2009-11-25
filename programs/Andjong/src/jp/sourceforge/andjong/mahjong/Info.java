package jp.sourceforge.andjong.mahjong;

import android.R.integer;

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
	 * インスタンスを初期化します。
	 *
	 * @param game
	 *            Game
	 */
	public Info(Mahjong game) {
		this.game = game;
	}

	/**
	 * サイコロの配列を取得します。
	 *
	 * @return サイコロの配列
	 */
	public Sai[] getSais() {
		return game.getSais();
	}

	/**
	 * 表ドラ、槓ドラの配列を取得します。
	 *
	 * @return 表ドラ、槓ドラの配列
	 */
	public Hai[] getDoras() {
		return game.getDoras();
	}

	/**
	 * 自風を取得します。
	 *
	 * @return 自風
	 */
	public int getJikaze() {
		return game.getJikaze();
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
	 * 河をコピーします。
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
	 * ツモ牌を取得します。
	 *
	 * @return ツモ牌
	 */
	public Hai getTsumoHai() {
		return new Hai(game.getTsumoHai());
	}

	/**
	 * 捨牌を取得します。
	 *
	 * @return 捨牌
	 */
	public Hai getSuteHai() {
		return new Hai(game.getSuteHai());
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

	/**
	 * リーチを取得します。
	 *
	 * @param kaze
	 *            風
	 * @return リーチ
	 */
	public boolean isReach(int kaze) {
		return game.isReach(kaze);
	}

	/**
	 * ツモの残り数を取得します。
	 *
	 * @return ツモの残り数
	 */
	public int getTsumoRemain() {
		return game.getTsumoRemain();
	}

	/**
	 * 局を取得します。
	 *
	 * @return 局
	 */
	public int getkyoku() {
		return game.getkyoku();
	}

	/**
	 * 名前を取得します。
	 *
	 * @param kaze
	 *            風
	 * @return 名前
	 */
	public String getName(int kaze) {
		return game.getName(kaze);
	}

	/**
	 * 本場を取得します。
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
	 * 点棒を取得します。
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
