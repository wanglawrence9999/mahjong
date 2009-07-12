package jp.sourceforge.andjong;

import jp.sourceforge.andjong.Tehai.Combi;
import jp.sourceforge.andjong.Tehai.CountFormat;

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

	private final static int HYOUKA_SHUU = 1;

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	public AI(Info info) {
		this.info = info;
	}

	public EID event(EID eid, int fromKaze, int toKaze) {
		EID returnEid = EID.NAGASHI;

		// イベントを処理する。
		switch (eid) {
		case TSUMO:// ツモ
			returnEid = eidTsumo(fromKaze, toKaze);
			break;
		case SUTEHAI:// 捨牌
			if (fromKaze == info.getJikaze()) {
				returnEid = EID.NAGASHI;
				break;
			}
			int agariScore = info.getAgariScore(tehai, info.getSuteHai());
			if (agariScore > 0) {
				returnEid = EID.RON;
			} else {
				returnEid = EID.NAGASHI;
			}
			break;
		default:
			break;
		}

		return returnEid;
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
	public EID eidTsumo(int fromKaze, int toKaze) {
		int score = 0;
		int maxScore = 0;
		sutehaiIdx = 13;

		// 自分の手牌をコピーします。
		info.copyTehai(tehai, fromKaze);

		// ツモ牌を取得します。
		Hai tsumoHai = info.getTsumoHai();

		// ツモあがりの場合はイベント（ツモあがり）を返します。
		int agariScore = info.getAgariScore(tehai, info.getSuteHai());
		if (agariScore > 0)
			return EID.TSUMOAGARI;

		// リーチの場合は流します。
		if (info.isReach(info.getJikaze())) {
			return EID.NAGASHI;
		}

		tehai.getCountFormat(countFormat, null);
		maxScore = getCountFormatScore(countFormat);
		// System.out.println("score:" + score + ",maxScore:" + maxScore +
		// ",hai:" + UI.idToString(tsumoHai.getId()));
		Hai hai = new Hai();

		Hai[] jyunTehai = new Hai[Tehai.JYUNTEHAI_MAX];
		for (int i = 0; i < Tehai.JYUNTEHAI_MAX; i++)
			jyunTehai[i] = new Hai();
		int jyunTehaiLength = tehai.copyJyunTehai(jyunTehai);

		for (int i = 0; i < jyunTehaiLength; i++) {
			tehai.copyJyunTehaiIdx(hai, i);
			tehai.removeJyunTehai(i);
			tehai.getCountFormat(countFormat, tsumoHai);
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

		return EID.SUTEHAI;
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
