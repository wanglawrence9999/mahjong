package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Hai.*;

import java.util.Random;

/**
 * 山を管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Yama {
	/** 山牌の最大数 */
	private final static int YAMA_MAX = 136;

	/** 山牌 */
	private Hai[] hais = new Hai[YAMA_MAX];

	/** ツモの最大数 */
	private final static int TSUMO_MAX = 122;

	/** ツモ回数 */
	private int tsumoCount;

	/** ツモ位置 */
	private int tsumoIdx;

	/** リンシャンツモの最大数 */
	private final static int RINSHAN_MAX = 4;

	/** リンシャンツモ回数 */
	private int rinshanCount;

	/** リンシャンツモ位置 */
	private int rinshanIdx = TSUMO_MAX;

	/**
	 * 山を初期化します。
	 */
	public Yama() {
		init();
	}

	/**
	 * 山を初期化します。
	 */
	private void init() {
		int haiIdx = 0;
		int id;
		int max;
		int loop;

		for (id = KIND_WAN + 1, max = id + 9; id < max; id++)
			for (loop = 0; loop < 4; loop++, haiIdx++)
				hais[haiIdx] = new Hai(id);

		for (id = KIND_PIN + 1, max = id + 9; id < max; id++)
			for (loop = 0; loop < 4; loop++, haiIdx++)
				hais[haiIdx] = new Hai(id);

		for (id = KIND_SOU + 1, max = id + 9; id < max; id++)
			for (loop = 0; loop < 4; loop++, haiIdx++)
				hais[haiIdx] = new Hai(id);

		for (id = KIND_FON + 1, max = id + 4; id < max; id++)
			for (loop = 0; loop < 4; loop++, haiIdx++)
				hais[haiIdx] = new Hai(id);

		for (id = KIND_SANGEN + 1, max = id + 3; id < max; id++)
			for (loop = 0; loop < 4; loop++, haiIdx++)
				hais[haiIdx] = new Hai(id);
	}

	/**
	 * 洗牌をします。
	 */
	void xipai() {
		Random random = new Random();
		Hai temp;

		// ランダムで入れ替えます。
		for (int i = 0, j, size = YAMA_MAX; i < YAMA_MAX; i++) {
			j = random.nextInt(size);
			temp = hais[i];
			hais[i] = hais[j];
			hais[j] = temp;
		}

		tsumoCount = 0;
		rinshanCount = 0;
	}

	/**
	 * ツモします。
	 * 
	 * @return ツモ牌
	 */
	Hai tsumo() {
		if (tsumoCount >= TSUMO_MAX) {
			return null;
		}
		tsumoCount++;

		tsumoIdx++;
		if (tsumoIdx >= YAMA_MAX) {
			tsumoIdx = 0;
		}

		return new Hai(hais[tsumoIdx]);
	}

	/**
	 * リンシャンツモします。
	 * 
	 * @return リンシャンツモ牌
	 */
	Hai rinshan() {
		if (rinshanCount >= RINSHAN_MAX) {
			return null;
		}
		rinshanCount++;

		rinshanIdx++;
		if (rinshanIdx >= YAMA_MAX) {
			rinshanIdx = 0;
		}

		return new Hai(hais[rinshanIdx]);
	}

	/**
	 * 表ドラ、槓ドラの配列を取得します。
	 * 
	 * @return 表ドラ、槓ドラの配列
	 */
	Hai[] getDoras() {
		int doraNum = 1 + rinshanCount;
		Hai[] dora = new Hai[doraNum];

		for (int i = 0, doraIdx = rinshanIdx; i < doraNum; i++, doraIdx += 2) {
			if (doraIdx >= YAMA_MAX) {
				doraIdx = 0;
			}
			dora[i] = new Hai(hais[doraIdx]);
		}

		return dora;
	}

	/**
	 * 表ドラ、槓ドラ、裏ドラ、槓ウラの配列を取得します。
	 * 
	 * @return 表ドラ、槓ドラ、裏ドラ、槓ウラの配列
	 */
	Hai[] getDorasAll() {
		int doraNum = (1 + rinshanCount) * 2;
		Hai[] dora = new Hai[doraNum];

		for (int i = 0, doraIdx = rinshanIdx; i < doraNum; i++, doraIdx++) {
			if (doraIdx >= YAMA_MAX) {
				doraIdx = 0;
			}
			dora[i] = new Hai(hais[doraIdx]);
		}

		return dora;
	}

	/**
	 * ツモの残り数を取得します。
	 * 
	 * @return ツモの残り数
	 */
	int getTsumoRemain() {
		return TSUMO_MAX - tsumoCount;
	}

	/**
	 * 山に割れ目を設定します。
	 * 
	 * @param sais
	 *            サイコロの配列
	 */
	void setWareme(Sai[] sais) {
		int sum = sais[0].getNo() + sais[1].getNo() - 1;
		tsumoIdx = ((sum % 4) * 36) + sum;

		rinshanIdx = tsumoIdx + TSUMO_MAX;
		if (rinshanIdx >= YAMA_MAX) {
			rinshanIdx -= YAMA_MAX;
		}
	}
}
