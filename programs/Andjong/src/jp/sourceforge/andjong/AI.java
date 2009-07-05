package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Info.*;
import jp.sourceforge.andjong.Tehai.CountFormat;

/**
 * AIを実装するクラスです。<br>
 * TODO オーバーライドしやすい設計にする。
 * 
 * @author Yuji Urushibara
 * 
 */
public class AI {
	private Info info;

	private Tehai tehai = new Tehai();

	public AI(Info info) {
		this.info = info;
	}

	public int event(int eventCallPlayerIdx, int eventTargetPlayerIdx,
			int eventId) {
		int returnEvent = EVENTID_NAGASHI;

		switch (eventId) {
		case EVENTID_TSUMO:
			returnEvent = eventTsumo();
			break;
		case EVENTID_SUTEHAI:
			returnEvent = EVENTID_NAGASHI;
		default:
			break;
		}

		return returnEvent;
	}

	public int eventTsumo() {
		int score = 0;
		int maxScore = 0;
		info.copyTehai(tehai, 0);

		Hai tsumoHai = info.getTsumoHai();

		info.setSutehaiIdx(13);
		CountFormat countFormat = tehai.getCountFormat(null);
		maxScore = getCountFormatScore(countFormat);
		//System.out.println("score:" + score + ",maxScore:" + maxScore + ",hai:" + UI.idToString(tsumoHai.getId()));
		Hai hai = new Hai();

		Hai[] jyunTehai = new Hai[Tehai.JYUNTEHAI_MAX];
		for (int i = 0; i < Tehai.JYUNTEHAI_MAX; i++)
			jyunTehai[i] = new Hai();
		int jyunTehaiLength = tehai.copyJyunTehai(jyunTehai);
		
		for (int i = 0; i < jyunTehaiLength; i++) {
			tehai.copyJyunTehaiIdx(hai, i);
			tehai.removeJyunTehai(i);
			countFormat = tehai.getCountFormat(tsumoHai);
			score = getCountFormatScore(countFormat);
			//System.out.println("score:" + score + ",maxScore:" + maxScore + ",hai:" + UI.idToString(hai.getId()));
			if (score > maxScore) {
				maxScore = score;
				//System.out.println("setSutehaiIdx:" + i);
				info.setSutehaiIdx(i);
			}
			tehai.addJyunTehai(hai);
		}

		return EVENTID_SUTEHAI;
	}

	private int getCountFormatScore(CountFormat countFormat) {
		int score = 0;

		for (int i = 0; i < countFormat.length; i++) {
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
