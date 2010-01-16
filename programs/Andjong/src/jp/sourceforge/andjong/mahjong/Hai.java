package jp.sourceforge.andjong.mahjong;

/**
 * 牌を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Hai {
	/*
	 * ID
	 */

	/** 一萬 */
	public final static int ID_WAN_1 = 0;
	/** 二萬 */
	public final static int ID_WAN_2 = 1;
	/** 三萬 */
	public final static int ID_WAN_3 = 2;
	/** 四萬 */
	public final static int ID_WAN_4 = 3;
	/** 五萬 */
	public final static int ID_WAN_5 = 4;
	/** 六萬 */
	public final static int ID_WAN_6 = 5;
	/** 七萬 */
	public final static int ID_WAN_7 = 6;
	/** 八萬 */
	public final static int ID_WAN_8 = 7;
	/** 九萬 */
	public final static int ID_WAN_9 = 8;

	/** 一筒 */
	public final static int ID_PIN_1 = 9;
	/** 二筒 */
	public final static int ID_PIN_2 = 10;
	/** 三筒 */
	public final static int ID_PIN_3 = 11;
	/** 四筒 */
	public final static int ID_PIN_4 = 12;
	/** 五筒 */
	public final static int ID_PIN_5 = 13;
	/** 六筒 */
	public final static int ID_PIN_6 = 14;
	/** 七筒 */
	public final static int ID_PIN_7 = 15;
	/** 八筒 */
	public final static int ID_PIN_8 = 16;
	/** 九筒 */
	public final static int ID_PIN_9 = 17;

	/** 一索 */
	public final static int ID_SOU_1 = 18;
	/** 二索 */
	public final static int ID_SOU_2 = 19;
	/** 三索 */
	public final static int ID_SOU_3 = 20;
	/** 四索 */
	public final static int ID_SOU_4 = 21;
	/** 五索 */
	public final static int ID_SOU_5 = 22;
	/** 六索 */
	public final static int ID_SOU_6 = 23;
	/** 七索 */
	public final static int ID_SOU_7 = 24;
	/** 八索 */
	public final static int ID_SOU_8 = 25;
	/** 九索 */
	public final static int ID_SOU_9 = 26;

	/** 東 */
	public final static int ID_TON = 27;
	/** 南 */
	public final static int ID_NAN = 28;
	/** 西 */
	public final static int ID_SHA = 29;
	/** 北 */
	public final static int ID_PE = 30;

	/** 白 */
	public final static int ID_HAKU = 31;
	/** 發 */
	public final static int ID_HATSU = 32;
	/** 中 */
	public final static int ID_CHUN = 33;

	/** IDの最大値 */
	public final static int ID_MAX = ID_CHUN;

	/** IDの個数の最大値 */
	public final static int ID_ITEM_MAX = ID_MAX + 1;

	/*
	 * NO
	 */

	/** 1 */
	public final static int NO_1 = 1;
	/** 2 */
	public final static int NO_2 = 2;
	/** 3 */
	public final static int NO_3 = 3;
	/** 4 */
	public final static int NO_4 = 4;
	/** 5 */
	public final static int NO_5 = 5;
	/** 6 */
	public final static int NO_6 = 6;
	/** 7 */
	public final static int NO_7 = 7;
	/** 8 */
	public final static int NO_8 = 8;
	/** 9 */
	public final static int NO_9 = 9;

	/** 一萬 */
	public final static int NO_WAN_1 = 1;
	/** 二萬 */
	public final static int NO_WAN_2 = 2;
	/** 三萬 */
	public final static int NO_WAN_3 = 3;
	/** 四萬 */
	public final static int NO_WAN_4 = 4;
	/** 五萬 */
	public final static int NO_WAN_5 = 5;
	/** 六萬 */
	public final static int NO_WAN_6 = 6;
	/** 七萬 */
	public final static int NO_WAN_7 = 7;
	/** 八萬 */
	public final static int NO_WAN_8 = 8;
	/** 九萬 */
	public final static int NO_WAN_9 = 9;

	/** 一筒 */
	public final static int NO_PIN_1 = 1;
	/** 二筒 */
	public final static int NO_PIN_2 = 2;
	/** 三筒 */
	public final static int NO_PIN_3 = 3;
	/** 四筒 */
	public final static int NO_PIN_4 = 4;
	/** 五筒 */
	public final static int NO_PIN_5 = 5;
	/** 六筒 */
	public final static int NO_PIN_6 = 6;
	/** 七筒 */
	public final static int NO_PIN_7 = 7;
	/** 八筒 */
	public final static int NO_PIN_8 = 8;
	/** 九筒 */
	public final static int NO_PIN_9 = 9;

	/** 一索 */
	public final static int NO_SOU_1 = 1;
	/** 二索 */
	public final static int NO_SOU_2 = 2;
	/** 三索 */
	public final static int NO_SOU_3 = 3;
	/** 四索 */
	public final static int NO_SOU_4 = 4;
	/** 五索 */
	public final static int NO_SOU_5 = 5;
	/** 六索 */
	public final static int NO_SOU_6 = 6;
	/** 七索 */
	public final static int NO_SOU_7 = 7;
	/** 八索 */
	public final static int NO_SOU_8 = 8;
	/** 九索 */
	public final static int NO_SOU_9 = 9;

	/** 東 */
	public final static int NO_TON = 1;
	/** 南 */
	public final static int NO_NAN = 2;
	/** 西 */
	public final static int NO_SHA = 3;
	/** 北 */
	public final static int NO_PE = 4;

	/** 白 */
	public final static int NO_HAKU = 1;
	/** 發 */
	public final static int NO_HATSU = 2;
	/** 中 */
	public final static int NO_CHUN = 3;

	/*
	 * 種類
	 */

	/** 萬子 */
	public final static int KIND_WAN = 0x00000010;
	/** 筒子 */
	public final static int KIND_PIN = 0x00000020;
	/** 索子 */
	public final static int KIND_SOU = 0x00000040;
	/** 数牌 */
	public final static int KIND_SHUU = KIND_WAN | KIND_PIN | KIND_SOU;
	/** 風牌 */
	public final static int KIND_FON = 0x00000100;
	/** 三元牌 */
	public final static int KIND_SANGEN = 0x00000200;
	/** 字牌 */
	public final static int KIND_TSUU = KIND_FON | KIND_SANGEN;

	/** 番号の配列 */
	private final static int[] NOS = {
	// 萬子
	NO_WAN_1, NO_WAN_2, NO_WAN_3, NO_WAN_4, NO_WAN_5, NO_WAN_6, NO_WAN_7, NO_WAN_8, NO_WAN_9,
	// 筒子
	NO_PIN_1, NO_PIN_2, NO_PIN_3, NO_PIN_4, NO_PIN_5, NO_PIN_6, NO_PIN_7, NO_PIN_8, NO_PIN_9,
	// 索子
	NO_SOU_1, NO_SOU_2, NO_SOU_3, NO_SOU_4, NO_SOU_5, NO_SOU_6, NO_SOU_7, NO_SOU_8, NO_SOU_9,
	// 風牌
	NO_TON, NO_NAN, NO_SHA, NO_PE,
	// 三元牌
	NO_HAKU, NO_HATSU, NO_CHUN };

	/** 種類の配列 */
	private final static int[] KINDS = {
	// 萬子
	KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN,
	// 筒子
	KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN,
	// 索子
	KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU,
	// 風牌
	KIND_FON, KIND_FON, KIND_FON, KIND_FON,
	// 三元牌
	KIND_SANGEN, KIND_SANGEN, KIND_SANGEN };

	/** 一九牌フラグの配列 */
	private final static boolean[] IS_ICHIKYUUS = {
	// 萬子
	true, false, false, false, false, false, false, false, true,
	// 筒子
	true, false, false, false, false, false, false, false, true,
	// 索子
	true, false, false, false, false, false, false, false, true,
	// 風牌
	false, false, false, false,
	// 三元牌
	false, false, false };

	/** 字牌フラグの配列 */
	private final static boolean[] IS_TSUUS = {
	// 萬子
	false, false, false, false, false, false, false, false, false,
	// 筒子
	false, false, false, false, false, false, false, false, false,
	// 索子
	false, false, false, false, false, false, false, false, false,
	// 風牌
	true, true, true, true,
	// 三元牌
	true, true, true };

	/** ネクスト牌のIDの配列 */
	private final static int[] NEXT_HAI_IDS = {
	// 萬子
	ID_WAN_2, ID_WAN_3, ID_WAN_4, ID_WAN_5, ID_WAN_6, ID_WAN_7, ID_WAN_8, ID_WAN_9, ID_WAN_1,
	// 筒子
	ID_PIN_2, ID_PIN_3, ID_PIN_4, ID_PIN_5, ID_PIN_6, ID_PIN_7, ID_PIN_8, ID_PIN_9, ID_PIN_1,
	// 索子
	ID_SOU_2, ID_SOU_3, ID_SOU_4, ID_SOU_5, ID_SOU_6, ID_SOU_7, ID_SOU_8, ID_SOU_9, ID_SOU_1,
	// 風牌
	ID_NAN, ID_SHA, ID_PE, ID_TON,
	// 三元牌
	ID_HATSU, ID_CHUN, ID_HAKU };

	/** ID */
	private int m_id;

	/**
	 * 空の牌を作成する。
	 */
	public Hai() {
	}

	/**
	 * IDから牌を作成する。
	 *
	 * @param a_id
	 *            ID
	 */
	public Hai(int a_id) {
		this.m_id = a_id;
	}

	/**
	 * 牌から牌を作成する。
	 *
	 * @param a_hai
	 *            牌
	 */
	public Hai(Hai a_hai) {
		copy(this, a_hai);
	}

	/**
	 * 牌をコピーする。
	 *
	 * @param a_dest
	 *            コピー先の牌
	 * @param a_src
	 *            コピー元の牌
	 */
	public static void copy(Hai a_dest, Hai a_src) {
		a_dest.m_id = a_src.m_id;
	}

	/**
	 * IDを取得する。
	 *
	 * @return ID
	 */
	public int getId() {
		return m_id;
	}

	/**
	 * 番号を取得する。
	 *
	 * @return 番号
	 */
	public int getNo() {
		return NOS[m_id];
	}

	/**
	 * 種類を取得する。
	 *
	 * @return 種類
	 */
	public int getKind() {
		return KINDS[m_id];
	}

	/**
	 * NK(番号と種類のOR)を取得する。
	 *
	 * @return NK(番号と種類のOR)
	 */
	public int getNoKind() {
		return NOS[m_id] | KINDS[m_id];
	}

	/**
	 * 一九牌フラグを取得する。
	 *
	 * @return 一九牌フラグ
	 */
	public boolean isIchikyuu() {
		return IS_ICHIKYUUS[m_id];
	}

	/**
	 * 字牌フラグを取得する。
	 *
	 * @return 字牌フラグ
	 */
	public boolean isTsuu() {
		return IS_TSUUS[m_id];
	}

	/**
	 * 字牌フラグを取得する。
	 *
	 * @param a_noKind
	 *            NK(番号と種類のOR)
	 * @return 字牌フラグ
	 */
	public static boolean isTsuu(int a_noKind) {
		return (a_noKind & KIND_TSUU) != 0;
	}

	/**
	 * 一九字牌フラグを取得する。
	 *
	 * @return 一九字牌フラグ
	 */
	public boolean isYaochuu() {
		return IS_ICHIKYUUS[m_id] | IS_TSUUS[m_id];
	}

	/**
	 * ネクスト牌のIDを取得する。
	 *
	 * @return ネクスト牌のID
	 */
	public int getNextHaiId() {
		return NEXT_HAI_IDS[m_id];
	}

	/**
	 * NK(番号と種類のOR)をIDに変換する。
	 *
	 * @param a_noKind
	 *            NK(番号と種類のOR)
	 * @return ID
	 */
	public static int noKindToId(int a_noKind) {
		int id;
		if (a_noKind >= KIND_SANGEN) {
			id = a_noKind - KIND_SANGEN + ID_HAKU - 1;
		} else if (a_noKind >= KIND_FON) {
			id = a_noKind - KIND_FON + ID_TON - 1;
		} else if (a_noKind >= KIND_SOU) {
			id = a_noKind - KIND_SOU + ID_SOU_1 - 1;
		} else if (a_noKind >= KIND_PIN) {
			id = a_noKind - KIND_PIN + ID_PIN_1 - 1;
		} else {
			id = a_noKind - KIND_WAN + ID_WAN_1 - 1;
		}
		return id;
	}
}
