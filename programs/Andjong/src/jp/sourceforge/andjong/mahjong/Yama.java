package jp.sourceforge.andjong.mahjong;

import java.util.Random;

/**
 * 山を管理する。
 *
 * @author Yuji Urushibara
 *
 */
public class Yama {
	/** 山牌の配列の最大数 */
	private final static int YAMA_HAIS_MAX = 136;

	/** ツモ牌の配列の最大数 */
	private final static int TSUMO_HAIS_MAX = 122;

	/** リンシャン牌の配列の最大数 */
	private final static int RINSHAN_HAIS_MAX = 4;

	/** 各ドラ牌の配列の最大数 */
	private final static int DORA_HAIS_MAX = RINSHAN_HAIS_MAX + 1;

	/** 山牌の配列 */
	private Hai[] m_yamaHais = new Hai[YAMA_HAIS_MAX];

	/** ツモ牌の配列 */
	private Hai[] m_tsumoHais = new Hai[TSUMO_HAIS_MAX];

	/** リンシャン牌の配列 */
	private Hai[] m_rinshanHais = new Hai[RINSHAN_HAIS_MAX];

	/** リンシャン牌の位置 */
	private int m_iRinshanHais;

	/** ツモ牌のインデックス */
	private int m_iTsumoHais;

	/** 表ドラ牌の配列 */
	private Hai[] m_omoteDoraHais = new Hai[DORA_HAIS_MAX];

	/** 裏ドラ牌の配列 */
	private Hai[] m_uraDoraHais = new Hai[DORA_HAIS_MAX];

	{
		for (int i = Hai.ID_WAN_1; i < Hai.ID_ITEM_MAX; i++) {
			for (int j = 0; j < 4; j++) {
				m_yamaHais[(i * 4) + j] = new Hai(i);
			}
		}
	}

	/**
	 * 山を作成する。
	 */
	Yama() {
		setTsumoHaisStartIndex(0);
	}

	/**
	 * 洗牌する。
	 */
	void xipai() {
		Random random = new Random();
		Hai temp;

		for (int i = 0, j; i < YAMA_HAIS_MAX; i++) {
			j = random.nextInt(YAMA_HAIS_MAX);
			temp = m_yamaHais[i];
			m_yamaHais[i] = m_yamaHais[j];
			m_yamaHais[j] = temp;
		}
	}

	/**
	 * ツモ牌を取得する。
	 *
	 * @return ツモ牌
	 */
	Hai tsumo() {
		if (m_iTsumoHais >= TSUMO_HAIS_MAX) {
			return null;
		}

		Hai tsumoHai = new Hai(m_tsumoHais[m_iTsumoHais]);
		m_iTsumoHais++;

		return tsumoHai;
	}

	/**
	 * リンシャン牌を取得する。
	 *
	 * @return リンシャン牌
	 */
	Hai rinshanTsumo() {
		if (m_iRinshanHais >= RINSHAN_HAIS_MAX) {
			return null;
		}

		Hai rinshanHai = new Hai(m_rinshanHais[m_iRinshanHais]);
		m_iRinshanHais++;

		return rinshanHai;
	}

	/**
	 * 表ドラの配列を取得する。
	 *
	 * @return 表ドラの配列
	 */
	Hai[] getOmoteDoraHais() {
		int omoteDoraHaisLength = m_iRinshanHais + 1;
		Hai[] omoteDoraHais = new Hai[omoteDoraHaisLength];

		for (int i = 0; i < omoteDoraHaisLength; i++) {
			omoteDoraHais[i] = new Hai(this.m_omoteDoraHais[i]);
		}

		return omoteDoraHais;
	}

	/**
	 * 裏ドラの配列を取得する。
	 *
	 * @return 裏ドラの配列
	 */
	Hai[] getUraDoraHais() {
		int uraDoraHaisLength = m_iRinshanHais + 1;
		Hai[] uraDoraHais = new Hai[uraDoraHaisLength];

		for (int i = 0; i < uraDoraHaisLength; i++) {
			uraDoraHais[i] = new Hai(this.m_uraDoraHais[i]);
		}

		return uraDoraHais;
	}

	/**
	 * ツモ牌の開始位置を設定する。
	 *
	 * @param a_tsumoHaiStartIndex
	 *            ツモ牌の開始位置
	 */
	boolean setTsumoHaisStartIndex(int a_tsumoHaiStartIndex) {
		if (a_tsumoHaiStartIndex >= YAMA_HAIS_MAX) {
			return false;
		}

		int yamaHaisIdx = a_tsumoHaiStartIndex;

		for (int i = 0; i < TSUMO_HAIS_MAX; i++) {
			m_tsumoHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		m_iTsumoHais = 0;

		for (int i = 0; i < RINSHAN_HAIS_MAX; i++) {
			m_rinshanHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		m_iRinshanHais = 0;

		for (int i = 0; i < DORA_HAIS_MAX; i++) {
			m_omoteDoraHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}

			m_uraDoraHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		return true;
	}

	/**
	 * ツモ牌の残り数を取得する。
	 *
	 * @return ツモ牌の残り数
	 */
	int getTsumoNokori() {
		return TSUMO_HAIS_MAX - m_iTsumoHais;
	}
}
