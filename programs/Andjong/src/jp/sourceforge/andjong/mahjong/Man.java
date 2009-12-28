package jp.sourceforge.andjong.mahjong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import jp.sourceforge.andjong.mahjong.EventIf.EventId;

public class Man implements EventIf {
	/** プレイヤーに提供する情報 */
	private Info mInfo;

	/** 捨牌のインデックス */
	private int sutehaiIdx = 0;

	private String name;

	public String getName() {
		return name;
	}

	private PlayerAction mPlayerAction;

	public Man(Info info, String name, PlayerAction playerAction) {
		this.mInfo = info;
		this.name = name;
		this.mPlayerAction = playerAction;
	}

	/** 手牌 */
	private Tehai mTehai = new Tehai();

	@Override
	public EventId event(EventId eid, int fromKaze, int toKaze) {
		int sutehaiIdx = 0;
		int agariScore = 0;
		switch (eid) {
		case TSUMO:
			// 手牌をコピーする。
			mInfo.copyTehai(mTehai, mInfo.getJikaze());
			agariScore = mInfo.getAgariScore(mTehai, mInfo.getTsumoHai());
			if (agariScore > 0) {
			//if (true) {
				mPlayerAction.setValidTsumo(true);
				mPlayerAction.setMenuSelect(0);
				mPlayerAction.setState(PlayerAction.STATE_TSUMO_SELECT);
				mPlayerAction.actionWait();
				if (mPlayerAction.getMenuSelect() == 0) {
					mPlayerAction.init();
					return EventId.TSUMO_AGARI;
				}
				mPlayerAction.init();
			}
			while (true) {
				// 入力を待つ。
				mPlayerAction.setState(PlayerAction.STATE_SUTEHAI_SELECT);
				mPlayerAction.actionWait();
				sutehaiIdx = mPlayerAction.getSutehaiIdx();
				if (sutehaiIdx != Integer.MAX_VALUE) {
					if (mPlayerAction.isValidTsumo()) {
						if (sutehaiIdx == 100) {
							mPlayerAction.init();
							return EventId.TSUMO_AGARI;
						}
					}
					if (sutehaiIdx >= 0 && sutehaiIdx <= 13) {
						break;
					}
				}
			}
			mPlayerAction.init();
			this.sutehaiIdx = sutehaiIdx;
			return EventId.SUTEHAI;
		case SELECT_SUTEHAI:
			while (true) {
				try {
					// 入力待ち
					Thread.sleep(10, 0);
					sutehaiIdx = mPlayerAction.getSutehaiIdx();
					if (sutehaiIdx != Integer.MAX_VALUE) {
						mPlayerAction.setSutehaiIdx(Integer.MAX_VALUE);
						if (sutehaiIdx >= 0 && sutehaiIdx <= 14) {
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.sutehaiIdx = sutehaiIdx;
			return EventId.SUTEHAI;
		case SUTEHAI:
			if (fromKaze == mInfo.getJikaze()) {
				return EventId.NAGASHI;
			}
			mInfo.copyTehai(mTehai, mInfo.getJikaze());
			agariScore = mInfo.getAgariScore(mTehai, mInfo.getTsumoHai());
			if (agariScore > 0) {
				mPlayerAction.setValidTsumo(true);
				mPlayerAction.setMenuSelect(0);
				mPlayerAction.setState(PlayerAction.STATE_RON_SELECT);
				mPlayerAction.actionWait();
				if (mPlayerAction.getMenuSelect() == 0) {
					mPlayerAction.init();
					return EventId.RON_AGARI;
				}
				mPlayerAction.init();
			}

//			if (mTehai.validPon(mInfo.getSuteHai())) {
//				mPlayerAction.setValidPon(true);
//				//mPlayerAction.setActionRequest(true);
//				while (true) {
//					mPlayerAction.actionWait();
//					sutehaiIdx = mPlayerAction.getSutehaiIdx();
//					if (sutehaiIdx != Integer.MAX_VALUE) {
//						synchronized (mPlayerAction) {
//							mPlayerAction.setState(PlayerAction.STATE_NONE);
//						}
//						mPlayerAction.setSutehaiIdx(Integer.MAX_VALUE);
//						if (sutehaiIdx == 100) {
//							mPlayerAction.init();
//							//mPlayerAction.setActionRequest(false);
//							return EID.PON;
//						}
//						break;
//					}
//				}
//				mPlayerAction.init();
//				//mPlayerAction.setActionRequest(false);
//			}
			break;
		default:
			break;
		}

		return EventId.NAGASHI;
	}

	public int getISutehai() {
		return sutehaiIdx;
	}
}
