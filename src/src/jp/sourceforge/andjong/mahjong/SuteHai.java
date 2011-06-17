package jp.sourceforge.andjong.mahjong;

/**
 * 捨牌を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class SuteHai extends Hai {
	/** 鳴きフラグ */
	private boolean m_naki = false;

	/** リーチフラグ */
	private boolean m_reach = false;

	/** 手出しフラグ */
	private boolean m_tedashi = false;

	/**
	 * 捨牌を作成する。
	 */
	public SuteHai() {
		super();
	}

	/**
	 * IDから捨牌を作成する。
	 *
	 * @param a_id
	 *            ID
	 */
	public SuteHai(int a_id) {
		super(a_id);
	}

	/**
	 * 牌から捨牌を作成する。
	 *
	 * @param a_hai
	 *            牌
	 */
	public SuteHai(Hai a_hai) {
		super(a_hai);
	}

	/**
	 * 捨牌をコピーする。
	 *
	 * @param a_dest
	 *            コピー先の捨牌
	 * @param a_src
	 *            コピー元の捨牌
	 */
	public static void copy(SuteHai a_dest, SuteHai a_src) {
		Hai.copy(a_dest, a_src);
		a_dest.m_naki = a_src.m_naki;
		a_dest.m_reach = a_src.m_reach;
		a_dest.m_tedashi = a_src.m_tedashi;
	}

	/**
	 * 捨牌をコピーする。
	 *
	 * @param a_dest
	 *            コピー先の捨牌
	 * @param a_src
	 *            コピー元の捨牌
	 */
	public static void copy(SuteHai a_dest, Hai a_src) {
		Hai.copy(a_dest, a_src);
		a_dest.m_naki = false;
		a_dest.m_reach = false;
		a_dest.m_tedashi = false;
	}

	/**
	 * 鳴きフラグを設定する。
	 *
	 * @param a_naki
	 *            鳴きフラグ
	 */
	public void setNaki(boolean a_naki) {
		this.m_naki = a_naki;
	}

	/**
	 * 鳴きフラグを取得する。
	 *
	 * @return 鳴きフラグ
	 */
	public boolean isNaki() {
		return m_naki;
	}

	/**
	 * リーチフラグを設定する。
	 *
	 * @param a_reach
	 *            リーチフラグ
	 */
	public void setReach(boolean a_reach) {
		this.m_reach = a_reach;
	}

	/**
	 * リーチフラグを取得する。
	 *
	 * @return リーチフラグ
	 */
	public boolean isReach() {
		return m_reach;
	}

	/**
	 * 手出しフラグを設定する。
	 *
	 * @param a_tedashi
	 *            手出しフラグ
	 */
	public void setTedashi(boolean a_tedashi) {
		this.m_tedashi = a_tedashi;
	}

	/**
	 * 手出しフラグを取得する。
	 *
	 * @return 手出しフラグ
	 */
	public boolean isTedashi() {
		return m_tedashi;
	}
}
