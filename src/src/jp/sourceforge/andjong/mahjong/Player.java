package jp.sourceforge.andjong.mahjong;

/**
 * プレイヤーを管理するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
class Player {
	/** EventIF */
	private EventIf eventIf;

	/**
	 * EventIFを取得します。。
	 *
	 * @return EventIF
	 */
	EventIf getEventIf() {
		return eventIf;
	}

	/** 手牌 */
	private Tehai m_tehai = new Tehai();

	/**
	 * 手牌を取得します。
	 *
	 * @return 手牌
	 */
	Tehai getTehai() {
		return m_tehai;
	}

	/** 河 */
	private Hou kawa = new Hou();

	/**
	 * 河を取得します。
	 *
	 * @return 河
	 */
	Hou getKawa() {
		return kawa;
	}

	/** 自風 */
	private int jikaze;

	/**
	 * 自風を取得します。
	 *
	 * @return 自風
	 */
	int getJikaze() {
		return jikaze;
	}

	/**
	 * 自風を設定します。
	 *
	 * @param jikaze
	 *            自風
	 */
	void setJikaze(int jikaze) {
		this.jikaze = jikaze;
	}

	/** 点棒 */
	private int tenbou;

	/**
	 * 点棒を取得します。
	 *
	 * @return
	 */
	int getTenbou() {
		return tenbou;
	}

	/**
	 * 点棒を設定します。
	 *
	 * @param tenbou
	 *            点棒
	 */
	void setTenbou(int tenbou) {
		this.tenbou = tenbou;
	}

	/**
	 * 点棒を増やします。
	 *
	 * @param ten
	 *            点
	 */
	void increaseTenbou(int ten) {
		tenbou += ten;
	}

	/**
	 * 点棒を減らします。
	 *
	 * @param ten
	 *            点
	 */
	void reduceTenbou(int ten) {
		tenbou -= ten;
	}

	/** リーチ */
	private boolean reach;

	/**
	 * リーチを取得します。
	 *
	 * @return リーチ
	 */
	boolean isReach() {
		return reach;
	}

	/**
	 * リーチを設定します。
	 *
	 * @param reach
	 *            リーチ
	 */
	void setReach(boolean reach) {
		this.reach = reach;
	}

	/** ダブルリーチ */
	private boolean m_doubleReach;

	/**
	 * ダブルリーチを取得します。
	 *
	 * @return ダブルリーチ
	 */
	boolean isDoubleReach() {
		return m_doubleReach;
	}

	/**
	 * ダブルリーチを設定します。
	 *
	 * @param reach
	 *            ダブルリーチ
	 */
	void setDoubleReach(boolean a_doubleReach) {
		this.m_doubleReach = a_doubleReach;
	}

	private int m_suteHaisCount;
	void setSuteHaisCount(int a_suteHaisCount) {
		this.m_suteHaisCount = a_suteHaisCount;
	}
	int getSuteHaisCount() {
		return m_suteHaisCount;
	}

	private CountFormat m_countFormat = new CountFormat();

	boolean isTenpai() {
		if (reach) {
			return true;
		}

		Hai addHai;
		for (int id = 0; id < Hai.ID_ITEM_MAX; id++) {
			addHai = new Hai(id);
			m_countFormat.setCountFormat(m_tehai, addHai);
			if (m_countFormat.getCombis(null) > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * プレイヤーを初期化する。
	 *
	 * @param eventIf
	 *            EventIF
	 */
	Player(EventIf eventIf) {
		this.eventIf = eventIf;
	}

	/**
	 * プレイヤーを初期化します。
	 */
	void init() {
		// 手牌を初期化します。
		m_tehai.initialize();

		// 河を初期化します。
		kawa.initialize();

		// リーチを初期化します。
		reach = false;

		m_ippatsu = false;
		m_doubleReach = false;
	}

	public void setIppatsu(boolean a_ippatsu) {
		this.m_ippatsu = a_ippatsu;
	}

	public boolean isIppatsu() {
		return m_ippatsu;
	}

	private boolean m_ippatsu;
}
