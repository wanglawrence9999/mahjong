package jp.sourceforge.andjong;

/**
 * 牌を管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Hai {
	/** 萬子 */
	public final static int KIND_WAN = 0x00000010;
	/** 筒子 */
	public final static int KIND_PIN = 0x00000020;
	/** 索子 */
	public final static int KIND_SOU = 0x00000040;
	/** 数牌 */
	public final static int KIND_SHUU = 0x00000070;

	/** 風牌 */
	public final static int KIND_FON = 0x00000100;
	/** 三元牌 */
	public final static int KIND_SANGEN = 0x00000200;
	/** 字牌 */
	public final static int KIND_TSUU = 0x00000300;

	/** 牌の種類をマスクする */
	public final static int KIND_MASK = 0x0000000F;

	/**
	 * 牌番号
	 * <p>
	 * <dl>
	 * <dt>萬子</dt>
	 * <dd>KIND_WAN | 1-9</dd>
	 * <dt>筒子</dt>
	 * <dd>KIND_PIN | 1-9</dd>
	 * <dt>索子</dt>
	 * <dd>KIND_SOU | 1-9</dd>
	 * <dt>風牌</dt>
	 * <dd>KIND_FON | 1-4</dd>
	 * <dt>三元牌</dt>
	 * <dd>KIND_SANGEN | 1-3</dd>
	 * </dl>
	 * </p>
	 */
	private int id;

	/**
	 * 牌番号を取得する。
	 * 
	 * @return 牌番号
	 */
	public int getId() {
		return id;
	}

	/** プロパティ（赤牌） */
	public final static int PROPERTY_AKA = 0x00000001;

	/** プロパティ */
	private int property;

	/**
	 * プロパティを取得する。
	 * 
	 * @return プロパティ
	 */
	public int getProperty() {
		return property;
	}

	/**
	 * 空のHaiオブジェクトを作ります。
	 */
	public Hai() {

	}

	/**
	 * 牌番号を渡してHaiオブジェクトを作ります。
	 * 
	 * @param id
	 *            牌番号
	 */
	public Hai(int id) {
		this.id = id;
	}

	/**
	 * 牌番号とプロパティを渡してHaiオブジェクトを作ります。
	 * 
	 * @param id
	 *            牌番号
	 * @param property
	 *            プロパティ
	 */
	public Hai(int id, int property) {
		this.id = id;
		this.property = property;
	}

	/**
	 * Haiオブジェクトを渡してHaiオブジェクトを作ります。
	 * 
	 * @param hai
	 *            Haiオブジェクト
	 */
	public Hai(Hai hai) {
		this.id = hai.id;
		this.property = hai.property;
	}

	/**
	 * Haiオブジェクトをコピーします。
	 * 
	 * @param hai
	 *            Haiオブジェクト
	 */
	public void copy(Hai hai) {
		this.id = hai.id;
		this.property = hai.property;
	}
}
