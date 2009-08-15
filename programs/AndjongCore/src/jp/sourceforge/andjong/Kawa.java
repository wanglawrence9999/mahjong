package jp.sourceforge.andjong;

/**
 * 河を管理するクラス
 *
 * @author Yuji Urushibara
 *
 */
public class Kawa {
	/** 捨牌の長さの最大値 */
	public final static int SUTEHAIS_LENGTH_MAX = 32;

	/** 捨牌 */
	private SuteHai[] suteHais = new SuteHai[SUTEHAIS_LENGTH_MAX];

	/** 捨牌の長さ */
	private int suteHaisLength;

	{
		for (int i = 0; i < SUTEHAIS_LENGTH_MAX; i++) {
			suteHais[i] = new SuteHai();
		}
	}

	/**
	 * 河を初期化する。
	 */
	public void initialize() {
		suteHaisLength = 0;
	}

	/**
	 * 捨牌に牌を追加する。
	 *
	 * @param hai
	 *            追加する牌
	 */
	public boolean add(Hai hai) {
		if (suteHaisLength >= SUTEHAIS_LENGTH_MAX) {
			return false;
		}

		Hai.copy(suteHais[suteHaisLength++], hai);
		return true;
	}

	/**
	 * 捨牌の最後の牌に、鳴きフラグを設定する。
	 *
	 * @param naki
	 *            鳴きフラグ
	 */
	public boolean setNaki(boolean naki) {
		if (suteHaisLength <= 0) {
			return false;
		}

		suteHais[suteHaisLength - 1].setNaki(naki);
		return true;
	}

	/**
	 * 捨牌の最後の牌に、リーチフラグを設定する。
	 *
	 * @param reach
	 *            リーチフラグ
	 */
	public boolean setReach(boolean reach) {
		if (suteHaisLength <= 0) {
			return false;
		}

		suteHais[suteHaisLength - 1].setReach(reach);
		return true;
	}

	/**
	 * 捨牌の最後の牌に、手出しフラグを設定する。
	 *
	 * @param tedashi
	 *            手出しフラグ
	 */
	public boolean setTedashi(boolean tedashi) {
		if (suteHaisLength <= 0) {
			return false;
		}

		suteHais[suteHaisLength - 1].setTedashi(tedashi);
		return true;
	}

	/**
	 * 河オブジェクトをコピーする。
	 *
	 * @param kawa
	 *            河
	 */
	void copy(Kawa kawa) {
		this.suteHaisLength = kawa.copySuteHai(this.suteHais);
	}

	/**
	 * 河を取得する。
	 *
	 * @return 河
	 */
	SuteHai[] getSuteHais() {
		return suteHais;
	}

	/**
	 * 河の長さを取得する。
	 *
	 * @return 河の長さ
	 */
	int getSuteHaiLength() {
		return suteHaisLength;
	}

	/**
	 * 河をコピーする。
	 *
	 * @param SuteHais
	 *            河
	 * @return 河の長さ
	 */
	int copySuteHai(SuteHai[] SuteHais) {
		for (int i = 0; i < this.suteHaisLength; i++)
			Hai.copy(SuteHais[i], this.suteHais[i]);
		return this.suteHaisLength;
	}
}
