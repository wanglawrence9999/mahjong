package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.mahjong.EventIf.EventId;

public class PlayerAction {
	public static final int STATE_NONE = 0;
	public static final int STATE_SUTEHAI_SELECT = 1;
	public static final int STATE_RON_SELECT= 2;
	public static final int STATE_TSUMO_SELECT= 3;
	public static final int STATE_ACTION_WAIT = 4;
	public static final int STATE_CHII_SELECT = 5;
	public static final int STATE_KAN_SELECT = 6;
	public static final int STATE_REACH_SELECT = 7;

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

	private boolean m_dispMenu;

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

		m_validKan = false;
		m_kanNum = 0;
		m_kanSelect = 0;
		m_dispMenu = false;
		m_validDaiMinKan = false;

		m_menuNum = 0;

		setMenuSelect(5);
	}

	private int m_menuNum;

	public synchronized void setMenuNum(int a_menuNum) {
		this.m_menuNum = a_menuNum;
	}

	public synchronized int getMenuNum() {
		return m_menuNum;
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
				| m_validChiiLeft | m_validChiiCenter | m_validChiiRight | m_validKan | m_validDaiMinKan;
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

	private boolean m_validKan;
	private Hai[] m_kanHais = new Hai[3];
	private int m_kanNum = 0;
	private int m_kanSelect = 0;

	public synchronized void setValidKan(boolean a_validKan, Hai[] a_kanHais, int a_kanNum) {
		this.m_validKan = a_validKan;
		this.m_kanHais = a_kanHais;
		this.m_kanNum = a_kanNum;
	}

	public synchronized Hai[] getKanHais() {
		return m_kanHais;
	}

	public synchronized int getKanNum() {
		return m_kanNum;
	}

	private boolean m_validDaiMinKan;

	public synchronized void setValidDaiMinKan(boolean a_validDaiMinKan) {
		this.m_validDaiMinKan = a_validDaiMinKan;
	}

	public synchronized boolean isValidDaiMinKan() {
		return m_validDaiMinKan;
	}

	public synchronized void setKanSelect(int a_kanSelect) {
		this.m_kanSelect = a_kanSelect;
	}

	public synchronized int getKanSelect() {
		return m_kanSelect;
	}

	public synchronized boolean isValidKan() {
		return m_validKan;
	}

	public synchronized void setDispMenu(boolean a_dispMenu) {
		this.m_dispMenu = a_dispMenu;
	}

	public synchronized boolean isDispMenu() {
		return m_dispMenu;
	}

	public void setReachSelect(int a_reachSelect) {
		this.m_reachSelect = a_reachSelect;
	}

	public int getReachSelect() {
		return m_reachSelect;
	}

	private int m_reachSelect;
}
