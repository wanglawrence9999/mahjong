package jp.sourceforge.andjong.mahjong;

public interface EventIfTemp {
	/** イベントID */
	enum EID {
		/** 場所決め */
		BASHOGIME,
		/** 親決め */
		OYAGIME,
		/** 洗牌 */
		SENPAI,
		/** 理牌待ち */
		RIHAI_WAIT,
		/** サイ振り */
		SAIFURI,
		/** 配牌 */
		HAIPAI,
		/** 流局 */
		RYUUKYOKU,
		/** 流し */
		NAGASHI,
		/** ツモ */
		TSUMO,
		/** 捨牌選択 */
		SUTEHAISELECT,
		/** ツモあがり */
		TSUMOAGARI,
		/** 捨牌 */
		SUTEHAI,
		/** リーチ */
		REACH,
		/** ロン */
		RON,
		/** ポン */
		PON,
		/** チー */
		CHII,
		/** 明槓 */
		MINKAN,
		/** 暗槓 */
		ANKAN,
		/** 連荘 */
		RENCHAN,
		/** 進行待ち */
		PROGRESS_WAIT,
		/** ゲームの終了 */
		END
	}

	/**
	 * イベントを処理します。
	 *
	 * @param eid
	 *            イベントID
	 * @param fromKaze
	 *            イベントを発行した風
	 * @param toKaze
	 *            イベントの対象となった風
	 * @return イベントID
	 */
	EID event(EID eid, int fromKaze, int toKaze);

	/**
	 * 捨牌のインデックスを取得します。
	 *
	 * @return 捨牌のインデックス
	 */
	int getSutehaiIdx();

	String getName();
}
