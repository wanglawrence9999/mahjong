package jp.sourceforge.andjong.mahjong;

/**
 * カウントフォーマットを管理するクラスです。
 * <p>
 * 構造体のように使用します。
 * </p>
 *
 * @author Yuji Urushibara
 *
 */
public class CountFormat {
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

	/**
	 * カウントフォーマットを取得する。
	 *
	 * @param addHai
	 *            手牌に追加する牌。nullでも良い。
	 * @return カウントフォーマット
	 */
	public static void getCountFormat(Tehai tehai, CountFormat countFormat,
			Hai addHai) {
		int jyunTehaiId;
		int addHaiId = 0;
		boolean set = true;

		countFormat.length = 0;

		if (addHai != null) {
			addHaiId = addHai.getNoKind();
			set = false;
		}

		for (int i = 0; i < tehai.getJyunTehaiLength();) {
			jyunTehaiId = (tehai.getJyunTehai())[i].getNoKind();

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

			while (++i < tehai.getJyunTehaiLength())
				if (jyunTehaiId == (tehai.getJyunTehai())[i].getNoKind())
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

	public int getCombi(Combi[] combis) {
		combiManage.init(getTotalCountLength());
		searchCombi(this, 0);
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
		if (((pos + 2) < countFormat.length) && ((countFormat.counts[pos + 2].id & Hai.KIND_TSUU) == 0)) {
			if ((countFormat.counts[pos].id + 1 == countFormat.counts[pos + 1].id) && (countFormat.counts[pos + 1].length > 0)) {
				if ((countFormat.counts[pos].id + 2 == countFormat.counts[pos + 2].id) && (countFormat.counts[pos + 2].length > 0)) {
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
