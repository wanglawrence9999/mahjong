package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Hai.*;

import java.util.Random;

public class Yama {
	/** 山牌の最大数 */
	private final static int YAMA_MAX = 136;

	/** 山牌 */
	private Hai[] hais = new Hai[YAMA_MAX];

	/** ツモ位置の最後 */
	private final static int TSUMO_END = 122;

	/** ツモ位置 */
	private int tsumoIdx;

	/** リンシャンツモ位置の最後 */
	private final static int RINSHAN_END = 126;

	/** リンシャンツモ位置 */
	private int rinshanIdx;

	/**
	 * 山牌を初期化します。
	 */
	public Yama() {
		init();
	}

	/**
	 * 山牌を初期化します。
	 * <p>
	 * 力尽くで初期化します。
	 * </p>
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
	 * <p>
	 * ランダムで入れ替えます。
	 * </p>
	 */
	public void xipai() {
		Random random = new Random();
		Hai temp;

		for (int i = 0, j, size = YAMA_MAX; i < YAMA_MAX; i++) {
			j = random.nextInt(size);
			temp = hais[i];
			hais[i] = hais[j];
			hais[j] = temp;
		}

		tsumoIdx = 0;
		rinshanIdx = TSUMO_END;
	}

	/**
	 * ツモします。
	 * 
	 * @return ツモ牌
	 */
	public Hai tsumo() {
		if (tsumoIdx < TSUMO_END)
			return new Hai(hais[tsumoIdx++]);

		return null;
	}

	/**
	 * リンシャンツモします。
	 * 
	 * @return リンシャンツモ牌
	 */
	public Hai rinshan() {
		if (rinshanIdx < RINSHAN_END)
			return new Hai(hais[rinshanIdx++]);

		return null;
	}

	/**
	 * 表ドラ、槓ドラの配列を取得します。
	 * 
	 * @return 表ドラ、槓ドラの配列
	 */
	public Hai[] getDora() {
		int doraNum = 1 + (rinshanIdx - TSUMO_END);
		Hai[] dora = new Hai[doraNum];

		for (int i = 0, doraIdx = RINSHAN_END; i < doraNum; i++, doraIdx += 2)
			dora[i] = new Hai(hais[doraIdx]);

		return dora;
	}

	/**
	 * 表ドラ、槓ドラ、裏ドラ、槓ウラの配列を取得します。
	 * 
	 * @return 表ドラ、槓ドラ、裏ドラ、槓ウラの配列
	 */
	public Hai[] getDoraAll() {
		int doraNum = (1 + (rinshanIdx - TSUMO_END)) * 2;
		Hai[] dora = new Hai[doraNum];

		for (int i = 0, doraIdx = RINSHAN_END; i < doraNum; i++, doraIdx++)
			dora[i] = new Hai(hais[doraIdx]);

		return dora;
	}

	/**
	 * ツモの残り数を取得します。
	 * 
	 * @return ツモの残り数
	 */
	public int getTsumoRemain() {
		return TSUMO_END - tsumoIdx;
	}
}
