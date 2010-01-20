package jp.sourceforge.andjong.mahjong;

/**
 * イベントIFを管理するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
public interface EventIf {
	/** イベントID */
	enum EventId {
		/** ゲームの開始 */
		START_GAME,
		/** 局の開始 */
		START_KYOKU,
		/** ツモ */
		TSUMO,
		/** 捨牌の選択 */
		SELECT_SUTEHAI,
		/** 捨牌 */
		SUTEHAI,
		/** リーチ */
		REACH,
		/** ポン */
		PON,
		/** チー(左) */
		CHII_LEFT,
		/** チー(中央) */
		CHII_CENTER,
		/** チー(右) */
		CHII_RIGHT,
		/** 大明槓 */
		DAIMINKAN,
		/** 加槓 */
		KAN,
		/** 暗槓 */
		ANKAN,
		/** ツモあがり */
		TSUMO_AGARI,
		/** ロンあがり */
		RON_AGARI,
		/** 流し */
		NAGASHI,
		/** 流局 */
		RYUUKYOKU,
		/** 局の終了 */
		END_KYOKU,
		/** ゲームの終了 */
		END_GAME,

		/** ロンのチェック */
		RON_CHECK,

		/** 理牌待ち */
		UI_WAIT_RIHAI,
		/** 進行待ち */
		UI_WAIT_PROGRESS,
		/** プレイヤーアクションの入力 */
		UI_INPUT_PLAYER_ACTION
	}

	/** 風(東) */
	public final static int KAZE_TON = 0;
	/** 風(南) */
	public final static int KAZE_NAN = 1;
	/** 風(西) */
	public final static int KAZE_SHA = 2;
	/** 風(北) */
	public final static int KAZE_PE = 3;
	/** 風の種類の個数 */
	public final static int KAZE_KIND_NUM = KAZE_PE + 1;
	/** 風(なし) */
	public final static int KAZE_NONE = 4;

	/**
	 * イベントIFの名前を取得する。
	 *
	 * @return イベントIFの名前
	 */
	public String getName();

	/**
	 * 捨牌のインデックスを取得します。
	 *
	 * @return 捨牌のインデックス
	 */
	int getISutehai();

	/**
	 * イベントを処理する。
	 *
	 * @param a_eventId
	 *            イベントID
	 * @param a_kazeFrom
	 *            イベントを発行した風
	 * @param a_kazeTo
	 *            イベントの対象となった風
	 * @return イベントID
	 */
	EventId event(EventId a_eventId, int a_kazeFrom, int a_kazeTo);
}
