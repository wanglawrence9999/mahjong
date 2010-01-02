package jp.sourceforge.andjong;

import android.content.res.Resources;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.Kawa;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.Tehai;
import jp.sourceforge.andjong.mahjong.EventIf.EventId;

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
	/** 結果 */
	public static final int STATE_RESULT = 9;

	/** 状態 */
	int m_state = STATE_INIT_WAIT;

	/** 局の文字列 */
	private String m_kyokuString = null;

	/** リーチ棒の数 */
	private int m_reachbou = 0;

	/** 本場 */
	private int m_honba = 0;

	/** 起家 */
	private int m_chiicha = 0;

	/** 手牌から捨てた牌のインデックス */
	private int m_iSkip = 0;

	/**
	 * 局の文字列を設定する。
	 *
	 * @param a_kyoku
	 *            局
	 */
	public synchronized void setKyokuString(Resources a_resources, int a_kyoku) {
		if (a_kyoku > Mahjong.KYOKU_TON_4) {
			m_kyokuString = null;
			return;
		}

		String[] kyokuStrings = a_resources.getStringArray(R.array.kyoku);
		m_kyokuString = kyokuStrings[a_kyoku];
	}

	/**
	 * 局の文字列を取得する。
	 *
	 * @return 局の文字列
	 */
	public synchronized String getKyokuString() {
		return m_kyokuString;
	}

	/**
	 * リーチ棒の数を設定する。
	 *
	 * @param a_reachbou
	 *            リーチ棒の数
	 */
	public synchronized void setReachbou(int a_reachbou) {
		this.m_reachbou = a_reachbou;
	}

	/**
	 * リーチ棒の数を取得する。
	 *
	 * @return リーチ棒の数
	 */
	public synchronized int getReachbou() {
		return m_reachbou;
	}

	/**
	 * 本場を設定する。
	 *
	 * @param a_honba
	 *            本場
	 */
	public synchronized void setHonba(int a_honba) {
		this.m_honba = a_honba;
	}

	/**
	 * 本場を取得する。
	 *
	 * @return 本場
	 */
	public synchronized int getHonba() {
		return m_honba;
	}

	/**
	 * 起家を設定する。
	 *
	 * @param a_chiicha
	 *            起家
	 */
	public synchronized void setChiicha(int a_chiicha) {
		this.m_chiicha = a_chiicha;
	}

	/**
	 * 起家を取得する。
	 *
	 * @return 起家
	 */
	public synchronized int getChiicha() {
		return m_chiicha;
	}

	/**
	 * 手牌から捨てた牌のインデックスを設定する。
	 *
	 * @param a_iSkip
	 *            手牌から捨てた牌のインデックス
	 */
	public synchronized void setSkipIdx(int a_iSkip) {
		this.m_iSkip = a_iSkip;
	}

	/**
	 * 手牌から捨てた牌のインデックスを取得する。
	 *
	 * @return 手牌から捨てた牌のインデックス
	 */
	public synchronized int getSkipIdx() {
		return m_iSkip;
	}

	/**
	 * 状態を設定する。
	 *
	 * @param m_state
	 *            状態
	 */
	synchronized void setState(int m_state) {
		this.m_state = m_state;
	}

	/**
	 * 状態を取得する。
	 *
	 * @return 状態
	 */
	synchronized int getState() {
		return m_state;
	}

	public class PlayerInfo {
		/** 手牌 */
		Tehai m_tehai = new Tehai();
		/** 河 */
		Kawa m_kawa = new Kawa();
		/** ツモ牌 */
		Hai m_tsumoHai;
		/** 点棒 */
		int m_tenbo;
	}

	/** プレイヤー情報 */
	PlayerInfo m_playerInfos[] = new PlayerInfo[4];

	/** デバッグフラグ */
	boolean m_isDebug = false;

	/** イベントID */
	EventId m_eid;

	{
		for (int i = 0; i < 4; i++) {
			m_playerInfos[i] = new PlayerInfo();
		}
	}
}
