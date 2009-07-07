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
	public final static int JYUNTEHAI_MAX = 14;

	/**
	 * 純手牌
	 * <p>
	 * ソートされています。
	 * </p>
	 */
	private Hai[] jyunTehai = new Hai[JYUNTEHAI_MAX];

	/** 純手牌の長さ */
	private int jyunTehaiLength;

	/** 明順の配列の長さ */
	private int minshunsLength;

	/** 明順の配列 */
	private Hai[][] minshuns = new Hai[4][3];

	/** 明刻の配列の長さ */
	private int minkousLength;

	/** 明刻の配列 */
	private Hai[][] minkous = new Hai[4][3];

	/** 明槓の配列の長さ */
	private int minkansLength;

	/** 明槓の配列 */
	private Hai[][] minkans = new Hai[4][4];

	/** 暗槓の配列の長さ */
	private int ankansLength;

	/** 暗槓の配列 */
	private Hai[][] ankans = new Hai[4][4];

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
	 * 手牌オブジェクトをコピーする。
	 * 
	 * @param tehai
	 *            手牌オブジェクト
	 * @param jyunTehaiCopy
	 *            純手牌のコピー許可
	 */
	public void copy(Tehai tehai, boolean jyunTehaiCopy) {
		init();
		if (jyunTehaiCopy == true) {
			this.jyunTehaiLength = tehai.copyJyunTehai(this.jyunTehai);
		}
		this.minshunsLength = tehai.copyMinshuns(this.minshuns);
		this.minkousLength = tehai.copyMinkous(this.minkous);
		this.minkansLength = tehai.copyMinkans(this.minkans);
		this.ankansLength = tehai.copyAnkans(this.ankans);
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
			if (jyunTehai[i - 1].getId() == hai.getId()) {
				if (jyunTehai[i - 1].getProperty() < (hai.getProperty() & PROPERTY_AKA))
					break;
			} else if (jyunTehai[i - 1].getId() < hai.getId())
				break;

			jyunTehai[i].copy(jyunTehai[i - 1]);
		}

		jyunTehai[i].copy(hai);
		jyunTehaiLength++;
	}

	/**
	 * 純手牌のインデックスを指定して、牌をコピーする。
	 * 
	 * @param hai
	 *            牌
	 * @param idx
	 *            純手牌のインデックス
	 */
	public void copyJyunTehaiIdx(Hai hai, int idx) {
		hai.copy(jyunTehai[idx]);
	}

	/**
	 * 純手牌を取得する。
	 * 
	 * @return 純手牌
	 */
	public Hai[] getJyunTehai() {
		return jyunTehai;
	}

	/**
	 * 純手牌の長さを取得する。
	 * 
	 * @return 純手牌の長さ
	 */
	public int getJyunTehaiLength() {
		return jyunTehaiLength;
	}

	/**
	 * 純手牌をコピーする。
	 * 
	 * @param jyunTehai
	 *            純手牌
	 * @return 純手牌の長さ
	 */
	public int copyJyunTehai(Hai[] jyunTehai) {
		for (int i = 0; i < this.jyunTehaiLength; i++)
			jyunTehai[i].copy(this.jyunTehai[i]);
		return this.jyunTehaiLength;
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
	 * 明順の配列を取得する。
	 * 
	 * @return 明順の配列
	 */
	public Hai[][] getMinshuns() {
		return minshuns;
	}

	/**
	 * 明順の配列の長さを取得する。
	 * 
	 * @return 明順の配列の長さ
	 */
	public int getMinshunsLength() {
		return minshunsLength;
	}

	/**
	 * 明順の配列をコピーする。
	 * 
	 * @param minshuns
	 *            明順の配列
	 * @return 明順の配列の長さ
	 */
	public int copyMinshuns(Hai[][] minshuns) {
		for (int i = 0; i < this.minshunsLength; i++) {
			minshuns[i][0].copy(this.minshuns[i][0]);
			minshuns[i][1].copy(this.minshuns[i][1]);
			minshuns[i][2].copy(this.minshuns[i][2]);
		}
		return this.minshunsLength;
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
	 * 明刻の配列を取得する。
	 * 
	 * @return 明刻の配列
	 */
	public Hai[][] getMinkous() {
		return minkous;
	}

	/**
	 * 明刻の配列の長さを取得する。
	 * 
	 * @return 明刻の配列の長さ
	 */
	public int getMinkousLength() {
		return minkousLength;
	}

	/**
	 * 明刻の配列をコピーする。
	 * 
	 * @param minkous
	 *            明刻の配列
	 * @return 明刻の配列の長さ
	 */
	public int copyMinkous(Hai[][] minkous) {
		for (int i = 0; i < this.minkousLength; i++) {
			minkous[i][0].copy(this.minkous[i][0]);
			minkous[i][1].copy(this.minkous[i][1]);
			minkous[i][2].copy(this.minkous[i][2]);
		}
		return this.minkousLength;
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
	 * 明槓の配列を取得する。
	 * 
	 * @return 明槓の配列
	 */
	public Hai[][] getMinkans() {
		return minkans;
	}

	/**
	 * 明槓の配列の長さを取得する。
	 * 
	 * @return 明槓の配列の長さ
	 */
	public int getMinkansLength() {
		return minkansLength;
	}

	/**
	 * 明槓の配列をコピーする。
	 * 
	 * @param minkans
	 *            明槓の配列
	 * @return 明槓の配列の長さ
	 */
	public int copyMinkans(Hai[][] minkans) {
		for (int i = 0; i < this.minkansLength; i++) {
			minkans[i][0].copy(this.minkans[i][0]);
			minkans[i][1].copy(this.minkans[i][1]);
			minkans[i][2].copy(this.minkans[i][2]);
			minkans[i][3].copy(this.minkans[i][3]);
		}
		return this.minkansLength;
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
	public void addAnkan(Hai[] ankan) {
		if (ankansLength >= 4)
			return;

		ankans[ankansLength][0].copy(ankan[0]);
		ankans[ankansLength][1].copy(ankan[1]);
		ankans[ankansLength][2].copy(ankan[2]);
		ankans[ankansLength++][3].copy(ankan[3]);
	}

	/**
	 * 暗槓の配列を取得する。
	 * 
	 * @return 暗槓の配列
	 */
	public Hai[][] getAnkans() {
		return ankans;
	}

	/**
	 * 暗槓の配列の長さを取得する。
	 * 
	 * @return 暗槓の配列の長さ
	 */
	public int getAnkansLength() {
		return ankansLength;
	}

	/**
	 * 暗槓の配列をコピーする。
	 * 
	 * @param minkans
	 *            暗槓の配列
	 * @return 暗槓の配列の長さ
	 */
	public int copyAnkans(Hai[][] ankans) {
		for (int i = 0; i < this.ankansLength; i++) {
			ankans[i][0].copy(this.ankans[i][0]);
			ankans[i][1].copy(this.ankans[i][1]);
			ankans[i][2].copy(this.ankans[i][2]);
			ankans[i][3].copy(this.ankans[i][3]);
		}
		return this.ankansLength;
	}

	/**
	 * カウントフォーマットを管理するクラスです。
	 * <p>
	 * 構造体のように使用します。
	 * </p>
	 * 
	 * @author Yuji Urushibara
	 * 
	 */
	public static class CountFormat {
		/**
		 * カウントを管理するクラスです。
		 * <p>
		 * 構造体のように使用します。
		 * </p>
		 * 
		 * @author Yuji Urushibara
		 * 
		 */
		public static class Count {
			/** 牌番号 */
			public int id;

			/** 牌の個数 */
			public int length;
		}

		/** カウントの最大値 */
		public static final int COUNT_MAX = 14 + 2;

		/** カウントの配列 */
		public Count[] counts = new Count[COUNT_MAX];

		/** カウントの長さ */
		public int length;

		{
			for (int i = 0; i < COUNT_MAX; i++)
				counts[i] = new Count();
		}

		/**
		 * カウントの配列の長さの合計を取得する。
		 * 
		 * @return　カウントの配列の長さの合計
		 */
		public int getTotalCountLength() {
			int totalCountLength = 0;

			for (int i = 0; i < length; i++)
				totalCountLength += counts[i].length;

			return totalCountLength;
		}
	}

	/**
	 * カウントフォーマットを取得する。
	 * 
	 * @param addHai
	 *            手牌に追加する牌。nullでも良い。
	 * @return カウントフォーマット
	 */
	public void getCountFormat(CountFormat countFormat, Hai addHai) {
		int jyunTehaiId;
		int addHaiId = 0;
		boolean set = true;

		countFormat.length = 0;

		if (addHai != null) {
			addHaiId = addHai.getId();
			set = false;
		}

		for (int i = 0; i < jyunTehaiLength;) {
			jyunTehaiId = jyunTehai[i].getId();

			if (!set && (jyunTehaiId > addHaiId)) {
				set = true;
				countFormat.counts[countFormat.length].id = addHaiId;
				countFormat.counts[countFormat.length].length = 1;
				countFormat.length++;
				continue;
			}

			countFormat.counts[countFormat.length].id = jyunTehaiId;
			countFormat.counts[countFormat.length].length = 1;

			if (!set && (jyunTehaiId == addHaiId)) {
				set = true;
				countFormat.counts[countFormat.length].length++;
			}

			while (++i < jyunTehaiLength)
				if (jyunTehaiId == jyunTehai[i].getId())
					countFormat.counts[countFormat.length].length++;
				else
					break;

			countFormat.length++;
		}
	}

	/**
	 * 上がりの組み合わせのクラスです。
	 * 
	 * @author Yuji Urushibara
	 * 
	 */
	public static class Combi {
		/** 頭の牌番号 */
		public int atamaId = 0;

		/** 順子の牌番号の配列 */
		public int[] shunIds = new int[4];

		/** 順子の牌番号の配列の数 */
		public int shunCount = 0;

		/** 刻子の牌番号の配列 */
		public int[] kouIds = new int[4];

		/** 刻子の牌番号の配列の数 */
		public int kouCount = 0;

		/**
		 * Combiオブジェクトをコピーします。
		 * 
		 * @param combi
		 *            Combiオブジェクト
		 */
		public void copy(Combi combi) {
			atamaId = combi.atamaId;

			shunCount = combi.shunCount;
			shunIds[0] = combi.shunIds[0];
			shunIds[1] = combi.shunIds[1];
			shunIds[2] = combi.shunIds[2];
			shunIds[3] = combi.shunIds[3];

			kouCount = combi.kouCount;
			kouIds[0] = combi.kouIds[0];
			kouIds[1] = combi.kouIds[1];
			kouIds[2] = combi.kouIds[2];
			kouIds[3] = combi.kouIds[3];
		}
	}

	/**
	 * 上がりの組み合わせの構築を管理するクラスです。
	 * 
	 * @author Yuji Urushibara
	 * 
	 */
	private static class CombiManage {
		/** 上がりの組み合わせ（作業領域） */
		public Combi combiWork = new Combi();

		/** 上がりの組み合わせの配列 */
		public Combi[] combis = new Combi[10];

		/** 上がりの組み合わせの配列の数 */
		public int combisCount = 0;

		/** カウントの配列の長さの残り */
		public int totalCount = 0;

		{
			for (int i = 0; i < 10; i++)
				combis[i] = new Combi();
		}

		/**
		 * 作業領域を初期化する。
		 * 
		 * @param totalCount
		 *            カウントの配列の長さの残り
		 */
		public void init(int totalCount) {
			combiWork.atamaId = 0;
			combiWork.shunCount = 0;
			combiWork.kouCount = 0;
			combisCount = 0;
			this.totalCount = totalCount;
		}

		/**
		 * 上がりの組み合わせを追加する。
		 */
		public void add() {
			combis[combisCount++].copy(combiWork);
		}
	}

	/** 上がりの組み合わせの構築を管理 */
	public CombiManage combiManage = new CombiManage();

	public int getCombi(Combi[] combis, CountFormat countFormat) {
		combiManage.init(countFormat.getTotalCountLength());
		searchCombi(countFormat, 0);
		combis = combiManage.combis;
		return combiManage.combisCount;
	}

	/**
	 * 上がりの組み合わせを探す。
	 * <p>
	 * 再帰的に上がりの組み合わせを探します。<br>
	 * 処理速度が重要です。<br>
	 * TODO 最適化の手段を検討すること。
	 * </p>
	 * 
	 * @param pos
	 *            検索位置
	 */
	public void searchCombi(CountFormat countFormat, int pos) {
		/*
		 * 検索位置を更新します。
		 */
		for (; pos < countFormat.length; pos++)
			if (countFormat.counts[pos].length > 0)
				break;

		if (pos >= countFormat.length)
			return;

		/*
		 * 頭をチェックする。
		 */
		if (combiManage.combiWork.atamaId == 0) {
			if (countFormat.counts[pos].length >= 2) {
				/*
				 * 頭を確定して情報を更新する。
				 */
				countFormat.counts[pos].length -= 2;
				combiManage.totalCount -= 2;
				combiManage.combiWork.atamaId = countFormat.counts[pos].id;

				/*
				 * 上がりの組み合わせを見つけたら追加する。
				 */
				if (combiManage.totalCount <= 0)
					combiManage.add();
				else
					searchCombi(countFormat, pos);

				/*
				 * 確定した頭を戻す。
				 */
				countFormat.counts[pos].length += 2;
				combiManage.totalCount += 2;
				combiManage.combiWork.atamaId = 0;
			}
		}

		/*
		 * 順子をチェックする。
		 */
		if (((pos + 2) < countFormat.length)
				&& ((countFormat.counts[pos + 2].id & KIND_TSUU) == 0)) {
			if ((countFormat.counts[pos].id + 1 == countFormat.counts[pos + 1].id)
					&& (countFormat.counts[pos + 1].length > 0)) {
				if ((countFormat.counts[pos].id + 2 == countFormat.counts[pos + 2].id)
						&& (countFormat.counts[pos + 2].length > 0)) {
					/*
					 * 順子を確定して情報を更新する。
					 */
					countFormat.counts[pos].length--;
					countFormat.counts[pos + 1].length--;
					countFormat.counts[pos + 2].length--;
					combiManage.totalCount -= 3;
					combiManage.combiWork.shunIds[combiManage.combiWork.shunCount] = countFormat.counts[pos].id;
					combiManage.combiWork.shunCount++;

					/*
					 * 上がりの組み合わせを見つけたら追加する。
					 */
					if (combiManage.totalCount <= 0)
						combiManage.add();
					else
						searchCombi(countFormat, pos);

					/*
					 * 確定した順子を戻す。
					 */
					countFormat.counts[pos].length++;
					countFormat.counts[pos + 1].length++;
					countFormat.counts[pos + 2].length++;
					combiManage.totalCount += 3;
					combiManage.combiWork.shunCount--;
				}
			}
		}

		/*
		 * 刻子をチェックする。
		 */
		if (countFormat.counts[pos].length >= 3) {
			/*
			 * 刻子を確定して情報を更新する。
			 */
			countFormat.counts[pos].length -= 3;
			combiManage.totalCount -= 3;
			combiManage.combiWork.kouIds[combiManage.combiWork.kouCount] = countFormat.counts[pos].id;
			combiManage.combiWork.kouCount++;

			/*
			 * 上がりの組み合わせを見つけたら追加する。
			 */
			if (combiManage.totalCount <= 0)
				combiManage.add();
			else
				searchCombi(countFormat, pos);

			/*
			 * 確定した刻子を戻す。
			 */
			combiManage.totalCount += 3;
			countFormat.counts[pos].length += 3;
			combiManage.combiWork.kouCount--;
		}
	}
}
