package jp.sourceforge.andjong;

/**
 * 河を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Kawa {
	/**
	 * 捨牌の配列の長さの最大値
	 * <p>
	 * TODO 理論上の最大値が不明です。
	 * </p>
	 */
	public final static int SUTEHAIS_LENGTH_MAX = 32;

	/** 捨牌の配列 */
	private SuteHai[] suteHais = new SuteHai[SUTEHAIS_LENGTH_MAX];

	/** 捨牌の配列の長さ */
	private int suteHaisLength;

	/**
	 * 河を作成する。
	 */
	public Kawa() {
		for (int i = 0; i < SUTEHAIS_LENGTH_MAX; i++) {
			suteHais[i] = new SuteHai();
		}

		initialize();
	}

	/**
	 * 河を初期化する。
	 */
	public void initialize() {
		suteHaisLength = 0;
	}

	/**
	 * 河をコピーする。
	 *
	 * @param destKawa
	 *            コピー先の河
	 * @param srcKawa
	 *            コピー元の河
	 */
	public static void copy(Kawa destKawa, Kawa srcKawa) {
		destKawa.suteHaisLength = srcKawa.suteHaisLength;
		copySuteHai(destKawa.suteHais, srcKawa.suteHais,
				destKawa.suteHaisLength);
	}

	/**
	 * 捨牌の配列を取得する。
	 *
	 * @return 捨牌の配列
	 */
	public SuteHai[] getSuteHais() {
		return suteHais;
	}

	/**
	 * 捨牌の配列の長さを取得する。
	 *
	 * @return 捨牌の配列の長さ
	 */
	public int getSuteHaisLength() {
		return suteHaisLength;
	}

	/**
	 * 捨牌の配列に牌を追加する。
	 *
	 * @param hai
	 *            追加する牌
	 */
	public boolean add(Hai hai) {
		if (suteHaisLength >= SUTEHAIS_LENGTH_MAX) {
			return false;
		}

		Hai.copy(suteHais[suteHaisLength], hai);
		suteHaisLength++;

		return true;
	}

	/**
	 * 捨牌の配列の最後の牌に、鳴きフラグを設定する。
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
	 * 捨牌の配列の最後の牌に、リーチフラグを設定する。
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
	 * 捨牌の配列の最後の牌に、手出しフラグを設定する。
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
	 * 捨牌の配列をコピーする。
	 *
	 * @param destSuteHais
	 *            コピー先の捨牌の配列
	 * @param srcSuteHais
	 *            コピー元の捨牌の配列
	 * @param length
	 *            コピーする長さ
	 * @return 結果
	 */
	public static boolean copySuteHai(SuteHai[] destSuteHais,
			SuteHai[] srcSuteHais, int length) {
		if (length >= SUTEHAIS_LENGTH_MAX) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			Hai.copy(destSuteHais[i], srcSuteHais[i]);
		}

		return true;
	}
}
