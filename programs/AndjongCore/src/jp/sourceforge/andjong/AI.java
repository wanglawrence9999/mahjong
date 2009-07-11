package jp.sourceforge.andjong;

import jp.sourceforge.andjong.Tehai.Combi;
import jp.sourceforge.andjong.Tehai.CountFormat;

/**
 * AIを実装するクラスです。<br>
 * TODO オーバーライドしやすい設計にする。
 * 
 * @author Yuji Urushibara
 * 
 */
public class AI implements EventIF {
	private Info info;

	private Tehai tehai = new Tehai();

	/** カウントフォーマット */
	private CountFormat countFormat = new CountFormat();

	public AI(Info info) {
		this.info = info;
	}

	public EID event(EID eid, int fromKaze, int toKaze) {
		EID returnEid = EID.NAGASHI;

		switch (eid) {
		case TSUMO:
			returnEid = eventTsumo(fromKaze, toKaze);
			break;
		case SUTEHAI:
			if (fromKaze == info.getJikaze()) {
				returnEid = EID.NAGASHI;
				break;
			}
			info.copyTehai(tehai, 0);
			tehai.getCountFormat(countFormat, info.getSuteHai());
			int combisCount = tehai.getCombi(combis, countFormat);
			if (combisCount > 0) {
				info.getAgariScore(tehai, info.getSuteHai(), combisCount,
						combis);
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

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	private final static int HYOUKA_SHUU = 1;

	public EID eventTsumo(int fromKaze, int toKaze) {
		int score = 0;
		int maxScore = 0;
		info.copyTehai(tehai, fromKaze);
		Hai tsumoHai = info.getTsumoHai();

		// 和了のチェックをする。
		tehai.getCountFormat(countFormat, tsumoHai);
		int combisCount = tehai.getCombi(combis, countFormat);
		if (combisCount > 0) {
			info.getAgariScore(tehai, info.getSuteHai(), combisCount, combis);
			return EID.TSUMOAGARI;
		}

		info.setSutehaiIdx(13);
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
				info.setSutehaiIdx(i);
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
