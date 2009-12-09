package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

/**
 * AIを実装するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
public class AI implements EventIF {
	private Info info;

	private Tehai tehai = new Tehai();

	/** カウントフォーマット */
	private CountFormat countFormat = new CountFormat();

	private int sutehaiIdx = 0;

	public int getSutehaiIdx() {
		return sutehaiIdx;
	}

	private String name;

	public String getName() {
		return name;
	}

	private final static int HYOUKA_SHUU = 1;

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	public AI(Info info, String name) {
		this.info = info;
		this.name = name;
	}

	public EID event(EID eid, int fromKaze, int toKaze) {
		EID returnEid = EID.NAGASHI;

		// イベントを処理する。
		switch (eid) {
		case TSUMO:// ツモ
			returnEid = eidTsumo(fromKaze, toKaze);
			break;
		case SUTEHAI:// 捨牌
			returnEid = eidSutehai(fromKaze, toKaze);
			break;
		case SUTEHAISELECT:
			if (fromKaze != info.getJikaze()) {
				return EID.NAGASHI;
			}
			// 自分の手牌をコピーします。
			info.copyTehai(tehai, fromKaze);
			thinkSutehai(null);
			break;
		default:
			break;
		}

		return returnEid;
	}

	private EID eidSutehai(int fromKaze, int toKaze) {
		if (fromKaze == info.getJikaze()) {
			return EID.NAGASHI;
		}
		int agariScore = info.getAgariScore(tehai, info.getSuteHai());
		if (agariScore > 0) {
			return EID.RON;
		}

//		{
//			if (tehai.validChiiRight(info.getSuteHai())) {
//				System.out.println("validChiiRight！！！！！！！！！！！！！！！！！！！");
//				Hai[] jyunTehai = tehai.getJyunTehai();
//				int jyunTehaiLength = tehai.getJyunTehaiLength();
//				for (int i = 0; i < jyunTehaiLength; i++)
//					System.out.print(Console.idToString(jyunTehai[i].getId()));
//				System.out.println();
//				tehai.setChiiRight(info.getSuteHai());
//				jyunTehai = tehai.getJyunTehai();
//				jyunTehaiLength = tehai.getJyunTehaiLength();
//				for (int i = 0; i < jyunTehaiLength; i++)
//					System.out.print(Console.idToString(jyunTehai[i].getId()));
//				System.out.println();
//			}
//		}

		// 牌が少なくなったら鳴いてみます。
//		if (info.getTsumoRemain() < 16) {
//			// ポンできるかチェックします。
//			if (tehai.validPon(info.getSuteHai())) {
//				return EID.PON;
//			}
//		}

		return EID.NAGASHI;
	}

	/**
	 * イベント（ツモ）を処理する。
	 *
	 * @param fromKaze
	 *            イベントを発行した風
	 * @param toKaze
	 *            イベントの対象となった風
	 * @return イベントID
	 */
	private EID eidTsumo(int fromKaze, int toKaze) {
		// 自分の手牌をコピーします。
		info.copyTehai(tehai, fromKaze);

		// ツモ牌を取得します。
		Hai tsumoHai = info.getTsumoHai();

		// ツモあがりの場合はイベント（ツモあがり）を返します。
		int agariScore = info.getAgariScore(tehai, tsumoHai);
		if (agariScore > 0) {
			return EID.TSUMOAGARI;
		}

		// リーチの場合はツモ切りします。
		if (info.isReach(info.getJikaze())) {
			sutehaiIdx = 13;
			return EID.SUTEHAI;
		}

		thinkSutehai(tsumoHai);

		// 捨牌を決めたので手牌を更新します。
		if (sutehaiIdx != 13) {
			tehai.rmJyunTehai(sutehaiIdx);
			tehai.addJyunTehai(tsumoHai);
		}

		// リーチする場合はイベント（リーチ）を返します。
		if (thinkReach(tehai)) {
			return EID.REACH;
		}

		return EID.SUTEHAI;
	}

	private void thinkSutehai(Hai addHai) {
		int score = 0;
		int maxScore = 0;

		CountFormat.getCountFormat(tehai, countFormat, null);
		maxScore = getCountFormatScore(countFormat);
		// System.out.println("score:" + score + ",maxScore:" + maxScore +
		// ",hai:" + UI.idToString(tsumoHai.getId()));
		Hai hai = new Hai();

		Hai[] jyunTehai = new Hai[Tehai.JYUNTEHAI_MAX];
		for (int i = 0; i < Tehai.JYUNTEHAI_MAX; i++)
			jyunTehai[i] = new Hai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		Tehai.copyJyunTehai(jyunTehai, tehai.getJyunTehai(), jyunTehaiLength);

		for (int i = 0; i < jyunTehaiLength; i++) {
			tehai.copyJyunTehaiIdx(hai, i);
			tehai.rmJyunTehai(i);
			CountFormat.getCountFormat(tehai, countFormat, addHai);
			score = getCountFormatScore(countFormat);
			// System.out.println("score:" + score + ",maxScore:" + maxScore +
			// ",hai:" + UI.idToString(hai.getId()));
			if (score > maxScore) {
				maxScore = score;
				// System.out.println("setSutehaiIdx:" + i);
				sutehaiIdx = i;
			}
			tehai.addJyunTehai(hai);
		}
	}

	private final static Hai[] haiTable = new Hai[] {
			new Hai(Hai.ID_WAN_1), new Hai(Hai.ID_WAN_2),
			new Hai(Hai.ID_WAN_3), new Hai(Hai.ID_WAN_4),
			new Hai(Hai.ID_WAN_5), new Hai(Hai.ID_WAN_6),
			new Hai(Hai.ID_WAN_7), new Hai(Hai.ID_WAN_8),
			new Hai(Hai.ID_WAN_9),
			new Hai(Hai.ID_PIN_1), new Hai(Hai.ID_PIN_2),
			new Hai(Hai.ID_PIN_3), new Hai(Hai.ID_PIN_4),
			new Hai(Hai.ID_PIN_5), new Hai(Hai.ID_PIN_6),
			new Hai(Hai.ID_PIN_7), new Hai(Hai.ID_PIN_8),
			new Hai(Hai.ID_PIN_9),
			new Hai(Hai.ID_SOU_1), new Hai(Hai.ID_SOU_2),
			new Hai(Hai.ID_SOU_3), new Hai(Hai.ID_SOU_4),
			new Hai(Hai.ID_SOU_5), new Hai(Hai.ID_SOU_6),
			new Hai(Hai.ID_SOU_7), new Hai(Hai.ID_SOU_8),
			new Hai(Hai.ID_SOU_9),
			new Hai(Hai.ID_TON), new Hai(Hai.ID_NAN),
			new Hai(Hai.ID_SHA), new Hai(Hai.ID_PE),
			new Hai(Hai.ID_HAKU), new Hai(Hai.ID_HATSU),
			new Hai(Hai.ID_CHUN) };

	private boolean thinkReach(Tehai tehai) {
		for (Hai hai : haiTable) {
			CountFormat.getCountFormat(tehai, countFormat, hai);
			if (countFormat.getCombi(combis) > 0) {
				return true;
			}
		}
		return false;
	}

	private int getCountFormatScore(CountFormat countFormat) {
		int score = 0;

		for (int i = 0; i < countFormat.length; i++) {
			if ((countFormat.counts[i].id & Hai.KIND_SHUU) != 0) {
				score += countFormat.counts[i].length * HYOUKA_SHUU;
			}

			if (countFormat.counts[i].length == 2) {
				score += 4;
			}

			if (countFormat.counts[i].length >= 3) {
				score += 8;
			}

			if ((countFormat.counts[i].id & Hai.KIND_SHUU) > 0) {
				if ((countFormat.counts[i].id + 1) == countFormat.counts[i + 1].id) {
					score += 4;
				}

				if ((countFormat.counts[i].id + 2) == countFormat.counts[i + 2].id) {
					score += 4;
				}
			}
		}

		return score;
	}
}
