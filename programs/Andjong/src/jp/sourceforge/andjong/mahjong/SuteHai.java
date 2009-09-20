package jp.sourceforge.andjong.mahjong;

/**
 * 捨牌を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class SuteHai extends Hai {
	/** 鳴きフラグ */
	private boolean naki;

	/** リーチフラグ */
	private boolean reach;

	/** 手出しフラグ */
	private boolean tedashi;

	/**
	 * 捨牌を作成する。
	 */
	public SuteHai() {
		super();
	}

	/**
	 * IDから捨牌を作成する。
	 *
	 * @param id
	 *            ID
	 */
	public SuteHai(int id) {
		super(id);
	}

	/**
	 * 牌から捨牌を作成する。
	 *
	 * @param hai
	 *            牌
	 */
	public SuteHai(Hai hai) {
		super(hai);
	}

	/**
	 * 捨牌をコピーする。
	 *
	 * @param destSuteHai
	 *            コピー先の捨牌
	 * @param srcSuteHai
	 *            コピー元の捨牌
	 */
	public static void copy(SuteHai destSuteHai, SuteHai srcSuteHai) {
		Hai.copy(destSuteHai, srcSuteHai);
		destSuteHai.naki = srcSuteHai.naki;
		destSuteHai.reach = srcSuteHai.reach;
		destSuteHai.tedashi = srcSuteHai.tedashi;
	}

	/**
	 * 鳴きフラグを設定する。
	 *
	 * @param naki
	 *            鳴きフラグ
	 */
	public void setNaki(boolean naki) {
		this.naki = naki;
	}

	/**
	 * 鳴きフラグを取得する。
	 *
	 * @return 鳴きフラグ
	 */
	public boolean isNaki() {
		return naki;
	}

	/**
	 * リーチフラグを設定する。
	 *
	 * @param reach
	 *            リーチフラグ
	 */
	public void setReach(boolean reach) {
		this.reach = reach;
	}

	/**
	 * リーチフラグを取得する。
	 *
	 * @return リーチフラグ
	 */
	public boolean isReach() {
		return reach;
	}

	/**
	 * 手出しフラグを設定する。
	 *
	 * @param tedashi
	 *            手出しフラグ
	 */
	public void setTedashi(boolean tedashi) {
		this.tedashi = tedashi;
	}

	/**
	 * 手出しフラグを取得する。
	 *
	 * @return 手出しフラグ
	 */
	public boolean isTedashi() {
		return tedashi;
	}
}
