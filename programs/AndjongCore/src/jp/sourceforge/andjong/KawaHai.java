package jp.sourceforge.andjong;

public class KawaHai extends Hai {
	/** 河牌のプロパティ */
	private int kawaProperty;

	/**
	 * 河牌のプロパティを取得する。
	 * 
	 * @return　河牌のプロパティ
	 */
	public int getKawaProperty() {
		return kawaProperty;
	}

	/**
	 * 河牌のプロパティを設定する。
	 * 
	 * @param kawaProperty
	 *            　河牌のプロパティ
	 */
	void setKawaProperty(int kawaProperty) {
		this.kawaProperty = kawaProperty;
	}

	/**
	 * 河牌のプロパティを追加する。
	 * 
	 * @param kawaProperty
	 *            河牌のプロパティ
	 */
	void addKawaProperty(int kawaProperty) {
		this.kawaProperty |= kawaProperty;
	}
}
