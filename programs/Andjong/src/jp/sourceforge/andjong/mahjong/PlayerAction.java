package jp.sourceforge.andjong.mahjong;

public class PlayerAction {
	public static final int STATE_NONE = 0;
	public static final int STATE_SUTEHAI_SELECT = 1;
	public static final int STATE_RON_SELECT= 2;
	public static final int STATE_TSUMO_SELECT= 3;
	public static final int STATE_ACTION_WAIT = 4;

	private int mState = STATE_NONE;

	/** 捨牌のインデックス */
	private int mSutehaiIdx;

	/** アクション要求 */
	//private boolean mActionRequest;

	/** ロンが可能 */
	private boolean mValidRon;

	/** ツモが可能 */
	private boolean mValidTsumo;

	/** ポンが可能 */
	private boolean mValidPon;

	/** メニュー選択 */
	private int mMenuSelect;

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
		return mValidRon | mValidTsumo | mValidPon;
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

	public synchronized void setMenuSelect(int menuSelect) {
		this.mMenuSelect = menuSelect;
	}

	public synchronized int getMenuSelect() {
		return mMenuSelect;
	}
}
