package jp.sourceforge.andjong;

import static jp.sourceforge.andjong.Hai.*;

/**
 * 手牌を管理するクラスです。
 * 
 * @author Yuji Urushibara
 * 
 */
public class Tehai {
	/** 純手牌の最大数 */
	private final static int JYUNTEHAI_MAX = 13;

	/**
	 * 純手牌
	 * <p>
	 * ソートされています。
	 * </p>
	 */
	public Hai[] jyunTehai = new Hai[JYUNTEHAI_MAX];

	/** 純手牌の長さ */
	public int jyunTehaiLength;

	/** 明順の配列の長さ */
	public int minshunsLength;

	/** 明順の配列 */
	public Hai[][] minshuns = new Hai[4][3];

	/** 明刻の配列の長さ */
	public int minkousLength;

	/** 明刻の配列 */
	public Hai[][] minkous = new Hai[4][3];

	/** 明槓の配列の長さ */
	public int minkansLength;

	/** 明槓の配列 */
	public Hai[][] minkans = new Hai[4][4];

	/** 暗槓の配列の長さ */
	public int ankansLength;

	/** 暗槓の配列 */
	public Hai[][] ankans = new Hai[4][4];

	{
		for (int i = 0; i < JYUNTEHAI_MAX; i++)
			jyunTehai[i] = new Hai();

		for (int i = 0; i < minshuns.length; i++)
			for (int j = 0; j < minshuns[i].length; j++)
				minshuns[i][j] = new Hai();

		for (int i = 0; i < minkous.length; i++)
			for (int j = 0; j < minkous[i].length; j++)
				minkous[i][j] = new Hai();

		for (int i = 0; i < minkans.length; i++)
			for (int j = 0; j < minkans[i].length; j++)
				minkans[i][j] = new Hai();

		for (int i = 0; i < ankans.length; i++)
			for (int j = 0; j < ankans[i].length; j++)
				ankans[i][j] = new Hai();
	}

	/**
	 * 手牌を初期化します。
	 */
	public void init() {
		jyunTehaiLength = 0;
		minshunsLength = 0;
		minkousLength = 0;
		minkansLength = 0;
		ankansLength = 0;
	}

	/**
	 * 純手牌に牌を追加する。
	 * <p>
	 * 力尽くでソートします。<br>
	 * Haiオブジェクトをコピーします。<br>
	 * TODO もっとスマートな方法がありそうです。
	 * </p>
	 * 
	 * @param hai
	 *            追加する牌
	 */
	public void addJyunTehai(Hai hai) {
		if (jyunTehaiLength >= JYUNTEHAI_MAX)
			return;

		int i;
		for (i = jyunTehaiLength; i > 0; i--) {
			if (jyunTehai[i - 1].id == hai.id) {
				if (jyunTehai[i - 1].property < (hai.property & PROPERTY_AKA))
					break;
			} else if (jyunTehai[i - 1].id < hai.id)
				break;

			jyunTehai[i].copy(jyunTehai[i - 1]);
		}

		jyunTehai[i].copy(hai);
		jyunTehaiLength++;
	}

	/**
	 * 純手牌から指定位置の牌を削除する。
	 * 
	 * @param idx
	 *            指定位置
	 */
	public void removeJyunTehai(int idx) {
		if (idx >= JYUNTEHAI_MAX)
			return;

		for (int i = idx; i < jyunTehaiLength - 1; i++) {
			jyunTehai[i].copy(jyunTehai[i + 1]);
		}

		jyunTehaiLength--;
	}

	/**
	 * 明順を追加する。
	 * <p>
	 * Haiオブジェクトをコピーします。
	 * </p>
	 * 
	 * @param minshun
	 *            明順
	 */
	public void addMinshun(Hai[] minshun) {
		if (minshunsLength >= 4)
			return;

		minshuns[minshunsLength][0].copy(minshun[0]);
		minshuns[minshunsLength][1].copy(minshun[1]);
		minshuns[minshunsLength++][2].copy(minshun[2]);
	}

	/**
	 * 明刻を追加する。
	 * <p>
	 * Haiオブジェクトをコピーします。
	 * </p>
	 * 
	 * @param minkou
	 *            明刻
	 */
	public void addMinkou(Hai[] minkou) {
		if (minkousLength >= 4)
			return;

		minkous[minkousLength][0].copy(minkou[0]);
		minkous[minkousLength][1].copy(minkou[1]);
		minkous[minkousLength++][2].copy(minkou[2]);
	}

	/**
	 * 明槓を追加する。
	 * <p>
	 * Haiオブジェクトをコピーします。
	 * </p>
	 * 
	 * @param minkan
	 *            明槓
	 */
	public void addMinkan(Hai[] minkan) {
		if (minkansLength >= 4)
			return;

		minkans[minkansLength][0].copy(minkan[0]);
		minkans[minkansLength][1].copy(minkan[1]);
		minkans[minkansLength][2].copy(minkan[2]);
		minkans[minkansLength++][3].copy(minkan[3]);
	}

	/**
	 * 暗槓を追加する。
	 * <p>
	 * Haiオブジェクトをコピーします。
	 * </p>
	 * 
	 * @param ankan
	 *            暗槓
	 */
	public void addankan(Hai[] ankan) {
		if (ankansLength >= 4)
			return;

		ankans[ankansLength][0].copy(ankan[0]);
		ankans[ankansLength][1].copy(ankan[1]);
		ankans[ankansLength][2].copy(ankan[2]);
		ankans[ankansLength++][3].copy(ankan[3]);
	}
}
