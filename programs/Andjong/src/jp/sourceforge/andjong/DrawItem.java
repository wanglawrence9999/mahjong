package jp.sourceforge.andjong;

import android.content.res.Resources;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.Kawa;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.Tehai;
import jp.sourceforge.andjong.mahjong.EventIf.EID;

/**
 * 描画アイテムを管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class DrawItem {
	/** 初期化待ち */
	public static final int STATE_INIT_WAIT = 0;
	/** 状態なし */
	public static final int STATE_NONE = 1;
	/** 局の開始 */
	public static final int STATE_KYOKU_START = 2;
	/** プレイ */
	public static final int STATE_PLAY = 3;
	/** 理牌待ち */
	public static final int STATE_RIHAI_WAIT = 4;
	/** リーチ */
	public static final int STATE_REACH = 5;
	/** ツモ */
	public static final int STATE_TSUMO = 6;
	/** ロン */
	public static final int STATE_RON = 7;
	/** 流局 */
	public static final int STATE_RYUUKYOKU = 8;

	/** 状態 */
	int mState = STATE_INIT_WAIT;

	/** 局の文字列 */
	private String mKyokuString = null;

	/** リーチ棒の数 */
	private int mReachbou = 0;

	/** 本場 */
	private int mHonba = 0;

	/** 起家 */
	private int mChiicha = 0;

	/** 手牌から捨てた牌のインデックス */
	private int mSkipIdx = 0;

	/**
	 * 局の文字列を設定する。
	 *
	 * @param kyoku
	 *            局
	 */
	public synchronized void setKyokuString(Resources resources, int kyoku) {
		if (kyoku > Mahjong.KYOKU_TON_4) {
			mKyokuString = null;
			return;
		}

		String[] kyokuStrings = resources.getStringArray(R.array.kyoku);
		mKyokuString = kyokuStrings[kyoku];
	}

	/**
	 * 局の文字列を取得する。
	 *
	 * @return 局の文字列
	 */
	public synchronized String getKyokuString() {
		return mKyokuString;
	}

	/**
	 * リーチ棒の数を設定する。
	 *
	 * @param reachbou
	 *            リーチ棒の数
	 */
	public synchronized void setReachbou(int reachbou) {
		this.mReachbou = reachbou;
	}

	/**
	 * リーチ棒の数を取得する。
	 *
	 * @return リーチ棒の数
	 */
	public synchronized int getReachbou() {
		return mReachbou;
	}

	/**
	 * 本場を設定する。
	 *
	 * @param honba
	 *            本場
	 */
	public synchronized void setHonba(int honba) {
		this.mHonba = honba;
	}

	/**
	 * 本場を取得する。
	 *
	 * @return 本場
	 */
	public synchronized int getHonba() {
		return mHonba;
	}

	/**
	 * 起家を設定する。
	 *
	 * @param chiicha
	 *            起家
	 */
	public synchronized void setChiicha(int chiicha) {
		this.mChiicha = chiicha;
	}

	/**
	 * 起家を取得する。
	 *
	 * @return 起家
	 */
	public synchronized int getChiicha() {
		return mChiicha;
	}

	/**
	 * 手牌から捨てた牌のインデックスを設定する。
	 *
	 * @param skipIdx
	 *            手牌から捨てた牌のインデックス
	 */
	public synchronized void setSkipIdx(int skipIdx) {
		this.mSkipIdx = skipIdx;
	}

	/**
	 * 手牌から捨てた牌のインデックスを取得する。
	 *
	 * @return 手牌から捨てた牌のインデックス
	 */
	public synchronized int getSkipIdx() {
		return mSkipIdx;
	}

	/**
	 * 状態を設定する。
	 *
	 * @param state
	 *            状態
	 */
	synchronized void setState(int state) {
		this.mState = state;
	}

	/**
	 * 状態を取得する。
	 *
	 * @return 状態
	 */
	synchronized int getState() {
		return mState;
	}

	public class PlayerInfo {
		/** 手牌 */
		Tehai mTehai = new Tehai();
		Kawa mKawa = new Kawa();
		Hai mTsumoHai;
		/** 点棒 */
		int mTenbo;
	}

	PlayerInfo mPlayerInfos[] = new PlayerInfo[4];

	boolean mIsDebug = false;

	/** イベントID */
	EID eid;

	{
		for (int i = 0; i < 4; i++) {
			mPlayerInfos[i] = new PlayerInfo();
		}
	}
}
