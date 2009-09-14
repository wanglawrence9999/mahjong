package jp.sourceforge.andjong;

import java.util.Random;

/**
 * 山を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Yama {
	/** 山牌の最大数 */
	private final static int YAMAHAIS_MAX = 136;

	/** 山牌の配列 */
	private Hai[] yamaHais = new Hai[YAMAHAIS_MAX];

	/** ツモ牌の最大数 */
	private final static int TSUMO_HAIS_MAX = 122;

	/** ツモ牌の配列 */
	private Hai[] tsumoHais = new Hai[TSUMO_HAIS_MAX];

	/** ツモ牌の位置 */
	private int tsumoHaisIdx;

	/** リンシャン牌の最大数 */
	private final static int RINSHAN_HAIS_MAX = 4;

	/** リンシャン牌の配列 */
	private Hai[] rinshanHais = new Hai[RINSHAN_HAIS_MAX];

	/** リンシャン牌の位置 */
	private int rinshanHaisIdx;

	/** 各ドラ牌の最大数 */
	private final static int DORA_HAIS_MAX = RINSHAN_HAIS_MAX + 1;

	/** 表ドラ牌の配列 */
	private Hai[] omoteDoraHais = new Hai[DORA_HAIS_MAX];

	/** 裏ドラ牌の配列 */
	private Hai[] uraDoraHais = new Hai[DORA_HAIS_MAX];

	/**
	 * 山を作成する。
	 */
	Yama() {
		initialize();
		setTsumoHaisStartIdx(0);
	}

	/**
	 * 山牌を初期化する。
	 */
	private void initialize() {
		for (int id = Hai.ID_WAN_1, idx = 0; id <= Hai.ID_CHUN; id++) {
			for (int i = 0; i < 4; i++, idx++) {
				yamaHais[idx] = new Hai(id);
			}
		}
	}

	/**
	 * 洗牌する。
	 */
	void xipai() {
		Random random = new Random();
		Hai temp;

		for (int i = 0, j; i < YAMAHAIS_MAX; i++) {
			j = random.nextInt(YAMAHAIS_MAX);
			temp = yamaHais[i];
			yamaHais[i] = yamaHais[j];
			yamaHais[j] = temp;
		}
	}

	/**
	 * ツモ牌を取得する。
	 *
	 * @return ツモ牌
	 */
	Hai tsumo() {
		if (tsumoHaisIdx >= TSUMO_HAIS_MAX) {
			return null;
		}

		Hai tsumoHai = new Hai(tsumoHais[tsumoHaisIdx]);
		tsumoHaisIdx++;

		return tsumoHai;
	}

	/**
	 * リンシャン牌を取得する。
	 *
	 * @return リンシャン牌
	 */
	Hai rinshanTsumo() {
		if (rinshanHaisIdx >= RINSHAN_HAIS_MAX) {
			return null;
		}

		Hai rinshanHai = new Hai(rinshanHais[rinshanHaisIdx]);
		rinshanHaisIdx++;

		return rinshanHai;
	}

	/**
	 * 表ドラの配列を取得する。
	 *
	 * @return 表ドラの配列
	 */
	Hai[] getOmoteDoraHais() {
		int omoteDoraHaisLength = rinshanHaisIdx + 1;
		Hai[] omoteDoraHais = new Hai[omoteDoraHaisLength];

		for (int i = 0; i < omoteDoraHaisLength; i++) {
			omoteDoraHais[i] = new Hai(this.omoteDoraHais[i]);
		}

		return omoteDoraHais;
	}

	/**
	 * 裏ドラの配列を取得する。
	 *
	 * @return 裏ドラの配列
	 */
	Hai[] getUraDoraHais() {
		int uraDoraHaisLength = rinshanHaisIdx + 1;
		Hai[] uraDoraHais = new Hai[uraDoraHaisLength];

		for (int i = 0; i < uraDoraHaisLength; i++) {
			uraDoraHais[i] = new Hai(this.uraDoraHais[i]);
		}

		return uraDoraHais;
	}

	/**
	 * ツモ牌の開始位置を設定する。
	 *
	 * @param tsumoHaiStartIdx
	 *            ツモ牌の開始位置
	 */
	boolean setTsumoHaisStartIdx(int tsumoHaiStartIdx) {
		if (tsumoHaiStartIdx >= YAMAHAIS_MAX) {
			return false;
		}

		int yamaHaisIdx = tsumoHaiStartIdx;

		for (int i = 0; i < TSUMO_HAIS_MAX; i++) {
			tsumoHais[i] = yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMAHAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		for (int i = 0; i < RINSHAN_HAIS_MAX; i++) {
			rinshanHais[i] = yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMAHAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		for (int i = 0; i < DORA_HAIS_MAX; i++) {
			omoteDoraHais[i] = yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMAHAIS_MAX) {
				yamaHaisIdx = 0;
			}

			uraDoraHais[i] = yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMAHAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		tsumoHaisIdx = 0;
		rinshanHaisIdx = 0;

		return true;
	}

	/**
	 * ツモ牌の残り数を取得する。
	 *
	 * @return ツモ牌の残り数
	 */
	int getTsumoNokori() {
		return TSUMO_HAIS_MAX - tsumoHaisIdx;
	}
}
