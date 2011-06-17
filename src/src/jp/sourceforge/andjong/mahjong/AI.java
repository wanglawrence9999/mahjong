package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

/**
 * AIを実装するクラスです。
 *
 * @author Yuji Urushibara
 *
 */
public class AI implements EventIf {
	/** Infoのコンストラクタ */
	private Info m_info;

	/** AIの名前 */
	private String m_name;

	/** 捨牌のインデックス */
	private int m_iSutehai;

	/** 手牌 */
	private Tehai m_tehai = new Tehai();

	/** 河 */
	private Hou m_hou = new Hou();

	/** 捨牌 */
	private Hai m_suteHai = new Hai();

	/**
	 * AIを作成する。
	 *
	 * @param a_info
	 *            Infoのコンストラクタ
	 * @param a_name
	 *            AIの名前
	 */
	public AI(Info a_info, String a_name) {
		this.m_info = a_info;
		this.m_name = a_name;
	}

	@Override
	public String getName() {
		return m_name;
	}

	@Override
	public int getISutehai() {
		return m_iSutehai;
	}

	@Override
	public EventId event(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		EventId eventId = EventId.NAGASHI;

		switch (a_eventId) {
		case TSUMO:
			eventId = eventTsumo(a_kazeFrom, a_kazeTo);
			break;
		case PON:
		case CHII_CENTER:
		case CHII_LEFT:
		case CHII_RIGHT:
		case DAIMINKAN:
		case SUTEHAI:
		case RON_CHECK:
		case REACH:
			eventId = eventSutehai(a_kazeFrom, a_kazeTo);
			break;
		case SELECT_SUTEHAI:
			m_info.copyTehai(m_tehai);
			thinkSutehai(null);
			break;
		default:
			break;
		}

		return eventId;
	}

	/**
	 * イベント(ツモ)を処理する。
	 *
	 * @param a_kazeFrom
	 *            イベントを発行した風
	 * @param a_kazeTo
	 *            イベントを発行した風
	 * @return イベントID
	 */
	private EventId eventTsumo(int a_kazeFrom, int a_kazeTo) {
		m_info.copyTehai(m_tehai);
		Hai tsumoHai = m_info.getTsumoHai();

		// ツモあがりの場合は、イベント(ツモあがり)を返す。
		int agariScore = m_info.getAgariScore(m_tehai, tsumoHai);
		if (agariScore > 0) {
			return EventId.TSUMO_AGARI;
		}

		// リーチの場合は、ツモ切りする。
		if (m_info.isReach()) {
			m_iSutehai = 13;
			return EventId.SUTEHAI;
		}

		thinkSutehai(tsumoHai);

		// 捨牌を決めたので手牌を更新します。
		if (m_iSutehai != 13) {
			m_tehai.rmJyunTehai(m_iSutehai);
			m_tehai.addJyunTehai(tsumoHai);
		}

		// リーチする場合はイベント（リーチ）を返します。
		if (thinkReach(m_tehai)) {
			return EventId.REACH;
		}

		return EventId.SUTEHAI;
	}

	/**
	 * イベント(捨牌)を処理する。
	 *
	 * @param a_kazeFrom
	 *            イベントを発行した風
	 * @param a_kazeTo
	 *            イベントを発行した風
	 * @return イベントID
	 */
	private EventId eventSutehai(int a_kazeFrom, int a_kazeTo) {
		if (a_kazeFrom == m_info.getJikaze()) {
			return EventId.NAGASHI;
		}

		m_info.copyTehai(m_tehai);
		m_suteHai = m_info.getSuteHai();
		m_info.copyKawa(m_hou, m_info.getJikaze());

		if (isFuriten() == false) {
			int agariScore = m_info.getAgariScore(m_tehai, m_suteHai);
			if (agariScore > 0) {
				return EventId.RON_AGARI;
			}
		}

		return EventId.NAGASHI;
	}

