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
			returnEid = eidTsumo(fromKaze, toKaze);
			break;
		case SUTEHAI:
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

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	private final static int HYOUKA_SHUU = 1;

	public EID eidTsumo(int fromKaze, int toKaze) {
		int score = 0;
		int maxScore = 0;

		// 自分の手牌をコピーします。
		info.copyTehai(tehai, fromKaze);

		// ツモ牌を取得します。
		Hai tsumoHai = info.getTsumoHai();

		// ツモあがりの場合はEID.TSUMOAGARIを返します。
		int agariScore = info.getAgariScore(tehai, info.getSuteHai());
		if (agariScore > 0)
			return EID.TSUMOAGARI;

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
