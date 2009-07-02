package jp.sourceforge.andjong;

/**
 * 河を管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Kawa {
	/** プロパティ（リーチ） */
	public final static int PROPERTY_REACH = 0x00000002;
	/** プロパティ（鳴き） */
	public final static int PROPERTY_NAKI = 0x00000004;
	/** プロパティ（手出し） */
	public final static int PROPERTY_TEDASHI = 0x00000008;

	/** 河の最大数 */
	private final static int KAWA_MAX = 25;

	/** 河 */
	public Hai[] hai = new Hai[KAWA_MAX];

	/** 河の長さ */
	public int kawaLength;

	{
		for (int i = 0; i < hai.length; i++)
			hai[i] = new Hai(0);
	}

	/**
	 * 河を初期化する。
	 */
	public void init() {
		kawaLength = 0;
	}

	/**
	 * 河に牌を追加する。
	 * 
	 * @param addHai
	 *            追加する牌
	 */
	public void add(Hai addHai) {
		hai[kawaLength].id = addHai.id;
		hai[kawaLength++].property = addHai.property;
	}

	/**
	 * 河に牌を追加する。
	 * 
	 * @param addHai
	 *            追加する牌
	 * @param property
	 *            追加するプロパティ
	 */
	public void add(Hai addHai, int property) {
		hai[kawaLength].id = addHai.id;
		hai[kawaLength++].property = addHai.property | property;
	}

	/**
	 * 河の最後の牌にプロパティを追加する。
	 * 
	 * @param property
	 *            追加するプロパティ
	 */
	public void addProperty(int property) {
		hai[kawaLength].property |= property;
	}
}
