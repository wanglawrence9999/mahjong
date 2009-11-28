package jp.sourceforge.andjong.mahjong;

public class PlayerAction {
	public static final int STATE_NONE = 0;
	public static final int STATE_ACTION_WAIT = 1;

	private int mState = STATE_NONE;

	/** 捨牌のインデックス */
	private int mSutehaiIdx;

	/** アクション要求 */
	private boolean mActionRequest;

	/**
	 * コンストラクター
	 */
	public PlayerAction() {
		super();

		initSutehaiIdx();
		mActionRequest = false;
	}

	public void setState(int state) {
		this.mState = state;
	}

	public int getState() {
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
	 * 捨牌のインデックスを初期化する。
	 */
	public synchronized void initSutehaiIdx() {
		mSutehaiIdx = Integer.MAX_VALUE;
	}

	/**
	 * アクション要求を設定する。
	 *
	 * @param actionRequest
	 *            アクション要求
	 */
	public synchronized void setActionRequest(boolean actionRequest) {
		this.mActionRequest = actionRequest;
	}

	/**
	 * アクション要求を取得する。
	 *
	 * @return
	 */
	public synchronized boolean isActionRequest() {
		return mActionRequest;
	}
}
