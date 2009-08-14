package jp.sourceforge.andjong;

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

	/** ID */
	private int id;

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

	/*
	 * 番号
	 */

	/** 一 */
	public final static int NO_1 = 1;
	/** 二 */
	public final static int NO_2 = 2;
	/** 三 */
	public final static int NO_3 = 3;
	/** 四 */
	public final static int NO_4 = 4;
	/** 五 */
	public final static int NO_5 = 5;
	/** 六 */
	public final static int NO_6 = 6;
	/** 七 */
	public final static int NO_7 = 7;
	/** 八 */
	public final static int NO_8 = 8;
	/** 九 */
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
	 * ID Format A
	 */

	/** 萬子 */
	public final static int ID_A_WAN = 0x00000010;
	/** 筒子 */
	public final static int ID_A_PIN = 0x00000020;
	/** 索子 */
	public final static int ID_A_SOU = 0x00000040;
	/** 数牌 */
	public final static int ID_A_SHUU = ID_A_WAN | ID_A_PIN | ID_A_SOU;

	/** 風牌 */
	public final static int ID_A_FON = 0x00000100;
	/** 三元牌 */
	public final static int ID_A_SANGEN = 0x00000200;
	/** 字牌 */
	public final static int ID_A_TSUU = ID_A_FON | ID_A_SANGEN;

	/*
	 * 種類
	 */

	/** 萬子 */
	public final static int KIND_WAN = 0;
	/** 筒子 */
	public final static int KIND_PIN = 1;
	/** 索子 */
	public final static int KIND_SOU = 2;
	/** 風牌 */
	public final static int KIND_FON = 3;
	/** 三元牌 */
	public final static int KIND_SANGEN = 4;

	/*
	 * 定数の配列
	 */

	/** 番号の配列 */
	private final static int[] nos = {
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

	/** ID Format Aの配列 */
	private final static int[] idAs = {
	// 萬子
	ID_A_WAN | NO_WAN_1, ID_A_WAN | NO_WAN_2, ID_A_WAN | NO_WAN_3, ID_A_WAN | NO_WAN_4, ID_A_WAN | NO_WAN_5, ID_A_WAN | NO_WAN_6, ID_A_WAN | NO_WAN_7, ID_A_WAN | NO_WAN_8, ID_A_WAN | NO_WAN_9,
	// 筒子
	ID_A_PIN | NO_PIN_1, ID_A_PIN | NO_PIN_2, ID_A_PIN | NO_PIN_3, ID_A_PIN | NO_PIN_4, ID_A_PIN | NO_PIN_5, ID_A_PIN | NO_PIN_6, ID_A_PIN | NO_PIN_7, ID_A_PIN | NO_PIN_8, ID_A_PIN | NO_PIN_9,
	// 索子
	ID_A_SOU | NO_SOU_1, ID_A_SOU | NO_SOU_2, ID_A_SOU | NO_SOU_3, ID_A_SOU | NO_SOU_4, ID_A_SOU | NO_SOU_5, ID_A_SOU | NO_SOU_6, ID_A_SOU | NO_SOU_7, ID_A_SOU | NO_SOU_8, ID_A_SOU | NO_SOU_9,
	// 風牌
	ID_A_FON | NO_TON, ID_A_FON | NO_NAN, ID_A_FON | NO_SHA, ID_A_FON | NO_PE,
	// 三元牌
	ID_A_SANGEN | NO_HAKU, ID_A_SANGEN | NO_HATSU, ID_A_SANGEN | NO_CHUN };

	/** 種類の配列 */
	private final static int[] kinds = {
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
	private final static boolean[] ichikyuus = {
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
	private final static boolean[] tsuus = {
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

	/*
	 * コンストラクタ
	 */

	/**
	 * 空の牌を作成する。
	 */
	public Hai() {

	}

	/**
	 * IDから牌を作成する。
	 *
	 * @param id
	 *            ID
	 */
	public Hai(int id) {
		this.id = id;
	}

	/**
	 * 牌から牌を作成する。
	 *
	 * @param hai
	 *            牌
	 */
	public Hai(Hai hai) {
		copy(hai);
	}

	/*
	 * メソッド
	 */

	/**
	 * 牌をコピーする。
	 *
	 * @param hai
	 *            牌
	 */
	public void copy(Hai hai) {
		this.id = hai.id;
	}

	/**
	 * IDを取得する。
	 *
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 番号を取得する。
	 *
	 * @return 番号
	 */
	public int getNo() {
		return nos[id];
	}

	/**
	 * ID Format Aを取得する。
	 *
	 * @return ID Format A
	 */
	public int getIdA() {
		return idAs[id];
	}

	/**
	 * 種類を取得する。
	 *
	 * @return 種類
	 */
	public int getKind() {
		return kinds[id];
	}

	/**
	 * 一九牌フラグを取得する。
	 *
	 * @return 一九牌フラグ
	 */
	public boolean isIchikyuu() {
		return ichikyuus[id];
	}

	/**
	 * 字牌フラグを取得する。
	 *
	 * @return 字牌フラグ
	 */
	public boolean isTsuu() {
		return tsuus[id];
	}

	/**
	 * 一九字牌フラグを取得する。
	 *
	 * @return 一九字牌フラグ
	 */
	public boolean isYaotyuu() {
		return ichikyuus[id] | tsuus[id];
	}
}
