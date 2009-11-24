package jp.sourceforge.andjong.mahjong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.sourceforge.andjong.mahjong.EventIF.EID;

public class Man implements EventIF {
	private Info info;

	/** 捨牌のインデックス */
	private int sutehaiIdx = 0;

	private String name;

	public String getName() {
		return name;
	}

	private PlayerAction mPlayerAction;

	public Man(Info info, String name, PlayerAction playerAction) {
		this.info = info;
		this.name = name;
		this.mPlayerAction = playerAction;
	}

	private Tehai tehai = new Tehai();
	@Override
	public EID event(EID eid, int fromKaze, int toKaze) {
		String cmd;
		BufferedReader br;
		int sutehaiIdx = 0;
		switch (eid) {
		case TSUMO:
			info.copyTehai(tehai, info.getJikaze());
			while (true) {
				try {
					// 入力待ち
					Thread.sleep(10, 0);
					sutehaiIdx = info.getSutehaiIdx();
					if (sutehaiIdx != Integer.MAX_VALUE) {
						info.setSutehaiIdx(Integer.MAX_VALUE);
						if (sutehaiIdx == 100) {
							int agariScore = info.getAgariScore(tehai, info.getTsumoHai());
							if (agariScore > 0) {
								return EID.RON;
							}
							continue;
						}
						if (sutehaiIdx >= 0 && sutehaiIdx <= 14) {
							break;
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
			sutehaiIdx = Integer.parseInt(cmd);
			*/
//			if (sutehaiIdx == 0)
//				return EID.TSUMOAGARI;
//			sutehaiIdx--;
			this.sutehaiIdx = sutehaiIdx;
			return EID.SUTEHAI;
		case SUTEHAISELECT:
			while (true) {
				try {
					// 入力待ち
					Thread.sleep(10, 0);
					sutehaiIdx = info.getSutehaiIdx();
					if (sutehaiIdx != Integer.MAX_VALUE) {
						info.setSutehaiIdx(Integer.MAX_VALUE);
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
			info.copyTehai(tehai, info.getJikaze());
			int score = info.getAgariScore(tehai, info.getSuteHai());
			if (score > 0) {
				while (true) {
					try {
						// 入力待ち
						Thread.sleep(10, 0);
						sutehaiIdx = info.getSutehaiIdx();
						if (sutehaiIdx != Integer.MAX_VALUE) {
							info.setSutehaiIdx(Integer.MAX_VALUE);
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

			if (tehai.validPon(info.getSuteHai())) {
				synchronized (mPlayerAction) {
					mPlayerAction.setState(PlayerAction.STATE_ACTION_WAIT);
				}
				while (true) {
					try {
						// 入力待ち
						Thread.sleep(10, 0);
						sutehaiIdx = info.getSutehaiIdx();
						if (sutehaiIdx != Integer.MAX_VALUE) {
							synchronized (mPlayerAction) {
								mPlayerAction.setState(PlayerAction.STATE_NONE);
							}
							info.setSutehaiIdx(Integer.MAX_VALUE);
							if (sutehaiIdx == 100) {
								return EID.PON;
							}
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
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
