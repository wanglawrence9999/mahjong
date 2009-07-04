package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Info.*;

/**
 * UI‚ğÀ‘•‚·‚éƒNƒ‰ƒX‚Å‚·B ‚Æ‚è‚ ‚¦‚¸AƒRƒ“ƒ\[ƒ‹‚ÅÀ‘•‚µ‚Ü‚·B TODO ƒI[ƒo[ƒ‰ƒCƒh‚µ‚â‚·‚¢İŒv‚É‚·‚éB
 * 
 * @author Yuji Urushibara
 * 
 */
public class UI {
	private Info info;

	private Tehai tehai = new Tehai();

	public UI(Info info) {
		this.info = info;
	}

	static public String idToString(int id) {
		int kind = id & (Hai.KIND_SHUU | Hai.KIND_TSUU);
		id &= ~(Hai.KIND_SHUU | Hai.KIND_TSUU);

		switch (kind) {
		case Hai.KIND_WAN:
			switch (id) {
			case 1:
				return "ˆê";
			case 2:
				return "“ñ";
			case 3:
				return "O";
			case 4:
				return "l";
			case 5:
				return "ŒÜ";
			case 6:
				return "˜Z";
			case 7:
				return "µ";
			case 8:
				return "”ª";
			case 9:
				return "‹ã";
			}
			break;
		case Hai.KIND_PIN:
			switch (id) {
			case 1:
				return "‡@";
			case 2:
				return "‡A";
			case 3:
				return "‡B";
			case 4:
				return "‡C";
			case 5:
				return "‡D";
			case 6:
				return "‡E";
			case 7:
				return "‡F";
			case 8:
				return "‡G";
			case 9:
				return "‡H";
			}
			break;
		case Hai.KIND_SOU:
			switch (id) {
			case 1:
				return "‚P";
			case 2:
				return "‚Q";
			case 3:
				return "‚R";
			case 4:
				return "‚S";
			case 5:
				return "‚T";
			case 6:
				return "‚U";
			case 7:
				return "‚V";
			case 8:
				return "‚W";
			case 9:
				return "‚X";
			}
			break;
		case Hai.KIND_FON:
			switch (id) {
			case 1:
				return "“Œ";
			case 2:
				return "“ì";
			case 3:
				return "¼";
			case 4:
				return "–k";
			}
			break;
		case Hai.KIND_SANGEN:
			switch (id) {
			case 1:
				return "”’";
			case 2:
				return "á¢";
			case 3:
				return "’†";
			}
			break;
		}

		return null;
	}

	public void event(int eventCallPlayerIdx, int eventTargetPlayerIdx,
			int eventId) {
		switch (eventId) {
		case EVENTID_TSUMO:
			System.out.println("[" + eventCallPlayerIdx + "]["
					+ eventTargetPlayerIdx + "]EVENTID_TSUMO");
			info.copyTehai(tehai, 0);
			// ƒè”v‚ğ•\¦‚µ‚Ü‚·B
			for (int i = 0; i < tehai.jyunTehaiLength; i++)
				System.out.print(idToString(tehai.jyunTehai[i].id));
			System.out.println();
			break;
		case EVENTID_SUTEHAI:
			System.out.println("[" + eventCallPlayerIdx + "]["
					+ eventTargetPlayerIdx + "]EVENTID_SUTEHAI");
			break;
		case EVENTID_NAGASHI:
			System.out.println("[" + eventCallPlayerIdx + "]["
					+ eventTargetPlayerIdx + "]EVENTID_NAGASHI");
			break;
		default:
			break;
		}
	}
}
