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
	private KawaHai[] hais = new KawaHai[KAWA_MAX];

	/** 河の長さ */
	private int kawaLength;

	{
		for (int i = 0; i < hais.length; i++)
			hais[i] = new KawaHai();
	}

	/**
	 * 河を初期化する。
	 */
	void init() {
		kawaLength = 0;
	}

	/**
	 * 河に牌を追加する。
	 * 
	 * @param addHai
	 *            追加する牌
	 */
	void add(Hai addHai) {
		hais[kawaLength].copy(addHai);
		kawaLength++;
	}

	/**
	 * 河に牌を追加する。
	 * 
	 * @param addHai
	 *            追加する牌
	 * @param property
	 *            追加するプロパティ
	 */
	void add(Hai addHai, int property) {
		hais[kawaLength].copy(addHai);
		addProperty(property);
		kawaLength++;
	}

	/**
	 * 河の最後の牌にプロパティを追加する。
	 * 
	 * @param property
	 *            追加するプロパティ
	 */
	void addProperty(int property) {
		hais[kawaLength].addKawaProperty(property);
	}

	/**
	 * 河オブジェクトをコピーする。
	 * 
	 * @param kawa
	 *            河
	 */
	void copy(Kawa kawa) {
		this.kawaLength = copyKawaHai(this.hais);
	}

	/**
	 * 河をコピーする。
	 * 
	 * @param hais
	 *            河
	 * @return 河の長さ
	 */
	int copyKawaHai(KawaHai[] hais) {
		for (int i = 0; i < this.kawaLength; i++)
			hais[i].copy(this.hais[i]);
		return this.kawaLength;
	}
}
