package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.mahjong.EventIf.EventId;

public class PlayerAction {
	public static final int STATE_NONE = 0;
	public static final int STATE_SUTEHAI_SELECT = 1;
	public static final int STATE_RON_SELECT= 2;
	public static final int STATE_TSUMO_SELECT= 3;
	public static final int STATE_ACTION_WAIT = 4;
	public static final int STATE_CHII_SELECT = 5;

	EventId m_chiiEventId;

	public EventId getChiiEventId() {
		return m_chiiEventId;
	}

	public void setChiiEventId(EventId a_chiiEventId) {
		m_chiiEventId = a_chiiEventId;
	}

	private int mState = STATE_NONE;

	/** 捨牌のインデックス */
	private int mSutehaiIdx;

	/** ロンが可能 */
	private boolean mValidRon;

	/** ツモが可能 */
	private boolean mValidTsumo;

	/** ポンが可能 */
	private boolean mValidPon;

	/** メニュー選択 */
	private int mMenuSelect;

	public int m_indexs[];
	public int m_indexNum;

	/**
	 * コンストラクター
	 */
	public PlayerAction() {
		super();

		init();
	}

	/**
	 * 初期化する。
	 */
	public synchronized void init() {
		mState = STATE_NONE;
		mSutehaiIdx = Integer.MAX_VALUE;
		mValidRon = false;
		mValidTsumo = false;
		mValidPon = false;
		m_validReach = false;

		m_validChiiLeft = false;
		m_validChiiCenter = false;
		m_validChiiRight = false;

		setMenuSelect(5);
	}

	public synchronized void setState(int state) {
		this.mState = state;
	}

	public synchronized int getState() {
		return mState;
	}

	/**
	 * アクションを待つ。
	 */
	public synchronized void actionWait() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * アクションを通知する。
	 */
	public synchronized void actionNotifyAll() {
		notifyAll();
	}

	/**
	 * 捨牌のインデックスを設定する。
	 *
	 * @param sutehaiIdx
	 *            捨牌のインデックス
	 */
	public synchronized void setSutehaiIdx(int sutehaiIdx) {
		this.mSutehaiIdx = sutehaiIdx;
	}

	/**
	 * 捨牌のインデックスを取得する。
	 *
	 * @return 捨牌のインデックス
	 */
	public synchronized int getSutehaiIdx() {
		return mSutehaiIdx;
	}

	/**
	 * アクション要求を取得する。
	 *
	 * @return
	 */
	public synchronized boolean isActionRequest() {
		return mValidRon | mValidTsumo | mValidPon | m_validReach
				| m_validChiiLeft | m_validChiiCenter | m_validChiiRight;
	}

	/**
	 * ロンが可能かを設定する。
	 *
	 * @param validRon
	 *            可否
	 */
	public synchronized void setValidRon(boolean validRon) {
		this.mValidRon = validRon;
	}

	/**
	 * ロンが可能かを取得する。
	 *
	 * @return 可否
	 */
	public synchronized boolean isValidRon() {
		return mValidRon;
	}

	/**
	 * ツモが可能かを設定する。
	 *
	 * @param validTsumo
	 *            可否
	 */
	public synchronized void setValidTsumo(boolean validTsumo) {
		this.mValidTsumo = validTsumo;
	}

	/**
	 * ツモが可能かを取得する。
	 *
	 * @return 可否
	 */
	public synchronized boolean isValidTsumo() {
		return mValidTsumo;
	}

	/**
	 * ポンが可能かを設定する。
	 *
	 * @param validTsumo
	 *            可否
	 */
	public synchronized void setValidPon(boolean validPon) {
		this.mValidPon = validPon;
	}

	/**
	 * ポンが可能かを取得する。
	 *
	 * @return 可否
	 */
	public synchronized boolean isValidPon() {
		return mValidPon;
	}

	boolean m_validReach;

	public synchronized void setValidReach(boolean a_validReach) {
		this.m_validReach = a_validReach;
	}

	public synchronized boolean isValidReach() {
		return m_validReach;
	}

	public synchronized void setMenuSelect(int menuSelect) {
		this.mMenuSelect = menuSelect;
	}

	public synchronized int getMenuSelect() {
		return mMenuSelect;
	}

	private Hai[] m_sarashiHaiLeft = new Hai[2];
	private Hai[] m_sarashiHaiCenter = new Hai[2];
	private Hai[] m_sarashiHaiRight = new Hai[2];
	private boolean m_validChiiLeft;
	private boolean m_validChiiCenter;
	private boolean m_validChiiRight;

	public synchronized void setValidChiiLeft(boolean a_validChii, Hai[] a_sarashiHai) {
		this.m_validChiiLeft = a_validChii;
		this.m_sarashiHaiLeft = a_sarashiHai;
	}

	public synchronized boolean isValidChiiLeft() {
		return m_validChiiLeft;
	}

	public synchronized Hai[] getSarachiHaiLeft() {
		return m_sarashiHaiLeft;
	}

	public synchronized void setValidChiiCenter(boolean a_validChii, Hai[] a_sarashiHai) {
		this.m_validChiiCenter = a_validChii;
		this.m_sarashiHaiCenter = a_sarashiHai;
	}

	public synchronized boolean isValidChiiCenter() {
		return m_validChiiCenter;
	}

	public synchronized Hai[] getSarachiHaiCenter() {
		return m_sarashiHaiCenter;
	}

	public synchronized void setValidChiiRight(boolean a_validChii, Hai[] a_sarashiHai) {
		this.m_validChiiRight = a_validChii;
		this.m_sarashiHaiRight = a_sarashiHai;
	}

	public synchronized boolean isValidChiiRight() {
		return m_validChiiRight;
	}

	public synchronized Hai[] getSarachiHaiRight() {
		return m_sarashiHaiRight;
	}
}
