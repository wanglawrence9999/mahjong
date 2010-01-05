package jp.sourceforge.andjong.mahjong;

import android.util.Log;

public class Man implements EventIf {
	/** プレイヤーに提供する情報 */
	private Info m_info;

	/** 捨牌のインデックス */
	private int m_iSutehai = 0;

	private String name;

	public String getName() {
		return name;
	}

	private PlayerAction m_playerAction;

	public Man(Info info, String name, PlayerAction playerAction) {
		this.m_info = info;
		this.name = name;
		this.m_playerAction = playerAction;
	}

	/** 手牌 */
	private Tehai m_tehai = new Tehai();

	@Override
	public EventId event(EventId eid, int fromKaze, int toKaze) {
		int sutehaiIdx = 0;
		int agariScore = 0;
		Hai tsumoHai;
		Hai suteHai;
		int indexNum = 0;
		int[] indexs = new int[14];
		int menuNum = 0;
		EventId eventId[] = new EventId[4];
		int jyunTehaiLength;
		switch (eid) {
		case TSUMO:
			// 手牌をコピーする。
			m_info.copyTehai(m_tehai, m_info.getJikaze());
			// ツモ牌を取得する。
			tsumoHai = m_info.getTsumoHai();

			if (!m_info.isReach()) {
				Log.d("Man", "getReachIndexs in");
				indexNum = m_info.getReachIndexs(m_tehai, tsumoHai, indexs);
				Log.d("Man", "getReachIndexs = " + indexNum);
				if (indexNum > 0) {
					m_playerAction.setValidReach(true);
					eventId[menuNum] = EventId.REACH;
					menuNum++;
				}
			}

			agariScore = m_info.getAgariScore(m_tehai, tsumoHai);
			if (agariScore > 0) {
				Log.d("Man", "agariScore = " + agariScore);
				m_playerAction.setValidTsumo(true);
				eventId[menuNum] = EventId.TSUMO_AGARI;
				menuNum++;
			}

			if (menuNum > 0) {
				m_playerAction.setMenuSelect(5);
				m_playerAction.setState(PlayerAction.STATE_TSUMO_SELECT);
				m_info.postInvalidate();
				m_playerAction.actionWait();
				int menuSelect = m_playerAction.getMenuSelect();
				if (menuSelect < menuNum) {
					m_playerAction.init();
					if (eventId[menuSelect] == EventId.REACH) {
						m_playerAction.m_indexs = indexs;
						m_playerAction.m_indexNum = indexNum;
						while (true) {
							// 入力を待つ。
							m_playerAction.setState(PlayerAction.STATE_SUTEHAI_SELECT);
							m_playerAction.actionWait();
							sutehaiIdx = m_playerAction.getSutehaiIdx();
							if (sutehaiIdx != Integer.MAX_VALUE) {
								if (sutehaiIdx >= 0 && sutehaiIdx <= 13) {
									break;
								}
							}
						}
						m_playerAction.init();
						this.m_iSutehai = sutehaiIdx;
					}
					return eventId[menuSelect];
				}
				m_playerAction.init();
			}
			while (true) {
				// 入力を待つ。
				m_playerAction.setState(PlayerAction.STATE_SUTEHAI_SELECT);
				m_playerAction.actionWait();
				sutehaiIdx = m_playerAction.getSutehaiIdx();
				if (sutehaiIdx != Integer.MAX_VALUE) {
					if (sutehaiIdx >= 0 && sutehaiIdx <= 13) {
						break;
					}
				}
			}
			m_playerAction.init();
			this.m_iSutehai = sutehaiIdx;
			return EventId.SUTEHAI;
		case SELECT_SUTEHAI:
			m_info.copyTehai(m_tehai, m_info.getJikaze());
			jyunTehaiLength = m_tehai.getJyunTehaiLength();
			while (true) {
				// 入力を待つ。
				m_playerAction.setState(PlayerAction.STATE_SUTEHAI_SELECT);
				m_playerAction.actionWait();
				sutehaiIdx = m_playerAction.getSutehaiIdx();
				if (sutehaiIdx != Integer.MAX_VALUE) {
					if (sutehaiIdx >= 0 && sutehaiIdx <= jyunTehaiLength) {
						break;
					}
				}
			}
			Log.d("Man", "sutehaiIdx = " + sutehaiIdx);
			m_playerAction.init();
			this.m_iSutehai = sutehaiIdx;
			return EventId.SUTEHAI;
		case SUTEHAI:
			if (fromKaze == m_info.getJikaze()) {
				return EventId.NAGASHI;
			}

			m_info.copyTehai(m_tehai, m_info.getJikaze());
			suteHai = m_info.getSuteHai();

			agariScore = m_info.getAgariScore(m_tehai, suteHai);
			if (agariScore > 0) {
				Log.d("Man", "agariScore = " + agariScore);
				m_playerAction.setValidRon(true);
				eventId[menuNum] = EventId.RON_AGARI;
				menuNum++;
			}

			if (m_tehai.validPon(suteHai)) {
				m_playerAction.setValidPon(true);
				eventId[menuNum] = EventId.PON;
				menuNum++;
			}

			if (menuNum > 0) {
				m_playerAction.setMenuSelect(5);
				m_info.postInvalidate();
				m_playerAction.actionWait();
				int menuSelect = m_playerAction.getMenuSelect();
				if (menuSelect < menuNum) {
					m_playerAction.init();
					return eventId[menuSelect];
				}
				m_playerAction.init();
			}
			break;
		default:
			break;
		}

		return EventId.NAGASHI;
	}

	public int getISutehai() {
		return m_iSutehai;
	}
}
