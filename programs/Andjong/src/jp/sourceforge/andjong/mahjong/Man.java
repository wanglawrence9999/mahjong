package jp.sourceforge.andjong.mahjong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.util.Log;

import jp.sourceforge.andjong.mahjong.EventIF.EID;

public class Man implements EventIF {
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
	public EID event(EID eid, int fromKaze, int toKaze) {
		int sutehaiIdx = 0;
		switch (eid) {
		case TSUMO:
			// 手牌をコピーする。
			mInfo.copyTehai(mTehai, mInfo.getJikaze());
			while (true) {
				int agariScore = mInfo.getAgariScore(mTehai, mInfo.getTsumoHai());
				if (agariScore > 0) {
					mPlayerAction.setValidTsumo(true);
				}

				// 入力を待つ。
				mPlayerAction.actionWait();
				sutehaiIdx = mPlayerAction.getSutehaiIdx();
				if (sutehaiIdx != Integer.MAX_VALUE) {
					if (mPlayerAction.isValidTsumo()) {
						if (sutehaiIdx == 100) {
							mPlayerAction.init();
							return EID.TSUMOAGARI;
						}
					}
					if (sutehaiIdx >= 0 && sutehaiIdx <= 13) {
						break;
					}
				}
			}
			mPlayerAction.init();
			this.sutehaiIdx = sutehaiIdx;
			return EID.SUTEHAI;
		case SUTEHAISELECT:
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
			return EID.SUTEHAI;
		case SUTEHAI:
			if (fromKaze == 0) {
				return EID.NAGASHI;
			}
			mInfo.copyTehai(mTehai, mInfo.getJikaze());
			int score = mInfo.getAgariScore(mTehai, mInfo.getSuteHai());
			if (score > 0) {
				while (true) {
					try {
						// 入力待ち
						Thread.sleep(10, 0);
						sutehaiIdx = mPlayerAction.getSutehaiIdx();
						if (sutehaiIdx != Integer.MAX_VALUE) {
							mPlayerAction.setSutehaiIdx(Integer.MAX_VALUE);
							if (sutehaiIdx == 100) {
								return EID.RON;
							}
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (mTehai.validPon(mInfo.getSuteHai())) {
				mPlayerAction.setValidPon(true);
				//mPlayerAction.setActionRequest(true);
				while (true) {
					mPlayerAction.actionWait();
					sutehaiIdx = mPlayerAction.getSutehaiIdx();
					if (sutehaiIdx != Integer.MAX_VALUE) {
						synchronized (mPlayerAction) {
							mPlayerAction.setState(PlayerAction.STATE_NONE);
						}
						mPlayerAction.setSutehaiIdx(Integer.MAX_VALUE);
						if (sutehaiIdx == 100) {
							mPlayerAction.init();
							//mPlayerAction.setActionRequest(false);
							return EID.PON;
						}
						break;
					}
				}
				mPlayerAction.init();
				//mPlayerAction.setActionRequest(false);
			}
			/*
			cmd = null;
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(":");
			try {
				cmd = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (cmd.length() > 0)
				sutehaiIdx = Integer.parseInt(cmd);
			if (sutehaiIdx == 1) {
				return EID.RON;
			}
			*/
			break;
		default:
			break;
		}

		return EID.NAGASHI;
	}

	public int getSutehaiIdx() {
		return sutehaiIdx;
	}
}
