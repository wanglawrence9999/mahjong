package jp.sourceforge.andjong;

import java.util.Random;

/**
 * 山を管理するクラス
 * 
 * @author Yuji Urushibara
 * 
 */
public class Yama {
	/** 牌の数 */
	private final static int HAIS_NUM = 136;

	/** 牌 */
	private Hai[] hais = new Hai[HAIS_NUM];

	/** ツモ牌の数 */
	private final static int TSUMO_HAIS_NUM = 122;

	/** ツモ牌 */
	private Hai[] tsumoHais = new Hai[TSUMO_HAIS_NUM];

	/** ツモ牌の位置 */
	private int tsumoHaisIdx;

	/** リンシャン牌の数 */
	private final static int RINSHAN_HAIS_MAX = 4;

	/** リンシャン牌 */
	private Hai[] rinshanHais = new Hai[RINSHAN_HAIS_MAX];

	/** リンシャン牌の位置 */
	private int rinshanHaisIdx;

	/** ドラ牌の数 */
	private final static int DORA_HAIS_MAX = RINSHAN_HAIS_MAX + 1;

	/** ドラ牌 */
	private Hai[] doraHais = new Hai[DORA_HAIS_MAX];

	/** 裏ドラ牌 */
	private Hai[] uraDoraHais = new Hai[DORA_HAIS_MAX];

	/**
	 * 山を作成する。
	 */
	Yama() {
		initialize();
	}

	/**
	 * 牌を初期化する。
	 */
	private void initialize() {
		for (int id = Hai.ID_WAN_1, idx = 0; id <= Hai.ID_CHUN; id++) {
			for (int i = 0; i < 4; i++, idx++) {
				hais[idx] = new Hai(id);
			}
		}
	}

	/**
	 * 洗牌する。
	 */
	void xipai() {
		Random random = new Random();
		Hai temp;

		for (int i = 0, j; i < HAIS_NUM; i++) {
			j = random.nextInt(HAIS_NUM);
			temp = hais[i];
			hais[i] = hais[j];
			hais[j] = temp;
		}
	}

	/**
	 * ツモ牌を取得する。
	 * 
	 * @return ツモ牌
	 */
	Hai tsumo() {
		if (tsumoHaisIdx >= TSUMO_HAIS_NUM) {
			return null;
		}
		return new Hai(tsumoHais[tsumoHaisIdx++]);
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
		return new Hai(rinshanHais[rinshanHaisIdx++]);
	}

	/**
	 * 表ドラ、槓ドラの配列を取得する。
	 * 
	 * @return 表ドラ、槓ドラの配列
	 */
	Hai[] getDoraHais() {
		int doraHaisLength = rinshanHaisIdx + 1;
		Hai[] doraHais = new Hai[doraHaisLength];

		for (int i = 0; i < doraHaisLength; i++) {
			doraHais[i] = new Hai(this.doraHais[i]);
		}

		return doraHais;
	}

	/**
	 * 裏ドラ、槓ウラの配列を取得する。
	 * 
	 * @return 裏ドラ、槓ウラの配列
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
	 * ツモ牌の開始位置を設定します。
	 * 
	 * @param startTsumoHaiIdx
	 *            ツモ牌の開始位置
	 */
	void setStartTsumoHaisIdx(int startTsumoHaiIdx) {
		int haisIdx = startTsumoHaiIdx;

		for (int tsumoIdx = 0; tsumoIdx < TSUMO_HAIS_NUM; tsumoIdx++) {
			if (haisIdx >= HAIS_NUM) {
				haisIdx = 0;
			}
			tsumoHais[tsumoIdx] = hais[haisIdx++];
		}

		for (int rinshanHaisIdx = 0; rinshanHaisIdx < RINSHAN_HAIS_MAX; rinshanHaisIdx++) {
			if (haisIdx >= HAIS_NUM) {
				haisIdx = 0;
			}
			rinshanHais[rinshanHaisIdx] = hais[haisIdx++];
		}

		for (int doraHaisIdx = 0; doraHaisIdx < DORA_HAIS_MAX; doraHaisIdx++) {
			if (haisIdx >= HAIS_NUM) {
				haisIdx = 0;
			}
			doraHais[doraHaisIdx] = hais[haisIdx++];

			if (haisIdx >= HAIS_NUM) {
				haisIdx = 0;
			}
			uraDoraHais[doraHaisIdx] = hais[haisIdx++];
		}
	}

	/**
	 * ツモの残り数を取得する。
	 * 
	 * @return ツモの残り数
	 */
	int getTsumoNokori() {
		return TSUMO_HAIS_NUM - tsumoHaisIdx;
	}

	/**
	 * 山に割れ目を設定します。
	 * 
	 * @param sais
	 *            サイコロの配列
	 */
	void setWareme(Sai[] sais) {
		int sum = sais[0].getNo() + sais[1].getNo() - 1;
		tsumoHaisIdx = ((sum % 4) * 36) + sum;

		rinshanHaisIdx = tsumoHaisIdx + TSUMO_HAIS_NUM;
		if (rinshanHaisIdx >= HAIS_NUM) {
			rinshanHaisIdx -= HAIS_NUM;
		}

		tsumoHaisIdx = 0;
		rinshanHaisIdx = 0;

		{
			int i;
			for (i = 0; i < TSUMO_HAIS_NUM; i++) {
				tsumoHais[i] = hais[i];
			}

			for (; i < TSUMO_HAIS_NUM + RINSHAN_HAIS_MAX; i++) {
				rinshanHais[i - TSUMO_HAIS_NUM] = hais[i];
			}

			for (int j = 0; j < DORA_HAIS_MAX; j++) {
				doraHais[j] = hais[i++];
				uraDoraHais[j] = hais[i++];
			}
		}
		setStartTsumoHaisIdx(0);
		setStartTsumoHaisIdx(120);
	}
}