	private boolean isFuriten() {
		boolean furiten = false;
		Hai[] hais = new Hai[Hai.ID_ITEM_MAX];
		int indexNum = m_info.getMachiIndexs(m_tehai, hais);
		if (indexNum > 0) {
			SuteHai suteHaiTemp = new SuteHai();
			SuteHai[] suteHais = m_hou.getSuteHais();
			int suteHaisLength = m_hou.getSuteHaisLength();
			FURITENLOOP: for (int i = 0; i < suteHaisLength; i++) {
				suteHaiTemp = suteHais[i];
				for (int j = 0; j < indexNum; j++) {
					if (suteHaiTemp.getId() == hais[j].getId()) {
						furiten = true;
						break FURITENLOOP;
					}
				}
			}

			if (m_info.isReach() && !furiten) {
				suteHais = m_info.getSuteHais();
				int suteHaisCount = m_info.getSuteHaisCount();
				int playerSuteHaisCount = m_info.getPlayerSuteHaisCount();
				FURITENLOOP: for (; playerSuteHaisCount < suteHaisCount - 1; playerSuteHaisCount++) {
					suteHaiTemp = suteHais[playerSuteHaisCount];
					for (int j = 0; j < indexNum; j++) {
						if (suteHaiTemp.getId() == hais[j].getId()) {
							furiten = true;
							break FURITENLOOP;
						}
					}
				}
			}
		}

		return furiten;
	}

	/** カウントフォーマット */
	private CountFormat countFormat = new CountFormat();

	private final static int HYOUKA_SHUU = 1;

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	private void thinkSutehai(Hai addHai) {
		int score = 0;
		int maxScore = 0;

		m_iSutehai = 13;
		countFormat.setCountFormat(m_tehai, null);
		maxScore = getCountFormatScore(countFormat);
		// System.out.println("score:" + score + ",maxScore:" + maxScore +
		// ",hai:" + UI.idToString(tsumoHai.getId()));
		Hai hai = new Hai();

		Hai[] jyunTehai = new Hai[Tehai.JYUN_TEHAI_LENGTH_MAX];
		for (int i = 0; i < Tehai.JYUN_TEHAI_LENGTH_MAX; i++)
			jyunTehai[i] = new Hai();
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		Tehai.copyJyunTehai(jyunTehai, m_tehai.getJyunTehai(), jyunTehaiLength);

		for (int i = 0; i < jyunTehaiLength; i++) {
			m_tehai.copyJyunTehaiIndex(hai, i);
			m_tehai.rmJyunTehai(i);
			countFormat.setCountFormat(m_tehai, addHai);
			score = getCountFormatScore(countFormat);
			// System.out.println("score:" + score + ",maxScore:" + maxScore +
			// ",hai:" + UI.idToString(hai.getId()));
			if (score > maxScore) {
				maxScore = score;
				// System.out.println("setSutehaiIdx:" + i);
				m_iSutehai = i;
			}
			m_tehai.addJyunTehai(hai);
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
		if (m_info.getTsumoRemain() >= 4) {
			for (Hai hai : haiTable) {
				countFormat.setCountFormat(tehai, hai);
				if (countFormat.getCombis(combis) > 0) {
					return true;
				}
			}
		}
		return false;
	}

	private int getCountFormatScore(CountFormat countFormat) {
		int score = 0;

		for (int i = 0; i < countFormat.m_countNum; i++) {
			if ((countFormat.m_counts[i].m_noKind & Hai.KIND_SHUU) != 0) {
				score += countFormat.m_counts[i].m_num * HYOUKA_SHUU;
			}

			if (countFormat.m_counts[i].m_num == 2) {
				score += 4;
			}

			if (countFormat.m_counts[i].m_num >= 3) {
				score += 8;
			}

			if ((countFormat.m_counts[i].m_noKind & Hai.KIND_SHUU) > 0) {
				if ((countFormat.m_counts[i].m_noKind + 1) == countFormat.m_counts[i + 1].m_noKind) {
					score += 4;
				}

				if ((countFormat.m_counts[i].m_noKind + 2) == countFormat.m_counts[i + 2].m_noKind) {
					score += 4;
				}
			}
		}

		return score;
	}
}
