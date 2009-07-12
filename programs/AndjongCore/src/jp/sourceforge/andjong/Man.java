package jp.sourceforge.andjong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Man implements EventIF {
	private Info info;

	/** 捨牌のインデックス */
	private int sutehaiIdx = 0;

	public Man(Info info) {
		this.info = info;
	}

	@Override
	public EID event(EID eid, int fromKaze, int toKaze) {
		String cmd;
		BufferedReader br;
		int sutehaiIdx = 0;
		switch (eid) {
		case TSUMO:
			cmd = null;
			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(":");
			try {
				cmd = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sutehaiIdx = Integer.parseInt(cmd);
			if (sutehaiIdx == 0)
				return EID.TSUMOAGARI;
			sutehaiIdx--;
			this.sutehaiIdx = sutehaiIdx;
			return EID.SUTEHAI;
		case SUTEHAI:
			if (fromKaze == 0) {
				return EID.NAGASHI;
			}
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
