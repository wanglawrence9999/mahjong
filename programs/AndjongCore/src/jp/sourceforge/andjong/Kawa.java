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
	public final static int KAWA_MAX = 25;

	/** 河 */
	private KawaHai[] kawaHais = new KawaHai[KAWA_MAX];

	/** 河の長さ */
	private int kawaLength;

	{
		for (int i = 0; i < kawaHais.length; i++)
			kawaHais[i] = new KawaHai();
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
		kawaHais[kawaLength].copy(addHai);
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
		kawaHais[kawaLength].copy(addHai);
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
		kawaHais[kawaLength].addKawaProperty(property);
	}

	/**
	 * 河オブジェクトをコピーする。
	 * 
	 * @param kawa
	 *            河
	 */
	void copy(Kawa kawa) {
		this.kawaLength = kawa.copyKawaHai(this.kawaHais);
	}

	/**
	 * 河をコピーする。
	 * 
	 * @param kawaHais
	 *            河
	 * @return 河の長さ
	 */
	int copyKawaHai(KawaHai[] kawaHais) {
		for (int i = 0; i < this.kawaLength; i++)
			kawaHais[i].copy(this.kawaHais[i]);
		return this.kawaLength;
	}
}
